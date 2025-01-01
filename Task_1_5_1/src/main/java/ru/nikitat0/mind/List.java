package ru.nikitat0.mind;

import java.util.ArrayList;
import java.util.Objects;

public class List extends Element.Block {
    private final Style style;
    private final ArrayList<Item> items;

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (this.getClass() != otherObj.getClass()) {
            return false;
        }
        List other = (List) otherObj;
        return this.style.equals(other.style) && this.items.equals(other.items);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Item item : items) {
            if (style == Style.Ordered) {
                builder.append(i);
                builder.append('.');
            } else {
                builder.append('-');
            }
            builder.append(item);
            builder.append('\n');
            i++;
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private List(Style style, ArrayList<Item> items) {
        this.style = style;
        this.items = items;
    }

    public static final class Builder {
        private final Style style;
        private final ArrayList<Item> items = new ArrayList<Item>();

        public Builder(Style style) {
            this.style = style;
        }

        public Builder addItem(Element.Inline text) {
            return addItem(text, null);
        }

        public Builder addItem(Element.Inline text, Element.Block block) {
            items.add(new Item(text, block));
            return this;
        }

        public List build() {
            return new List(style, new ArrayList<Item>(items));
        }
    }

    public static final class TaskListBuilder {
        private final ArrayList<Item> items = new ArrayList<Item>();

        public TaskListBuilder() {
        }

        public TaskListBuilder addItem(Element.Inline text, boolean checked) {
            return addItem(text, null, checked);
        }

        public TaskListBuilder addItem(Element.Inline text, Element.Block block, boolean checked) {
            items.add(new TaskItem(text, block, checked));
            return this;
        }

        public List build() {
            return new List(Style.Unordered, new ArrayList<Item>(items));
        }
    }

    private static class Item {
        protected final Element.Inline text;
        protected final Element.Block body;

        private Item(Element.Inline text, Element.Block body) {
            this.text = text;
            this.body = body;
        }

        @Override
        public boolean equals(Object otherObj) {
            if (this.getClass() != otherObj.getClass()) {
                return false;
            }
            Item other = (Item) otherObj;
            return this.text.equals(other.text) && this.body.equals(other.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getClass(), text, body);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (text != null) {
                builder.append(' ');
                builder.append(text);
            }
            if (body != null) {
                builder.append('\n');
                builder.append(body.toString().replaceAll("(?m)^", "    "));
            }
            return builder.toString();
        }
    }

    private static class TaskItem extends Item {
        private final boolean checked;

        private TaskItem(Element.Inline text, Element.Block body, boolean checked) {
            super(text, body);
            this.checked = checked;
        }

        @Override
        public boolean equals(Object otherObj) {
            if (this.getClass() != otherObj.getClass()) {
                return false;
            }
            TaskItem other = (TaskItem) otherObj;
            return this.text.equals(other.text)
                    && this.body.equals(other.body)
                    && this.checked == other.checked;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), checked);
        }

        @Override
        public String toString() {
            return String.format(" [%s]%s", checked ? 'x' : ' ', super.toString());
        }
    }

    public static enum Style {
        Ordered,
        Unordered,
    }
}
