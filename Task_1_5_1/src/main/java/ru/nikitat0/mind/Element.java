package ru.nikitat0.mind;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class Element {
    public static abstract class Inline {
        public Inline join(CharSequence seq) {
            return new Sequence(this, new Text(seq));
        }

        public Inline join(Inline next) {
            return new Sequence(this, next);
        }

        protected Iterator<Inline> iterator() {
            return new OnceIterator<Inline>(this);
        }

        static class Sequence extends Inline {
            private final Inline text;
            private final Inline next;

            Sequence(Inline text, Inline next) {
                this.text = text;
                this.next = next;
            }

            protected Iterator<Inline> iterator() {
                return new Iterator<Inline>() {
                    private Iterator<Inline> iter = text.iterator();
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
                    public Inline next() {
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
                Iterator<Inline> left = this.iterator();
                Iterator<Inline> right = this.iterator();
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
                Iterator<Inline> iterator = iterator();
                while (iterator.hasNext()) {
                    joiner.add(iterator.next().toString());
                }
                return joiner.toString();
            }
        }
    }
}
