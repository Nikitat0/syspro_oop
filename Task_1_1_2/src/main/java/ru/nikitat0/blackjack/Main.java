package ru.nikitat0.blackjack;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.cards.CardView;
import ru.nikitat0.blackjack.cards.CardViewer;
import ru.nikitat0.blackjack.cards.Deck;
import ru.nikitat0.blackjack.cards.Deck.Deck52;
import ru.nikitat0.blackjack.game.DeckProvider;
import ru.nikitat0.blackjack.game.Game;
import ru.nikitat0.blackjack.game.Game.Side;
import ru.nikitat0.blackjack.game.Game.Cause;
import ru.nikitat0.blackjack.game.Game.Round;
import ru.nikitat0.blackjack.game.Score;

/** The Main class is responsible for game cli. */
public class Main implements Game.PlayerController, Game.EventListener {
    /**
     * Identation pattern used in cli.
     */
    public static String IDENTATION = "    ";

    /**
     * Separator used in cli.
     */
    public static String TURN_SEPARATOR = "-------";

    /**
     * Starts with program.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        new Main().play();
    }

    private final Scanner scanner = new Scanner(System.in);
    private final Game game;
    private Round round = null;

    private Main() {
        game = new Game(this, new DeckProvider() {
            @Override
            public Deck obtainDeck() {
                return new Deck52();
            }
        });
        game.subscribe(this);
    }

    private void play() {
        out.println("Добро пожаловать в Блэкджек!");
        game.play();
    }

    private String formatHand(List<CardView> hand) {
        String[] cards = new String[hand.size()];
        CountingViewer viewer = new CountingViewer();
        for (int i = 0; i < hand.size(); i++) {
            cards[i] = hand.get(i).see(viewer);
        }
        int total = viewer.total;
        String formatted = Arrays.toString(cards);
        return total == -1 ? formatted : String.format("%s => %d", formatted, total);
    }

    private void printHands() {
        out.printf("%sВаши карты: %s\n", IDENTATION, formatHand(round.seePlayerHand()));
        out.printf("%sКарты дилера: %s\n", IDENTATION, formatHand(round.seeDealerHand()));
    }

    @Override
    public boolean doPickCard() {
        out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
        int answer;
        do {
            answer = scanner.nextInt();
        } while (answer != 0 && answer != 1);
        return answer == 1;
    }

    @Override
    public boolean doPlayNextRound() {
        return true;
    }

    @Override
    public void onRoundBegins(Round round) {
        this.round = round;
        out.printf("Раунд %d\n", round.ordinalNumber());
        out.println("Дилер раздал карты");
        printHands();
        out.println();
        out.println("Ваш ход:");
        out.println(TURN_SEPARATOR);
    }

    @Override
    public void onRoundEnds(Side winner, Cause reason) {
        if (reason == Cause.BLACKJACK) {
            if (winner == Side.PLAYER) {
                out.print("Вы собрали блэкджек!");
            } else {
                out.print("Дилер собрал блэкджек.");
            }
        } else {
            if (winner == Side.PLAYER) {
                out.print("Вы выиграли раунд!");
            } else {
                out.print("Дилер выиграл раунд.");
            }
        }
        Score score = game.getScore();
        String favor;
        if (score.left > score.right) {
            favor = "в вашу пользу";
        } else if (score.left == score.right) {
            favor = "ничья";
        } else {
            favor = "в пользу дилера";
        }
        out.printf(" Счёт %s %s.\n", score, favor);
        out.println();
    }

    @Override
    public void onPick(Side who, Card card) {
        String msg = who == Side.PLAYER ? "Вы открыли карту" : "Дилер открывает карту";
        out.printf("%s %s (%d)\n", msg, card, card.rank.points);
        printHands();
        out.println();
    }

    @Override
    public void onDealerTurn(Card card) {
        out.println();
        out.println("Ход дилера:");
        out.println(TURN_SEPARATOR);
        out.printf("Дилер открывает закрытую карту %s (%d)\n", card, card.rank.points);
        printHands();
        out.println();
    }

    private static final class CountingViewer implements CardViewer<String> {
        private int total;

        @Override
        public String cardWithPoints(Card card, int points) {
            if (total != -1) {
                total += points;
            }
            return String.format("%s (%d)", card, points);
        }

        @Override
        public String closedCard() {
            total = -1;
            return "<закрытая карта>";
        }
    }
}
