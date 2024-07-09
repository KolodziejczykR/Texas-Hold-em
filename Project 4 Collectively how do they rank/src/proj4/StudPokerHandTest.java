package proj4;

import java.util.ArrayList;
import java.util.Arrays;

public class StudPokerHandTest {
    public static void main(String[] args) {
        Testing.setVerbose(false);

        studCompareToTests();
        otherTests();

        Testing.finishTests();
    }

    public static void studCompareToTests(){
        CommunityCardSet communityCards = createCommunityCards("3d", "5s", "7h", "4d", "kd");

        StudPokerHand hand1 = creatingStudHand("7d", "ad", communityCards); // flush
        StudPokerHand hand2 = creatingStudHand("2d", "ad", communityCards); // flush
        StudPokerHand hand3 = creatingStudHand("7s", "5d", communityCards); // two pair
        StudPokerHand hand4 = creatingStudHand("6s", "qc", communityCards); // high card
        StudPokerHand hand5 = creatingStudHand("ks", "ad", communityCards); // one pair
        StudPokerHand hand6 = creatingStudHand("5d", "4c", communityCards); // two pair
        StudPokerHand hand7 = creatingStudHand("7d", "as", communityCards); // one pair
        StudPokerHand hand8 = creatingStudHand("ks", "ad", communityCards); // one pair



        studCompareToTest("flush tie x4", hand1, hand2, 1);
        studGetCorrectReasonTest("flush tie x4 reason", hand1, hand2,
                        "Hand 1 wins on high card over Hand 2 since they are both flushes");
        studCompareToTest("flush vs two pair", hand1, hand3, 1);
        studCompareToTest("two pair vs high card", hand3, hand4, 1);
        studCompareToTest("two pair with different pairs", hand6, hand3, -1);
        studCompareToTest("one pair with different pairs", hand7, hand5, -1);
        studCompareToTest("one pair tie", hand5, hand8, 0);
        studCompareToTest("flush vs one pair", hand8, hand2, -1);
        studCompareToTest("high card vs one pair", hand4, hand7, -1);




    }

    public static void studCompareToTest(String msg, StudPokerHand hand1, StudPokerHand hand2, int expectedValue) {
        int result = hand1.compareTo(hand2);
        Testing.assertEquals(msg, expectedValue, result);
    }

    public static void studGetCorrectReasonTest(String msg, StudPokerHand hand1, StudPokerHand hand2, String expectedReason){
        String reason = hand1.getCorrectReason(hand2);
        Testing.assertEquals(msg, expectedReason, reason);
    }

    public static void otherTests(){
        Deck deck = new Deck();
        CommunityCardSet communityCards = createCommunityCards("3d", "4c", "7h", "4s", "kd");

        ArrayList<Card> cardList = new ArrayList<Card>(Arrays.asList(new Card(2,0), new Card(2,1)));
        StudPokerHand studPokerHand1 = new StudPokerHand(communityCards, cardList); // has first 2 cards in deck
        StudPokerHand studPokerHand2 = new StudPokerHand(communityCards); // empty at first

        for (int i = 0; i < StudPokerHand.STUD_HAND_SIZE; i++){
            studPokerHand2.addCard(deck.deal());
        }

        Testing.assertEquals("testing both constructors equivalence", studPokerHand1, studPokerHand2);
        Testing.assertEquals("testing the size", 7, studPokerHand1.size());

        Card test1 = new Card(2,1); // second card in studPokerHand1
        Testing.assertEquals("testing getIthCard", test1, studPokerHand1.getIthCard(1));

        studPokerHand1.emptyHand();
        // 5 is expected because it empties the 2 card stud hand, leaving the 5 card community set that is apart of size
        Testing.assertEquals("testing emptyHand", 5, studPokerHand1.size());

        Testing.assertEquals("testing toString()", "2 of Spades, 2 of Hearts", studPokerHand2.toString());
        Testing.assertEquals("testing toString() with empty hand", "No Cards in Hand", studPokerHand1.toString());
    }

    private static CommunityCardSet createCommunityCards(String card1, String card2, String card3, String card4, String card5) {
        CommunityCardSet communityCards = new CommunityCardSet();
        String[] cards = new String[] {card1, card2, card3, card4, card5};

        int[] cardRanks = createStudHandHelperRanks(cards);
        int[] cardSuits = createStudHandHelperSuits(cards);

        for (int i = 0; i < cards.length; i++) {
            Card card = new Card(cardRanks[i], cardSuits[i]);
            communityCards.addCard(card);
        }

        return communityCards;
    }

    private static StudPokerHand creatingStudHand(String card1, String card2, CommunityCardSet communityCards) {
        StudPokerHand hand = new StudPokerHand(communityCards);

        String[] cards = new String[] {card1, card2};

        int[] cardRanks = createStudHandHelperRanks(cards);
        int[] cardSuits = createStudHandHelperSuits(cards);

        for (int i = 0; i < cards.length; i++) {
            Card card = new Card(cardRanks[i], cardSuits[i]);
            hand.addCard(card);
        }

        return hand;
    }

    private static int[] createStudHandHelperRanks(String[] cards){
        int[] cardRanks = new int[cards.length];

        for (int i=0; i<cards.length; i++){
            String currentCardRank = cards[i].substring(0,1).toLowerCase();

            if (currentCardRank.equals("a")){
                cardRanks[i] = 14;
            }
            else if (currentCardRank.equals("k")){
                cardRanks[i] = 13;
            }
            else if (currentCardRank.equals("q")){
                cardRanks[i] = 12;
            }
            else if (currentCardRank.equals("j")){
                cardRanks[i] = 11;
            }
            else if (currentCardRank.equals("t")){
                cardRanks[i] = 10;
            }
            else{
                cardRanks[i] = Integer.parseInt(cards[i].substring(0,1));
            }
        }
        return cardRanks;
    }

    private static int[] createStudHandHelperSuits(String[] cards){
        int[] cardSuits = new int[cards.length];
        for (int i=0; i<cards.length; i++){
            String currentCardSuit = cards[i].substring(1).toLowerCase();

            if (currentCardSuit.equals("s")){
                cardSuits[i] = 0;
            }
            else if (currentCardSuit.equals("h")){
                cardSuits[i] = 1;
            }
            else if (currentCardSuit.equals("c")){
                cardSuits[i] = 2;
            }
            else{
                cardSuits[i] = 3;
            }
        }
        return cardSuits;
    }
}
