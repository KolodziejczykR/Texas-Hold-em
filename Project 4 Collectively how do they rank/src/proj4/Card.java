package proj4;

public class Card {
    private int rank;
    private final String suit;

    public static final int[] RANKS = {2,3,4,5,6,7,8,9,10,11,12,13,14};
    public static final String[] SUITS = {"Spades", "Hearts", "Clubs", "Diamonds"};
    public static final String[] RANK_NAMES = {"two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "jack", "queen", "king", "ace"};

    /**
     * Creates a Card with a given rank and suit.
     *
     * @param rank The rank of the card, which must be between 2 and 14, inclusive.
     * @param suit The suit of the card, which must be 0=SPADES, 1=HEARTS, 2=CLUBS, or 3=DIAMONDS.
     */
    public Card (int rank, int suit){
        this.rank = rank;
        this.suit = SUITS[suit];
    }

    /**
     * Creates a Card with a given rank and suit.
     *
     * @param rank whole cards (2-10) can either be spelled
     * out like "two" or numeric like "2". Case-insensitive.
     * @param suit "Spades", "Hearts", "Clubs", or "Diamonds"
     */
    public Card (String rank, String suit){
        this.suit = suit;

        rank = rank.toLowerCase();

        try{
            this.rank = Integer.parseInt(rank);
        }
        catch (NumberFormatException spelledOut){
            for (int i = 0; i < RANK_NAMES.length; i++){
                if (RANK_NAMES[i].equals(rank)){
                    this.rank = i+2; // "two" is at index 0, so i+2
                }
            }
        }
    }

    /**
     * @return The rank of the given card
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * @return The suit of the given card
     */
    public String getSuit() {
        return this.suit;
    }

    /**
     * Gets the full name of the rank for face cards and the aces
     * @return The full name of the rank for the given card
     */
    private String getFullRank() {
        String fullRank;
        int rank = this.getRank();

        if (rank == 11) {
            fullRank = "Jack";
        }
        else if (rank == 12) {
            fullRank = "Queen";
        }
        else if (rank == 13) {
            fullRank = "King";
        }
        else if (rank == 14) {
            fullRank = "Ace";
        }
        else{
            fullRank = String.valueOf(rank);
        }

        return fullRank;
    }

    /**
     * @return the formatted way we want to display a Card object when printing
     */
    public String toString(){
        return this.getFullRank() + " of " + this.getSuit();
    }

    /**
     * Checks to see if one card is equal to another
     * @param other the card we are comparing to
     * @return true or false whether the cards are equal or not
     */
    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if (other == this){
            return true;
        }
        if (!(other instanceof Card)){
            return false;
        }

        Card otherCard = (Card) other;

        if (this.getRank() == otherCard.getRank()){
            if (this.getSuit().equals(otherCard.getSuit())){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // testing

        Card card1 = new Card(11, 1);
        Card card2 = new Card("Jack", "Hearts");
        System.out.println("Card 1 rank #: " + card1.getRank());
        System.out.println("Card 2 full rank: " + card2.getFullRank());
        System.out.println("Card 1 suit: " + card1.getSuit());
        System.out.println(card1);
        System.out.println(card2);
        System.out.println(card1.equals(card2));
    }
}