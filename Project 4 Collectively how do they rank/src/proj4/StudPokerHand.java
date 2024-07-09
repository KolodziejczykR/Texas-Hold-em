package proj4;
import java.util.ArrayList;

public class StudPokerHand {
    public static final int STUD_AND_COMMUNITY_CARD_SIZE = 7;
    public static final int STUD_HAND_SIZE = 2;

    CommunityCardSet communityCards;
    ArrayList<Card> contents;

    public StudPokerHand(CommunityCardSet cc){
        this.contents = new ArrayList<>();
        this.communityCards = cc;
    }

    public StudPokerHand(CommunityCardSet cc, ArrayList<Card> cardList){
        this.contents = cardList;
        this.communityCards = cc;
    }

    /**
     * Adding a card to the StudPokerHand object
     * @param card the card we want to add to the hand
     */
    public void addCard(Card card){
        if (this.contents.size() < STUD_HAND_SIZE){
            this.contents.add(card);
        }
    }

    /**
     * Empties / resets the StudPokerHand object
     */
    public void emptyHand(){
        this.contents = new ArrayList<>();
    }

    /**
     * Gets the card at an index of a StudPokerHand
     * @param index the index of the desired card
     * @return the card at the given index
     */
    public Card getIthCard(int index){
        if (index >= 0 && index < STUD_HAND_SIZE){
            return this.contents.get(index);
        }
        return null;
    }

    /**
     * Gives the size of the StudPokerHand object
     * @return the number of cards in the stud poker hand and community cards associated with it
     */
    public int size(){
        return this.contents.size() + this.communityCards.size();
    }

    private ArrayList<PokerHand> getAllFiveCardHands() {
        ArrayList<Card> allCards = new ArrayList<>();

        for (int i = 0; i < STUD_AND_COMMUNITY_CARD_SIZE; i++) {
            if (i < STUD_HAND_SIZE){
                allCards.add(this.getIthCard(i));
            }
            else{
                allCards.add(this.communityCards.getIthCard(i-STUD_HAND_SIZE));
            }
        }

        ArrayList<PokerHand> allHandCombos = new ArrayList<>();
        return getAllFiveCardHandsRecursion(allCards, 0, new ArrayList<>(), allHandCombos);
    }

    /**
     * The recursive helper function for getAllFiveCardHands
     */
    private static ArrayList<PokerHand> getAllFiveCardHandsRecursion(ArrayList<Card> allCards, int startIndex, ArrayList<Card> currentCombination, ArrayList<PokerHand> allHandCombos) {
        // base case
        if (currentCombination.size() == PokerHand.HAND_SIZE) {
            allHandCombos.add(new PokerHand(new ArrayList<>(currentCombination)));
            return allHandCombos;
        }

        // recursive case
        for (int i = startIndex; i < allCards.size(); i++) {
            currentCombination.add(allCards.get(i));
            getAllFiveCardHandsRecursion(allCards, i + 1, currentCombination, allHandCombos);
            currentCombination.remove(currentCombination.size()-1); // backtracking
        }
        return allHandCombos;
    }


    private PokerHand getBestFiveCardHand() {
        ArrayList<PokerHand> hands =  this.getAllFiveCardHands();
        PokerHand bestSoFar = hands.get(0);

        for (int i = 1; i < hands.size(); i++) {
            if (hands.get(i).compareTo(bestSoFar) == 1) {
                bestSoFar = hands.get(i);
            }
        }
        return bestSoFar;
    }

    /**
     * Determines how this hand compares to another hand, using the
     * community card set to determine the best 5-card hand it can
     * make. Returns positive, negative, or zero depending on the comparison.
     *
     * @param other The hand to compare this hand to
     * @return a negative number if this is worth LESS than other, zero
     * if they are worth the SAME, and a positive number if this is worth
     * MORE than other
     */
    public int compareTo(StudPokerHand other){
        PokerHand handOneBest = this.getBestFiveCardHand();
        PokerHand handTwoBest = other.getBestFiveCardHand();

        return handOneBest.compareTo(handTwoBest);
    }

    /**
     * Gets the correct reason for why one hand is better than another when comparing
     * @param other The second hand we are comparing
     * @return The reason why one hand is better than the other in a string, depending on each hand
     */
    public String getCorrectReason(StudPokerHand other){
        PokerHand handOneBest = this.getBestFiveCardHand();
        PokerHand handTwoBest = other.getBestFiveCardHand();

        return handOneBest.getCorrectReason(handTwoBest);
    }

    /**
     * @return the formatted way we want to display a StudPokerHand object when printing
     */
    public String toString(){
        if (this.contents.size() == 0){
            return "No Cards in Hand";
        }

        StringBuilder result = new StringBuilder();
        for (Card card : this.contents){
            result.append(card.toString());
            result.append(", ");
        }
        return result.substring(0, result.length() - 2);
    }

    /**
     * Seeing whether 2 objects are equal StudPokerHand's
     * @param other the other Object we want to compare to
     * @return whether the Objects are equal or not
     */
    public boolean equals(Object other) {
        if (other == null){
            return false;
        }
        if (other == this){
            return true;
        }
        if (!(other instanceof StudPokerHand)){
            return false;
        }

        StudPokerHand otherHand = (StudPokerHand) other;

        PokerHand bestHand1 = this.getBestFiveCardHand();
        PokerHand bestHand2 = otherHand.getBestFiveCardHand();

        return bestHand1.compareTo(bestHand2) == 0;
    }

    public static void main(String[] args) {
        // tests

        Deck deck = new Deck();
        deck.shuffle();

        CommunityCardSet communityCards = new CommunityCardSet();
        ;       for (int j = 0; j < PokerHand.HAND_SIZE; j++) {
            communityCards.addCard(deck.deal());
        }

        StudPokerHand studHand1 = new StudPokerHand(communityCards);
        StudPokerHand studHand2 = new StudPokerHand(communityCards);

        for (int j = 0; j < STUD_HAND_SIZE; j++) {
            studHand1.addCard(deck.deal());
            studHand2.addCard(deck.deal());
        }

        System.out.println("Community Cards: " + communityCards);
        System.out.println("Stud Hand 1: " + studHand1);
        System.out.println("Stud Hand 2: " + studHand2);
        System.out.println();

        PokerHand bestHand = studHand1.getBestFiveCardHand();
        PokerHand bestHand2 = studHand2.getBestFiveCardHand();

        System.out.println("Best Hand with Stud 1: " + bestHand + " / type of hand = " + bestHand.typeOfHand());
        System.out.println("Best Hand with Stud 2: " + bestHand2 + " / type of hand = " + bestHand2.typeOfHand());

        if (studHand1.compareTo(studHand2) == 1) {
            System.out.println("Hand 1 wins");
        }
        if (studHand1.compareTo(studHand2) == -1) {
            System.out.println("Hand 2 wins");
        }
        if (studHand1.compareTo(studHand2) == 0) {
            System.out.println("Tie");
        }

        System.out.println(studHand1.getCorrectReason(studHand2));
    }
}