package proj4;

import java.util.ArrayList;
import java.util.Arrays;

public class CommunityCardSetTest {
    public static void main(String[] args) {
        Testing.setVerbose(false);

        communityTest();

        Testing.finishTests();
    }

    public static void communityTest(){
        Deck deck = new Deck();
        CommunityCardSet communityCards1 = new CommunityCardSet();

        Testing.assertEquals("testing toString() with empty hand", "No Cards in Hand", communityCards1.toString());

        ArrayList<Card> cardList = new ArrayList<>(Arrays.asList(new Card(2,0), new Card(2,1),
                                            new Card(2,2), new Card(2,3), new Card(3,0))); // first 5 cards in deck
        CommunityCardSet communityCards2 = new CommunityCardSet(cardList);

        for (int i = 0; i < PokerHand.HAND_SIZE; i++){
            Card newCard = deck.deal();
            communityCards1.addCard(newCard);
        }

        Testing.assertEquals("both constructors test", communityCards1, communityCards2);
        Testing.assertEquals("size test", 5, communityCards1.size());

        Card checkCard = new Card(2, 3); // fourth card in the deck
        Testing.assertEquals("getIthCard 3", checkCard, communityCards1.getIthCard(3));

        Testing.assertEquals("testing toString()", "2 of Spades, 2 of Hearts, 2 of Clubs, 2 of Diamonds, 3 of Spades", communityCards1.toString());
    }
}
