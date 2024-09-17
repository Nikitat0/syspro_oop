package ru.nikitat0.blackjack.cards;

/**
 * An representation of card on gaming table.
 */
public interface CardView {
    /**
     * Transforms the card to type specified by viewer.
     *
     * @param <T>    type of viewer representation
     * @param viewer viewer
     * @return viewer representation
     */
    <T> T see(CardViewer<T> viewer);

    /**
     * A view of closed card.
     */
    public static CardView CLOSED_CARD = new CardView() {
        @Override
        public <T> T see(CardViewer<T> viewer) {
            return viewer.closedCard();
        }
    };

    /**
     * A view of card with assigned points.
     */
    public static final class CardWithPoints implements CardView {
        /**
         * The card lying on gaming table.
         */
        public final Card card;

        /**
         * How many points this card gives to holder.
         */
        public final int points;

        /**
         * Constructs view with points card gives by itself.
         *
         * @param card card
         */
        public CardWithPoints(Card card) {
            this.card = card;
            this.points = card.rank.points;
        }

        /**
         * Constructs view with assigned points.
         *
         * @param card   card
         * @param points points
         */
        public CardWithPoints(Card card, int points) {
            this.card = card;
            this.points = points;
        }

        @Override
        public <T> T see(CardViewer<T> viewer) {
            return viewer.cardWithPoints(card, points);
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", card, points);
        }
    }
}
