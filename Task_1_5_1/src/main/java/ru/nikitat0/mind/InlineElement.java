package ru.nikitat0.mind;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class InlineElement {
    public InlineElement join(CharSequence seq) {
        return new Sequence(this, new Text(seq));
    }

    public InlineElement join(InlineElement next) {
        return new Sequence(this, next);
    }

    protected Iterator<InlineElement> iterator() {
        return new OnceIterator<InlineElement>(this);
    }

    static class Sequence extends InlineElement {
        private final InlineElement text;
        private final InlineElement next;

        Sequence(InlineElement text, InlineElement next) {
            this.text = text;
            this.next = next;
        }

        protected Iterator<InlineElement> iterator() {
            return new Iterator<InlineElement>() {
                private Iterator<InlineElement> iter = text.iterator();
                private boolean isIteratingCurrent = true;

                private void forward() {
                    if (isIteratingCurrent && !iter.hasNext()) {
                        iter = next.iterator();
                        isIteratingCurrent = false;
                    }
                }

                @Override
                public boolean hasNext() {
                    forward();
                    return iter.hasNext();
                }

                @Override
                public InlineElement next() {
                    return iter.next();
                }

            };
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Sequence)) {
                return false;
            }
            Iterator<InlineElement> left = this.iterator();
            Iterator<InlineElement> right = this.iterator();
            while (left.hasNext() && right.hasNext()) {
                if (!Objects.equals(left.next(), right.next())) {
                    return false;
                }
            }
            return left.hasNext() == right.hasNext();
        }

        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner("");
            Iterator<InlineElement> iterator = iterator();
            while (iterator.hasNext()) {
                joiner.add(iterator.next().toString());
            }
            return joiner.toString();
        }
    }
}
