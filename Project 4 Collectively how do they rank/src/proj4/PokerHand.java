package proj4;
import java.util.ArrayList;

public class PokerHand {

    public static final int HAND_SIZE = 5;
    private static final String FLUSH = "flush";
    private static final String TWO_PAIR = "two pair";
    private static final String ONE_PAIR = "one pair";
    private static final String HIGH_CARD = "high card";
    private static final int MAX_RANK_COUNT = 4;
    private static final int TRIPS_RANK_COUNT = 3;
    private static final int PAIR_RANK_COUNT = 2;

    ArrayList<Card> contents;

    public PokerHand() {
        this.contents = new ArrayList<Card>();
    }

    public PokerHand(ArrayList<Card> cardList) {
        this.contents = cardList;
    }

    /**
     * Adding a card to the PokerHand object
     * @param card the card we want to add to the hand
     */
    public void addCard(Card card){
        if (this.contents.size() < HAND_SIZE){
            this.contents.add(card);
        }
    }

    /**
     * Gets the card at an index of a PokerHand
     * @param index the index of the desired card
     * @return the card at the given index
     */
    public Card getIthCard(int index){
        if (index >= 0 && index < HAND_SIZE){
            return this.contents.get(index);
        }
        return null;
    }

    /**
     * Empties / resets the PokerHand object
     */
    public void emptyHand(){
        this.contents = new ArrayList<Card>();
    }

    /**
     * For counting a desired rank in the hand
     * @param rank the rank we want to be counting the frequency of
     * @return the number of occurrences of the rank in the hand
     */
    private int countRank(int rank){
        ArrayList<Integer> handRanks = new ArrayList<>();
        for (Card card : this.contents) {
            handRanks.add(card.getRank());
        }

        int count = 0;
        for (int i = 0; i < HAND_SIZE; i++){
            if (handRanks.get(i) == rank){
                count++;
            }
        }
        return count;
    }

    /**
     * For counting a desired suit in the hand
     * @param suit the suit we want to be counting the frequency of
     * @return the number of occurrences of the suit in the hand
     */
    private int countSuit(String suit){
        ArrayList<String> handSuits = new ArrayList<>();
        for (Card card : this.contents) {
            handSuits.add(card.getSuit());
        }

        int count = 0;
        for (int i = 0; i < HAND_SIZE; i++){
            if (handSuits.get(i).equals(suit)){
                count++;
            }
        }
        return count;
    }

    /**
     * Checking to see if the given hand is a flush or not.
     * @return If the given hand is a flush or not
     */
    private boolean isHandFlush(){
        int FLUSH_LENGTH = 5;
        ArrayList<String> handSuits = new ArrayList<>();

        for (Card card : this.contents){
            handSuits.add(card.getSuit());
        }

        return countSuit(handSuits.get(0)) == FLUSH_LENGTH;
    }

    /**
     * Used as a helper method throughout the program to sort a given ArrayList of type Integer
     * @param numbers a given ArrayList<Integer> we want to sort
     */
    private void sort(ArrayList<Integer> numbers){
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = numbers.size() - 1; j > i; j--) {
                if (numbers.get(j) > numbers.get(i)){
                    int helper = numbers.get(i);
                    numbers.set(i, numbers.get(j));
                    numbers.set(j, helper);
                }
            }
        }
    }

    /**
     * Adds two given ranks to a given ArrayList<Integer>, and can be used to assign the wanted count
     * variable in getPairs()
     * @param pairsList the given list of pairs from getPairs()
     * @param countOfRankCard the given rank of the countOfRank from getPairs()
     * @param currentCardRank the given rank of the current card from getPairs()
     * @return MAX_RANK_COUNT to assign to the desired count variable in getPairs()
     */
    private int getPairsHelper(ArrayList<Integer> pairsList, int countOfRankCard, int currentCardRank){
        pairsList.add(countOfRankCard);
        pairsList.add(currentCardRank);
        return MAX_RANK_COUNT;
    }

    /**
     * Gets the pairs within a given hand
     * @return A sorted list of the pairs in the given hand, or null if there are no pairs within the hand
     */
    private ArrayList<Integer> getPairs() {
        ArrayList<Integer> pairsList = new ArrayList<>(2);
        ArrayList<Integer> cardRanks = new ArrayList<>(5);

        for (Card card : this.contents) {
            cardRanks.add(card.getRank());
        }

        int countOfRank = countRank(cardRanks.get(0));
        int countOfRankCard = cardRanks.get(0);

        for (int currentCardRank : cardRanks){
            int currentRankCount = countRank(currentCardRank);

            if (countOfRank == PAIR_RANK_COUNT) {
                if (currentRankCount == TRIPS_RANK_COUNT) { // full house
                    countOfRank = getPairsHelper(pairsList, countOfRankCard, currentCardRank);
                }
                else if (currentRankCount == PAIR_RANK_COUNT && countOfRankCard != currentCardRank) { // two pair with different pairs
                    countOfRank = getPairsHelper(pairsList, countOfRankCard, currentCardRank);
                    countOfRankCard = currentCardRank;
                }
            }

            else if (countOfRank == TRIPS_RANK_COUNT && currentRankCount == PAIR_RANK_COUNT) { // full house
                countOfRank = getPairsHelper(pairsList, countOfRankCard, currentCardRank);
            }

            else if (currentRankCount == MAX_RANK_COUNT) { // four of a kind
                pairsList.add(currentCardRank);
                pairsList.add(currentCardRank);
                return pairsList;
            }

            else if (countOfRank < currentRankCount) {
                countOfRank = countRank(currentCardRank);
                countOfRankCard = currentCardRank;
            }
        }

        if (countOfRank == MAX_RANK_COUNT){
            sort(pairsList);
            return pairsList;
        }

        else if (countOfRank >= PAIR_RANK_COUNT){
            pairsList.add(countOfRankCard);
            return pairsList;
        }

        return null;
    }

    /**
     * Determines whether the given hand is a two pair, one pair, or just a high card hand
     * For this project, those types of hands are considered "paired hands", unlike a flush
     * @return The type of paired hand the given hand is, either a constant TWO_PAIR, ONE_PAIR, or HIGH_CARD
     */
    private String classifyingPairedHands(){
        if (this.getPairs() == null){
            return HIGH_CARD;
        }
        else if (this.getPairs().size() == 2){
            return TWO_PAIR;
        }
        else{
            return ONE_PAIR;
        }
    }

    /**
     * Classifies the type of the given hand
     * @return The type of the given hand, either a constant FLUSH or the value assigned by classifyingPairedHands()
     */
    public String typeOfHand(){
        if (this.isHandFlush()){
            return FLUSH;
        }
        else{
            return this.classifyingPairedHands();
        }
    }

    private ArrayList<Integer> compareHighCardHelper(){
        ArrayList<Integer> cardRanks = new ArrayList<>();
        for (Card card : this.contents) {
            cardRanks.add(card.getRank());
        }

        sort(cardRanks);
        return cardRanks;
    }

    /**
     * Comparing the card ranks between 2 given cards
     * @param other The second hand to compare
     * @return -1 if self is worth LESS than other, zero if they are worth the SAME,
     * and 1 if self is worth MORE than other, when both self and other are being compared on their card ranks.
     */
    private int compareHighCard(PokerHand other){
        ArrayList<Integer> handOneRanks = new ArrayList<>(this.compareHighCardHelper());
        ArrayList<Integer> handTwoRanks = new ArrayList<>(other.compareHighCardHelper());

        for (int cardIndex = 0; cardIndex < HAND_SIZE; cardIndex++){
            if (!handOneRanks.get(cardIndex).equals(handTwoRanks.get(cardIndex))) {
                if (handOneRanks.get(cardIndex) > handTwoRanks.get(cardIndex)){
                    return 1;
                }
                else if (handOneRanks.get(cardIndex) < handTwoRanks.get(cardIndex)){
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * Comparing the strength of 2 given hands classified as either two pair or one pair hands
     * @param other The second hand to compare
     * @return -1 if self is worth LESS than other, zero if they are worth the SAME,
     * and 1 if self is worth MORE than other when both self and other are categorized as two pair or one pair hands.
     */
    private int comparePairHands(PokerHand other){
        ArrayList<Integer> handOnePairs = new ArrayList<>(this.getPairs());
        ArrayList<Integer> handTwoPairs = new ArrayList<>(other.getPairs());

        for (int pairIndex = 0; pairIndex < handOnePairs.size(); pairIndex++) {
            if (handOnePairs.get(pairIndex) > handTwoPairs.get(pairIndex)) {
                return 1;
            }
            else if (handOnePairs.get(pairIndex) < handTwoPairs.get(pairIndex)) {
                return -1;
            }
        }
        return this.compareHighCard(other);
    }

    /**
     * To solve the issue of comparePairHands going to compareHighCard and making the reasons for
     * some incorrect choices wrong, if pairs are the same we return 0 and deal with in getCorrectReason
     */
    private int comparePairHandsCorrectReason(PokerHand other){
        ArrayList<Integer> handOnePairs = new ArrayList<>(this.getPairs());
        ArrayList<Integer> handTwoPairs = new ArrayList<>(other.getPairs());

        if (handOnePairs.size() == handTwoPairs.size()) {
            for (int pairIndex = 0; pairIndex < handOnePairs.size(); pairIndex++) {
                if (handOnePairs.get(pairIndex) > handTwoPairs.get(pairIndex)) {
                    return 1;
                }
                else if (handOnePairs.get(pairIndex) < handTwoPairs.get(pairIndex)) {
                    return -1;
                }
            }
            return 0; // pairs are equal
        }
        else if (handOnePairs.size() > handTwoPairs.size()) {
            return 1;
        }
        return -1;
    }

    /**
     * Determines how this hand compares to another hand, returns 1, -1, or zero depending on the comparison.
     * @param other The second hand to compare
     * @return -1 if self is worth LESS than other, zero if they are worth the SAME, and 1 if self is worth MORE than other.
     */
    public int compareTo(PokerHand other){
        String handOneType = this.typeOfHand();
        String handTwoType = other.typeOfHand();

        if (handOneType.equals(FLUSH)){
            if (!handTwoType.equals(FLUSH)){
                return 1;
            }
            else{
                return this.compareHighCard(other);
            }
        }

        else if (handOneType.equals(TWO_PAIR)){
            if (handTwoType.equals(FLUSH)){
                return -1;
            }
            else if (handTwoType.equals(TWO_PAIR)){
                return this.comparePairHands(other);
            }
            else{
                return 1;
            }
        }

        else if (handOneType.equals(ONE_PAIR)){
            if (handTwoType.equals(FLUSH) || handTwoType.equals(TWO_PAIR)){
                return -1;
            }
            else if (handTwoType.equals(ONE_PAIR)){
                return this.comparePairHands(other);
            }
            else{
                return 1;
            }
        }

        else{
            if (!handTwoType.equals(HIGH_CARD)){
                return -1;
            }
            else{
                return this.compareHighCard(other);
            }
        }
    }

    /**
     * Gets the correct reason for why one hand is better than another when comparing
     * @param other The second hand we are comparing
     * @return The reason why one hand is better than the other in a string, depending on each hand
     */
    public String getCorrectReason(PokerHand other) {
        String handOneType = this.typeOfHand();
        String handTwoType = other.typeOfHand();

        int comparisonResult = this.compareTo(other);

        String winnerType, loserType;
        int winnerInt, loserInt;

        if (comparisonResult == 1){
            winnerType = handOneType;
            loserType = handTwoType;
            winnerInt = 1;
            loserInt = 2;
        }
        else if (comparisonResult == -1){
            winnerType = handTwoType;
            loserType = handOneType;
            winnerInt = 2;
            loserInt = 1;
        }
        else {
            return "Hand 1 and Hand 2 are equal with the same cards";
        }

        if (winnerType.equals(FLUSH)) {
            if (loserType.equals(FLUSH)) {
                return "Hand " + winnerInt + " wins on high card over Hand " + loserInt + " since they are both flushes";
            }
            else {
                return "Hand " + winnerInt + " is a flush while Hand " + loserInt + " is a " + loserType;
            }
        }
        else if (winnerType.equals(TWO_PAIR)) {
            if (!loserType.equals(TWO_PAIR)) {
                return "Hand " + winnerInt + " is a two pair while Hand " + loserInt + " is a " + loserType;
            }
            else {
                if (this.comparePairHandsCorrectReason(other) == comparisonResult) {
                    return "Hand " + winnerInt + " has a better two pair than Hand " + loserInt;
                }
                else {
                    return "Hand " + winnerInt + " wins on high card over Hand " + loserInt + " since they have equal two pairs";
                }
            }
        }
        else if (winnerType.equals(ONE_PAIR)) {
            if (loserType.equals(ONE_PAIR)) {
                if (this.comparePairHandsCorrectReason(other) == comparisonResult) {
                    return "Hand " + winnerInt + " has a better one pair than Hand " + loserInt;
                }
                else {
                    return "Hand " + winnerInt + " wins on high card over Hand " + loserInt + " since they have equal one pairs";
                }
            }
            else {
                return "Hand " + winnerInt + " is a one pair while Hand " + loserInt + " a high card";
            }
        }
        else {
            return "Hand " + winnerInt + " wins on high card over Hand " + loserInt;
        }
    }

    /**
     * @return the formatted way we want to display a PokerHand object when printing
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
     * Seeing whether 2 objects are equal PokerHand's
     * @param other the other Object we want to compare to
     * @return whether the Objects are equal or not
     */
    public boolean equals(Object other){
        if (other == null){
            if (this == null){
                return true;
            }
            return false;
        }
        if (other == this){
            return true;
        }
        if (!(other instanceof PokerHand)){
            return false;
        }

        PokerHand otherHand = (PokerHand) other;

        return this.compareTo(otherHand) == 0;
    }

    public static void main(String[] args){
        // testing

        Deck deck = new Deck();
        deck.shuffle();

        PokerHand pokerHand = new PokerHand();
        PokerHand pokerHand2 = new PokerHand();

        for(int i = 0; i < HAND_SIZE; i++){
            pokerHand.addCard(deck.deal());
            pokerHand2.addCard(deck.deal());
        }

        System.out.println(pokerHand);
        //System.out.println("The third card in hand 1: " + pokerHand.getIthCard(2));
        System.out.println(pokerHand2);

        System.out.println(pokerHand.typeOfHand());
        System.out.println(pokerHand2.typeOfHand());

        if (pokerHand.compareTo(pokerHand2) == 1)
            System.out.println("Top hand wins");
        else if (pokerHand.compareTo(pokerHand2) == -1)
            System.out.println("Bottom hand wins");
        else
            System.out.println("It's a tie");
        System.out.println(pokerHand.getCorrectReason(pokerHand2));
    }
}
