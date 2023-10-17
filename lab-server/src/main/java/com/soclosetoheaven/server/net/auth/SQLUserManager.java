package com.soclosetoheaven.server.net.auth;

import com.soclosetoheaven.common.exception.InvalidRequestException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.exception.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.server.dao.SQLUserDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SQLUserManager implements UserManager {

    private final SQLUserDAO dao;


    private final List<User> collection;

    public SQLUserManager(SQLUserDAO dao) throws SQLException {
        this.dao = dao;
        this.collection = dao.readAll();
    }

    @Override
    public Response login(RequestBody requestBody) throws ManagingException {
        AuthCredentials authCredentials = requestBody.getAuthCredentials();
        User loggedUser = collection
                .stream()
                .filter(user -> user.getAuthCredentials().equals(authCredentials))
                .findFirst().orElseThrow(InvalidAuthCredentialsException::new);
        return new Response(
                Messages.LOGGED_IN.key
        );
    }

    @Override
    public Response register(RequestBody requestBody) throws ManagingException {
        AuthCredentials authCredentials = requestBody.getAuthCredentials();
        String[] args = requestBody.getArgs();
        if (args.length < 1)
            throw new InvalidRequestException();
        String name = args[FIRST_ARG];
        if (collection
                .stream()
                .map(User::getAuthCredentials)
                .map(AuthCredentials::getLogin)
                .anyMatch(elem -> elem.equals(authCredentials.getLogin()))
        )
            throw new InvalidAuthCredentialsException(Messages.LOGIN_TAKEN.key);
        User user = new User(name, authCredentials);
        int id = dao.create(user);
        if (id == SQLUserDAO.ERROR_CODE)
            throw new ManagingException(Messages.MANAGING_ERROR.key);
        user.setID(id);
        collection.add(user);
        return new Response(Messages.SUCCESSFULLY.key);
    }

    @Override
    public User getUserByID(int id) {
        Optional<User> user = collection
                .stream()
                .filter(elem -> elem.getID() == id)
                .findFirst();
        return user.orElse(null);
    }

    @Override
    public User getUserByAuthCredentials(AuthCredentials authCredentials) {
        Optional<User> user = collection
                .stream()
                .filter(elem -> elem.getAuthCredentials().equals(authCredentials))
                .findFirst();
        return user.orElse(null);
    }


    @Override
    public boolean checkIfAuthorized(AuthCredentials authCredentials){
        AuthCredentials auth = collection
                .stream()
                .map(User::getAuthCredentials)
                .filter(elem -> Objects.equals(elem, authCredentials))
                .findFirst()
                .orElse(null);
        return auth != null;
    }
}
