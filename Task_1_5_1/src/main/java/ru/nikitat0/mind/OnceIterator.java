package ru.nikitat0.mind;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class OnceIterator<T> implements Iterator<T> {
    private T element;

    OnceIterator(T element) {
        this.element = element;
    }

    @Override
    public boolean hasNext() {
        return element != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T element = this.element;
        this.element = null;
        return element;
    }
}
