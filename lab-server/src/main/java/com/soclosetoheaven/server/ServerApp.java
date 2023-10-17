package com.soclosetoheaven.server;



import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class ServerApp {

    private static final Lock loggerLock = new ReentrantLock();
    private static Logger Logger;

    private static volatile boolean running;

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("client.encoding.override", "UTF-8");
        String configPath = System.getenv("SERVER_LOGGER_CONFIG");
        if (configPath == null) {
            System.err.println("Unable to load logger config, stopping program!");
            return;
        }
        try {
            configureLogger(configPath);
            ServerInstance server = new ServerInstance();
            running = true;
            server.launch();
        } catch (IOException | SecurityException e) {
            System.err.println(e.getMessage());
            System.err.println("Unable to start server!");
        }
    }

    public static boolean getState() {
        return running;
    }

    private static void configureLogger(String configPath) throws IOException{
        try(FileInputStream fileInputStream = new FileInputStream(configPath)) {
            new File("logs").mkdir();
            LogManager.getLogManager().readConfiguration(fileInputStream);
            Logger = Logger.getLogger(ServerApp.class.getName());
        }
    }

    public static void changeState() {
        running = !running;
    }

    public static void log(Level level, String message) {
        loggerLock.lock();
        Logger.log(level, message);
        loggerLock.unlock();
    }
}