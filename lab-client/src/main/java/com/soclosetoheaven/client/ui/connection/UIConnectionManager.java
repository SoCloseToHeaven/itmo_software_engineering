package com.soclosetoheaven.client.ui.connection;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.common.net.connection.ConnectionManager;
import com.soclosetoheaven.client.net.connection.UDPClientConnection;
import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.messaging.*;
import com.soclosetoheaven.common.util.Observable;
import com.soclosetoheaven.common.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class UIConnectionManager implements ConnectionManager, Observable<List<Dragon>> {

    private static final int RESPONSE_TIMEOUT = 5;

    private final Thread thread;

    private final UDPClientConnection connection;

    private final BlockingQueue<Response> queue;

    private final DialogManager dialogManager;

    private AuthCredentials authCredentials;

    private List<Dragon> serverCollection;

    private final List<Observer<List<Dragon>>> observers;

    public UIConnectionManager(UDPClientConnection connection, DialogManager dialogManager) {
        this.thread = new Thread(this::manageResponse);
        this.thread.setDaemon(true);
        this.thread.start();
        this.observers = new ArrayList<>();
        this.connection = connection;
        this.queue = new SynchronousQueue<>();
        this.dialogManager = dialogManager;
    }


    @Override
    public Response manageRequest(Request request) throws ManagingException { // TODO: refactor try-catch
        if (authCredentials != null)
            request.setAuthCredentials(authCredentials);
        try {
            connection.sendData(request);
            Response response = queue.poll(RESPONSE_TIMEOUT, TimeUnit.SECONDS);
            if (response == null)
                throw new ManagingException(LocaledUI.MANAGING_DATA_ERROR.key);
            else if (response instanceof ResponseWithException)
                throw ((ResponseWithException) response).getException();
            else
                dialogManager.showText(response.getDescription());
            return response;
        } catch (IOException e) {
            throw new ManagingException(LocaledUI.SENDING_DATA_ERROR.key);
        } catch (InterruptedException e) {
            throw new ManagingException(LocaledUI.MANAGING_DATA_ERROR.key);
        }
    }


    public void manageRequestWithoutResponse(Request request) {
        if (authCredentials != null)
            request.setAuthCredentials(authCredentials);
        try {
            connection.sendData(request);
        } catch (IOException e) {
            dialogManager.showText(LocaledUI.SENDING_DATA_ERROR.key);
        }
    }

    @Override
    public void manageResponse() { // TODO: refactor try-catch
        while (true) {
            try {
                Response response = connection.waitAndGetData();
                if (!(response instanceof ResponseWithCollection)) {
                    queue.offer(response, RESPONSE_TIMEOUT, TimeUnit.SECONDS);
                } else if (authCredentials != null) {
                    this.serverCollection = ((ResponseWithCollection) response).getCollection();
                    notifyObservers();
                }
            } catch (IOException e) {
                dialogManager.showText(Messages.SERVER_DOES_NOT_RESPOND.key);
            } catch (InterruptedException e) {
                dialogManager.showText(LocaledUI.MANAGING_DATA_ERROR.key);
            }
        }
    }


    @Override
    public void setAuthCredentials(AuthCredentials authCredentials) {
        this.authCredentials = authCredentials;
    }


    @Override
    public void registerObserver(Observer<List<Dragon>> observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(serverCollection));
    }

    @Override
    public void removeObserver(Observer<List<Dragon>> observer) {
        observers.remove(observer);
    }
}
