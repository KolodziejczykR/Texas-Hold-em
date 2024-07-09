package proj4;
import java.util.ArrayList;

public class CommunityCardSet {

    public static final int COMMUNITY_CARD_SIZE = PokerHand.HAND_SIZE;

    ArrayList<Card> contents;

    public CommunityCardSet() {
        this.contents = new ArrayList<Card>();
    }

    public CommunityCardSet(ArrayList<Card> cardList) {
        this.contents = cardList;
    }

    /**
     * Adding a card to the CommunityCardSet object
     * @param card the card we want to add to the hand
     */
    public void addCard(Card card) {
        if (this.contents.size() < PokerHand.HAND_SIZE) {
            this.contents.add(card);
        }
    }

    /**
     * Gives the size of the community cards
     * @return the number of cards in the community cards
     */
    public int size(){
        return this.contents.size();
    }

    /**
     * Gets the card at an index of a CommunityCardSet
     * @param index the index of the desired card
     * @return the card at the given index
     */
    public Card getIthCard(int index){
        if (index >= 0 && index < PokerHand.HAND_SIZE){
            return this.contents.get(index);
        }
        return null;
    }

    /**
     * @return the formatted way we want to display a CommunityCardSet object when printing
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
     * Seeing whether 2 objects are equal CommunityCardSet's
     * @param other the other Object we want to compare to
     * @return whether the Objects are equal or not
     */
    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if (other == this){
            return true;
        }
        if (!(other instanceof CommunityCardSet)){
            return false;
        }

        CommunityCardSet otherSet = (CommunityCardSet) other;

        if (this.contents.size() == otherSet.contents.size()){
            for(int i = 0; i < COMMUNITY_CARD_SIZE; i++){
                if(!this.getIthCard(i).equals(otherSet.getIthCard(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();
        CommunityCardSet communityCardSet = new CommunityCardSet();

        for (int i = 0; i < PokerHand.HAND_SIZE; i++){
            communityCardSet.addCard(deck.deal());
        }

        System.out.println(communityCardSet);
    }
}
