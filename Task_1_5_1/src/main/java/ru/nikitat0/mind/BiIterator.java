package ru.nikitat0.mind;

import java.util.Iterator;

final class BiIterator<T> implements Iterator<T> {
    private Iterator<T> first;
    private Iterator<T> second;

    public BiIterator(Iterator<T> first, Iterator<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean hasNext() {
        return first.hasNext() || second.hasNext();
    }

    @Override
    public T next() {
        if (first.hasNext()) {
            return first.next();
        }
        return second.next();
    }
}
