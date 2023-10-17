package com.soclosetoheaven.server;


import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.ResponseWithCollection;
import com.soclosetoheaven.common.net.messaging.ResponseWithException;
import com.soclosetoheaven.server.dao.JDBCDragonDAO;
import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.server.collectionmanager.SynchronizedSQLCollectionManager;
import com.soclosetoheaven.server.commandmanager.ServerCommandManager;

import com.soclosetoheaven.server.dao.JDBCUserDAO;
import com.soclosetoheaven.server.dao.SQLDragonDAO;
import com.soclosetoheaven.server.dao.SQLUserDAO;
import com.soclosetoheaven.server.net.auth.SynchronizedSQLUserManager;
import com.soclosetoheaven.server.net.connection.UDPServerConnection;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import static com.soclosetoheaven.common.net.connection.SimpleConnection.LAST_PACKET_TOKEN;


public class ServerInstance{

    private static final int PORT = 34684;

    private static final long CLIENT_THREAD_KILL_TIME = 120_000;


    private final UDPServerConnection connection;

    private DragonCollectionManager collectionManager;

    private UserManager userManager;

    private final Lock serializationLock = new ReentrantLock();

    private final Lock clientsMapLock = new ReentrantLock();


    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Map<SocketAddress, ClientHandler> clients = new HashMap<>();

    public ServerInstance() throws SocketException {
        connection = new UDPServerConnection(PORT);
    }

    public void launch() {
        try {
            SQLDragonDAO dragonDAO = new JDBCDragonDAO(initializeSQLConnection());
            SQLUserDAO userDAO = new JDBCUserDAO(initializeSQLConnection());
            this.userManager = new SynchronizedSQLUserManager(userDAO);
            collectionManager = new SynchronizedSQLCollectionManager(dragonDAO);
            collectionManager.registerObserver((list) ->
                clients.values().forEach(client -> client.sendResponse(
                        new ResponseWithCollection(list)
                        )
                )
            );
        } catch (SQLException | IOException e) {
            ServerApp.log(Level.SEVERE, "%s - server shutdown".formatted(e.getMessage()));
            return;
        }
        ServerApp.log(Level.INFO, collectionManager.toString());
        Thread dataReceiver = new Thread(new ConnectionHandler());
        dataReceiver.start();
        Timer clientThreadKiller = new Timer(true);
        clientThreadKiller.schedule(new TimerTask() {
                @Override
                public void run() {
                    clientsMapLock.lock();
                    Set<ClientHandler> oldSet = new HashSet<>(clients.values());
                    oldSet.forEach(client -> {
                        if (System.currentTimeMillis() - client.getLastInteraction() >= CLIENT_THREAD_KILL_TIME) {
                            client.interrupt();
                            clients.remove(client.getSocketAddress());
                        }
                    });
                    clientsMapLock.unlock();
                }
            },
                CLIENT_THREAD_KILL_TIME
        );
    }

    private class ClientHandler{

        private final SocketAddress client;

        private final LinkedList<byte[]> buffers = new LinkedList<>();

        private final Thread thread;

        private long lastInteraction;


        ClientHandler(SocketAddress client) {
            this.client = client;
            this.thread = new Thread(this::handle);
            thread.start();
        }


        synchronized void handle(){
            while(ServerApp.getState())
                try {
                    if (buffers.isEmpty() ||
                            buffers.getLast()[connection.MAX_PACKET_SIZE] != LAST_PACKET_TOKEN) {
                        wait();
                        continue;
                    }
                    ServerApp.log(Level.INFO, "HANDLING REQUEST FROM CLIENT:" + client.toString());
                    byte[] data;
                    data = connection.transformPackagesToData(buffers);
                    buffers.clear();
                    Request request;
                    serializationLock.lock();
                    request = SerializationUtils.deserialize(data);
                    serializationLock.unlock();
                    Response response;

                    ServerCommandManager taskCommandManager;
                    if (userManager.checkIfAuthorized(request.getAuthCredentials()))
                        taskCommandManager = ServerCommandManager.defaultManager(collectionManager, userManager);
                    else
                        taskCommandManager = ServerCommandManager.authManager(userManager);
                    try {
                        response = taskCommandManager.manage(request);
                    } catch (ManagingException e) {
                        response = new ResponseWithException(e);
                    }
                    sendResponse(response);
                } catch (SerializationException | InterruptedException e) {
                    ServerApp.log(Level.SEVERE, e.getMessage());
                }
        }

        synchronized void put(byte[] buf) {
            lastInteraction = System.currentTimeMillis();
            buffers.add(buf);
            if (buf[connection.MAX_PACKET_SIZE] == LAST_PACKET_TOKEN)
                notify();
        }

        public void sendResponse(Response response) {
            executor.execute(() -> {
                try {
                    serializationLock.lock();
                    byte[] serializedResponse = SerializationUtils.serialize(response);
                    serializationLock.unlock();
                    connection.sendData(new ImmutablePair<>(
                                client,
                                serializedResponse
                        )
                    );
                } catch (IOException e) {
                    ServerApp.log(Level.SEVERE, e.getMessage());
                }
            }
            );
        }

        public void interrupt() {
            if (!thread.isInterrupted())
                this.thread.interrupt();
        }

        public synchronized long getLastInteraction() {
            return lastInteraction;
        }

        public SocketAddress getSocketAddress() {
            return this.client;
        }
    }


    private class ConnectionHandler implements Runnable{
        public void run() {
            while (ServerApp.getState()) {
                try {
                    Pair<SocketAddress, byte[]> pair = connection.waitAndGetData();
                    SocketAddress client = pair.getLeft();
                    byte[] packet = pair.getRight();
                    clientsMapLock.lock();
                    if (!clients.containsKey(client))
                        clients.put(client, new ClientHandler(client));
                    clients.get(client).put(packet);
                    clientsMapLock.unlock();
                } catch (IOException e) {
                    ServerApp.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }
    private static Connection initializeSQLConnection() throws IOException, SQLException{
        try (BufferedReader stream = new BufferedReader(new FileReader(System.getenv("DB_CONFIG")))) {
            String driver = stream.readLine();
            String user = stream.readLine();
            String password = stream.readLine();
            return DriverManager.getConnection(driver, user, password);
        }
    }
}
