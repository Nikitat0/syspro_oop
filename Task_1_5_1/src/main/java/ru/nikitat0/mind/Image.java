package ru.nikitat0.mind;

public class Image extends Element.Inline {
    private final Link imageLink;

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        Image other = (Image) otherObj;
        return this.imageLink.equals(other.imageLink);
    }

    @Override
    public String toString() {
        return "!" + imageLink.toString();
    }

    private Image(Link imageLink) {
        this.imageLink = imageLink;
    }

    public static final class Builder {
        private final Link.Builder linkBuilder;

        public Builder(String path) {
            this.linkBuilder = new Link.Builder(path);
            this.linkBuilder.setText(new Text(""));
        }

        public Builder setDescription(Text desc) {
            linkBuilder.setText(desc);
            return this;
        }

        public Builder setTooltip(String tooltip) {
            this.linkBuilder.setTooltip(tooltip);
            return this;
        }

        public Image build() {
            return new Image(linkBuilder.build());
        }
    }
}
