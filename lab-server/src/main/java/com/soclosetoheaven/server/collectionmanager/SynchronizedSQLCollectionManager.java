package com.soclosetoheaven.server.collectionmanager;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.util.Observer;
import com.soclosetoheaven.server.dao.SQLDragonDAO;
import com.soclosetoheaven.common.model.Dragon;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedSQLCollectionManager implements DragonCollectionManager {

    private final List<Dragon> collection;


    private final ReadWriteLock lock;

    private final SQLDragonDAO dao;

    private final List<Observer<List<Dragon>>> observers;

    public SynchronizedSQLCollectionManager(SQLDragonDAO dao) throws SQLException {
        this.lock = new ReentrantReadWriteLock();
        this.dao = dao;
        this.collection = dao.readAll();
        this.observers = new ArrayList<>();
    }

    @Override
    public List<Dragon> getCollection() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(collection);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean add(Dragon dragon) {
        int returnedValue;
        boolean flag = false;
        lock.writeLock().lock();
        if ((returnedValue = dao.create(dragon)) != SQLDragonDAO.ERROR_CODE) {
            dragon.setID(returnedValue);
            collection.add(dragon);
            flag = true;
            notifyObservers();
        }
        lock.writeLock().unlock();
        return flag;
    }

    @Override
    public Dragon remove(int index) {
        Dragon dragon = null;
        lock.writeLock().lock();
        if (index < collection.size() && dao.delete((dragon = collection.get(index))) != SQLDragonDAO.ERROR_CODE) {
            collection.remove(index);
            notifyObservers();
        }
        lock.writeLock().unlock();
        return dragon;
    }

    @Override
    public Dragon get(int index) {
        Dragon dragon = null;
        lock.readLock().lock();
        if (index < collection.size())
            dragon = collection.get(index);
        lock.readLock().unlock();
        return dragon;
    }

    @Override
    public boolean clear() {
        boolean flag = false;
        lock.writeLock().lock();
        if (dao.delete(collection) != SQLDragonDAO.ERROR_CODE) {
            collection.clear();
            flag = true;
            notifyObservers();
        }
        lock.writeLock().unlock();
        return flag;
    }

    @Override
    public void sort() {
        lock.writeLock().lock();
        Collections.sort(collection);
        lock.writeLock().unlock();
    }

    @Override
    public boolean removeByID(int id) {
        boolean flag = false;
        lock.writeLock().lock();
        Dragon dragon = collection
                .stream()
                .filter(elem -> elem.getID() == id)
                .findFirst()
                .orElse(null);
        if (dragon != null && dao.delete(dragon) != SQLDragonDAO.ERROR_CODE) {
            collection.remove(dragon);
            flag = true;
            notifyObservers();
        }
        lock.writeLock().unlock();
        return flag;
    }

    @Override
    public boolean update(Dragon dragon, int id) {
        boolean flag = false;
        lock.writeLock().lock();
        Dragon element = collection
                .stream()
                .filter(elem -> elem.getID() == id)
                .findFirst()
                .orElse(null);
        if (element != null && dao.update(dragon.setID(element.getID())) != SQLDragonDAO.ERROR_CODE) {
            collection.remove(element);
            dragon.setCreatorId(element.getCreatorId());
            collection.add(dragon);
            flag = true;
            notifyObservers();
        }
        lock.writeLock().unlock();
        return flag;
    }


    @Override
    public Dragon getByID(int id) {
        lock.readLock().lock();
        Dragon dragon =  collection.stream()
                .filter(p -> p.getID() == id)
                .findFirst()
                .orElse(null);
        lock.readLock().unlock();
        return dragon;
    }

    @Override
    public boolean removeAll(List<Dragon> dragons) {
        boolean flag = false;
        lock.writeLock().lock();
        if (dao.delete(dragons) != SQLDragonDAO.ERROR_CODE) {
            collection.removeAll(dragons);
            flag = true;
            notifyObservers();
        }
        lock.writeLock().unlock();
        return flag;
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        String info = "%s - %s, %s - %s".formatted(
                "Collection type",
                "List",
                "Current size",
                collection.size()
        );
        lock.readLock().unlock();
        return info;
    }

    @Override
    public void registerObserver(Observer<List<Dragon>> observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        List<Dragon> collectionCopy = new ArrayList<>(collection);
        this.observers.forEach(observer -> observer.update(collectionCopy));
    }

    @Override
    public void removeObserver(Observer<List<Dragon>> observer) {
        this.observers.remove(observer);
    }
}
