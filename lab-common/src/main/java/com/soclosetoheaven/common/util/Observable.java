package com.soclosetoheaven.common.util;

public interface Observable<T> {

    void registerObserver(Observer<T> observer);

    void notifyObservers();

    void removeObserver(Observer<T> observer);
}
