package ru.nikitat0.mind;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class Element {
    public Block then(CharSequence next) {
        return then(new Text(next));

    }

    public Block then(Element.Inline next) {
        return then(next.toParagraph());
    }

    public abstract Block then(Block next);

    protected Iterator<Element> iterator() {
        return new OnceIterator<Element>(this);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (!(otherObj instanceof Element)) {
            return false;
        }
        Iterator<Element> thisIterator = this.iterator();
        Iterator<Element> otherIterator = ((Element) otherObj).iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), otherIterator.next())) {
                return false;
            }
        }
        return thisIterator.hasNext() == otherIterator.hasNext();
    }

    public static abstract class Inline extends Element {

        public Inline join(CharSequence seq) {
            return join(new Text(seq));
        }

        public Inline join(Inline next) {
            return new Sequence(next);
        }

        @Override
        public Block then(Block next) {
            return toParagraph().then(next);
        }

        private class Sequence extends Inline {
            private final Inline next;

            Sequence(Inline next) {
                this.next = next;
            }

            protected Iterator<Element> iterator() {
                return new BiIterator<Element>(Inline.this.iterator(), next.iterator());
            }

            @Override
            public String toString() {
                StringJoiner joiner = new StringJoiner("");
                Iterator<Element> iterator = iterator();
                while (iterator.hasNext()) {
                    joiner.add(iterator.next().toString());
                }
                return joiner.toString();
            }
        }

        public Block toParagraph() {
            return new Paragraph(this);
        }
    }

    public static abstract class Block extends Element {
        @Override
        public Block then(Block next) {
            return new Sequence(next);
        }

        private class Sequence extends Block {
            private final Block next;

            Sequence(Block next) {
                this.next = next;
            }

            protected Iterator<Element> iterator() {
                return new BiIterator<Element>(Block.this.iterator(), next.iterator());
            }

            @Override
            public String toString() {
                StringJoiner joiner = new StringJoiner("\n\n");
                Iterator<Element> iterator = iterator();
                while (iterator.hasNext()) {
                    joiner.add(iterator.next().toString());
                }
                return joiner.toString();
            }
        }
    }
}
