package com.soclosetoheaven.common.util;

@FunctionalInterface
public interface Observer<T> {
    void update(T t);
}
