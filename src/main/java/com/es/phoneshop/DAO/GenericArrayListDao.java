package com.es.phoneshop.DAO;

import com.es.phoneshop.exception.ItemNotFoundException;
import com.es.phoneshop.model.GenericModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class GenericArrayListDao<T extends GenericModel> {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private long maxID;
    private final List<T> itemsList;

    public GenericArrayListDao() {
        maxID = 0L;
        this.itemsList = new ArrayList<>();
    }

    public T getItem(Long id) {
        lock.readLock().lock();
        try {
            if (id == null) {
                throw new IllegalArgumentException("id value is null");
            }
            return itemsList.stream()
                    .filter(item -> id.equals(item.getId()))
                    .findAny()
                    .orElseThrow(() -> new ItemNotFoundException(String.valueOf(id)));
        } finally {
            lock.readLock().unlock();
        }
    }


    public void saveItem(T item){
        lock.writeLock().lock();
        try {
            if (item.getId() == null) {
                item.setId(++maxID);
                itemsList.add(item);
            } else if (!item.equals(getItem(item.getId()))) {
                itemsList.set(Math.toIntExact(item.getId()-1), item);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void delete(Long id) {
        lock.writeLock().lock();
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID not set");
            }
            if (id > 0 && id <= maxID) {
                itemsList.remove(Math.toIntExact(id) - 1);
                rewriteID(id);
                --this.maxID;
            } else {
                throw new ItemNotFoundException(String.valueOf(id));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public ReadWriteLock getLock() {
        return lock;
    }

    public List<T> getItems() {
        return itemsList;
    }

    private void rewriteID(Long id) {
        itemsList.stream()
                .filter(item -> item.getId() > id)
                .forEach(item -> item.setId(item.getId() - 1));
    }
}
