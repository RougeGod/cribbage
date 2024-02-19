import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>(52);

    public Deck() {
        initializeDeck();
        }

    public void initializeDeck() {
        for (int count = 1; count <= 13; count++) {
            deck.add(new Card(count, 'S'));
            deck.add(new Card(count, 'D'));
            deck.add(new Card(count, 'H'));
            deck.add(new Card(count, 'C'));
        }
        shuffleDeck(52);
    }

    //mainly used when the user specifies the hand they have.
    //shuffling remaining cards may be unnecessary if we're just running through all possible cuts.
    public void initializeDeckWithout(ArrayList<Card> excluded, boolean shuffle) {
        deck = new ArrayList<Card>(52 - excluded.size());
        //reset the deck to add the cards
        Card card;
        for (int count = 1; count <= 13; count++) {
            card = new Card(count, 'S');
            if (!excluded.contains(card)) {
                deck.add(card);
            }
            card = new Card(count, 'D');
            if (!excluded.contains(card)) {
                deck.add(card);
            }
            card = new Card(count, 'H');
            if (!excluded.contains(card)) {
                deck.add(card);
            }
            card = new Card(count, 'C');
            if (!excluded.contains(card)) {
                deck.add(card);
            }
        }
        if (shuffle) {
            shuffleDeck(deck.size());
        }
    }

    private void shuffleDeck(int deckLength) {
        Random rand = new Random();
        Card temp;
        for (int count = 0; count < deckLength; count++) {
            int r = rand.nextInt(deckLength);
            temp = deck.get(count);
            deck.set(count, deck.get(r));
            deck.set(r, temp);
            }
        } //end of shuffle

    public Card pop() {
        return deck.remove(deck.size() - 1);
    }

    public Card gimme(int index) {
        return deck.get(index);
    }
}
