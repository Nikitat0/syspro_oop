package ru.nikitat0.mind;

import java.util.Iterator;

final class OnceIterator<T> implements Iterator<T> {
    private T element;

    public OnceIterator(T element) {
        this.element = element;
    }

	@Override
	public boolean hasNext() {
        return element != null;
	}

	@Override
	public T next() {
        T element = this.element;
        this.element = null;
        return element;
	}
}
