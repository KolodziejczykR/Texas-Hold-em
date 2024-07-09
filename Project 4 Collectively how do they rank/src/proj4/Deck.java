package proj4;
import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> deck;
    private int nextToDeal;
    private static final int DECK_SIZE = 52;

    public Deck() {
        this.deck = new ArrayList<Card>();
        this.nextToDeal = 0;

        for (int rank : Card.RANKS) {
            for (int suit = 0; suit < Card.SUITS.length; suit++) {
                Card card = new Card(rank, suit);
                this.deck.add(card);
            }
        }
    }

    /**
     * Gets the size of the deck
     * @return the number of dealable cards left in the deck
     */
    public int size(){
        return DECK_SIZE - this.nextToDeal;
    }

    /**
     * @return true if the deck is empty and false if not
     */
    public boolean isEmpty(){
        return this.deck.size() == 0;
    }

    /**
     * Deals a card from the deck
     * @return The card dealt from the deck
     */
    public Card deal(){
        if (this.nextToDeal < this.deck.size()){
            Card cardDealt = this.deck.get(this.nextToDeal);
            this.nextToDeal++;
            return cardDealt;
        }
        return null;
    }

    /**
     * Resets the deck to its original, un-shuffled state
     */
    public void gather(){
        this.nextToDeal = 0;
    }

    private static void swap(ArrayList<Card> deck, int start, int change) {
        Card helperCard = deck.get(start);
        deck.set(start, deck.get(change));
        deck.set(change, helperCard);
    }

    /**
     * Shuffles the deck using the helper method swap()
     */
    public void shuffle() {
        Random r = new Random();
        for (int boundary = this.nextToDeal; boundary < this.deck.size(); boundary++) {
            int change = boundary + r.nextInt(this.deck.size() - boundary);
            swap(this.deck, boundary, change);
        }
    }

    /**
     * @return the formatted way we want to display a Deck object when printing
     */
    public String toString(){
        StringBuilder result = new StringBuilder();

        for (int card = nextToDeal; card < this.size(); card++) {
            result.append(this.deck.get(card));
            result.append(", ");
        }
        return result.substring(0, result.length()-2);
    }

    /**
     * Seeing whether 2 objects are equal decks, if the cards are in the right order
     * @param other the object we care comparing to
     * @return whether the two objects are equal decks
     */
    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if (other == this){
            return true;
        }
        if (!(other instanceof Deck)){
            return false;
        }

        Deck otherDeck = (Deck) other;

        for (int card = 0; card < DECK_SIZE; card++) {
            if (!this.deck.get(card).equals(otherDeck.deck.get(card))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        // testing

        Deck deck1 = new Deck();

        System.out.println(deck1.size());
        System.out.println(deck1);
        deck1.shuffle();
        System.out.println(deck1);


        for(int i = 0; i < 5; i++){
            System.out.print(deck1.deal() + "  ");
        }
        System.out.println();

        deck1.shuffle();
        System.out.println(deck1);
        System.out.println(deck1.size());

        deck1.gather();
        System.out.println(deck1);
        System.out.println(deck1.size());
    }
}
