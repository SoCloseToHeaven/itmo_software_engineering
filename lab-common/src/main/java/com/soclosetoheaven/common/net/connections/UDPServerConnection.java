package com.soclosetoheaven.common.net.connections;

import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class UDPServerConnection implements SimpleConnection<Request, Response> {


    private final DatagramSocket socket;


    private byte[] buffer;

    private InetSocketAddress client;



    public UDPServerConnection(int port) throws SocketException {
        socket = new DatagramSocket(port);
        socket.setReuseAddress(true); // needed for IP multicasting
        buffer = new byte[BUFFER_SIZE];
    }


    @Override
    public Request waitAndGetData() throws IOException {
        ArrayList<byte[]> buffers = new ArrayList<>();
        do {
            clearBuffer();
            DatagramPacket packet = new DatagramPacket(buffer, MAX_PACKET_SIZE);
            socket.receive(packet);
            connect(packet.getAddress().getHostAddress(), packet.getPort());
            buffers.add(buffer);
        } while (buffer.length == MAX_PACKET_SIZE);
        byte[] data = transformPackagesToData(buffers);
        try {
            Request request = SerializationUtils.deserialize(data);
            if (request == null)
                throw new IOException("Received null request");
            return request;
        } catch (SerializationException | ClassCastException e) {
            disconnect();
            throw new IOException(e);
        }
        //return SerializationUtils.deserialize(data);
    }

    @Override
    public void sendData(Response response) throws IOException{
        if (!socket.isConnected())
            return;
        byte[][] packets = transformDataToPackages(response);

        for (byte[] pac : packets) {
            DatagramPacket packet = new DatagramPacket(
                    ByteBuffer.allocate(MAX_PACKET_SIZE).put(pac).array(),
                    MAX_PACKET_SIZE,
                    client
            );

            socket.send(packet);
            disconnect();
        }

    }

    private void clearBuffer() {
        buffer = new byte[BUFFER_SIZE];
    }


    @Override
    public void connect(String adr, int port) throws IOException {
        if (!socket.isConnected()) {
            this.client = new InetSocketAddress(adr, port);
            this.socket.connect(client);
        }
    }

    @Override
    public void disconnect() throws IOException {
        try {
            this.socket.disconnect();
            this.client = null;
        } catch (UncheckedIOException e) {
            throw new IOException(e);
        }
    }

    public InetSocketAddress getClient() {
        return client;
    }
}
