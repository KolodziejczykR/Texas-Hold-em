package proj4;

public class PokerHandTest {

    public static void main(String[] args) {
        Testing.setVerbose(false);

        flushTests();
        twoPairTests();
        onePairTests();
        highCardTests();
        otherTests();

        Testing.finishTests();
    }

    private static void flushTests(){
        PokerHand hand1 = creatingHand("qd", "qs", "9s", "5h", "4h");
        PokerHand hand2 = creatingHand("2d", "jd", "td", "8d", "7d");
        PokerHand hand3 = creatingHand("3d", "jd", "td", "8d", "7d");
        PokerHand hand4 = creatingHand("3h", "jh", "th", "8h", "7h");

        compareToTest("flush with high card tie x4", hand2, hand3, -1);
        getCorrectReasonTest("flush with high card tie x4 reason", hand2, hand3,
                    "Hand 2 wins on high card over Hand 1 since they are both flushes");

        compareToTest("flush true tie", hand4, hand3, 0);
        getCorrectReasonTest("flush true tie reason", hand4, hand3,"Hand 1 and Hand 2 are equal with the same cards");

        compareToTest("flush vs one pair", hand4, hand1, 1);
        getCorrectReasonTest("flush vs one pair reason", hand4, hand1, "Hand 1 is a flush while Hand 2 is a one pair");
    }

    private static void twoPairTests(){
        PokerHand hand1 = creatingHand("2D", "3S", "5S", "5H", "3H");
        PokerHand hand2 = creatingHand("2d", "3s", "5S", "5H", "3H");
        PokerHand hand3 = creatingHand("ad", "5s", "8s", "aH", "2H");
        PokerHand hand4 = creatingHand("3d", "jd", "td", "8d", "7d");
        PokerHand hand5 = creatingHand("2d", "4s", "5S", "5H", "4H");

        compareToTest("same two pair hand", hand1, hand2, 0);

        compareToTest("two pair vs one pair", hand1, hand3, 1);
        getCorrectReasonTest("two pair vs one pair reason", hand1, hand3, "Hand 1 is a two pair while Hand 2 is a one pair");

        compareToTest("two pair vs flush", hand2, hand4, -1);
        getCorrectReasonTest("two pair vs flush reason", hand2, hand4, "Hand 2 is a flush while Hand 1 is a two pair");

        compareToTest("two pair different second pair", hand1, hand5, -1);
        getCorrectReasonTest("two pair different second pair reason", hand1, hand5, "Hand 2 has a better two pair than Hand 1");
    }

    private static void onePairTests(){
        PokerHand hand1 = creatingHand("2d", "3s", "5S", "5H", "3H");
        PokerHand hand2 = creatingHand("Qd", "7s", "5s", "5H", "3H");
        PokerHand hand3 = creatingHand("Qd", "8s", "5s", "5H", "3H");
        PokerHand hand4 = creatingHand("ad", "5s", "8s", "aH", "2H");
        PokerHand hand5 = creatingHand("qd", "qs", "9s", "5h", "4h");
        PokerHand hand6 = creatingHand("4s", "jh", "th", "8h", "7h");
        PokerHand hand7 = creatingHand("Qd", "7s", "5s", "5H", "3H");


        compareToTest("same one pair, different last high card", hand2, hand3, -1);
        getCorrectReasonTest("same one pair, different last high card reason", hand2, hand3,
                    "Hand 2 wins on high card over Hand 1 since they have equal one pairs");

        compareToTest("different one pairs", hand4, hand5, 1);
        getCorrectReasonTest("different one pairs reason", hand4, hand5, "Hand 1 has a better one pair than Hand 2");

        compareToTest("one pair vs two pair", hand4, hand1, -1);
        getCorrectReasonTest("one pair vs two pair reason", hand4, hand1, "Hand 2 is a two pair while Hand 1 is a one pair");

        compareToTest("one pair vs high card", hand5, hand6, 1);
        getCorrectReasonTest("one pair vs high card reason", hand5, hand6, "Hand 1 is a one pair while Hand 2 a high card");

        compareToTest("same one pair hand", hand2, hand7, 0);
    }

    private static void highCardTests(){
        PokerHand hand1 = creatingHand("qd", "qs", "9s", "5h", "4h");
        PokerHand hand2 = creatingHand("3d", "jh", "th", "8h", "7h");
        PokerHand hand3 = creatingHand("4s", "jh", "th", "8h", "7h");
        PokerHand hand4 = creatingHand("4s", "jh", "th", "8h", "7h");
        PokerHand hand5 = creatingHand("4s", "ah", "th", "8h", "7h");

        compareToTest("high card tie x4", hand3, hand2, 1);
        getCorrectReasonTest("high card tie x4 reason", hand3, hand2, "Hand 1 wins on high card over Hand 2");

        compareToTest("high card true tie", hand3, hand4, 0);
        compareToTest("high card vs one pair", hand3, hand1, -1);

        compareToTest("high card no tie", hand4, hand5, -1);
        getCorrectReasonTest("high card no tie reason", hand4, hand5, "Hand 2 wins on high card over Hand 1");

    }

    private static void otherTests() {
        Deck deck = new Deck();

        PokerHand hand = new PokerHand();

        for(int i = 0; i < PokerHand.HAND_SIZE; i++){
            Card card = deck.deal();
            hand.addCard(card);
        }

        Card checkCard = new Card(2, 2); // third card in the deck
        Testing.assertEquals("getting card from hand", checkCard, hand.getIthCard(2));

        Testing.assertEquals("testing toString()", "2 of Spades, 2 of Hearts, 2 of Clubs, 2 of Diamonds, 3 of Spades", hand.toString());

        hand.emptyHand();
        Testing.assertEquals("emptying the hand", 0, hand.contents.size());
        Testing.assertEquals("testing toString() with empty hand", "No Cards in Hand", hand.toString());
    }

    private static PokerHand creatingHand(String card1, String card2, String card3, String card4, String card5) {
        PokerHand hand = new PokerHand();

        String[] cards = new String[] {card1, card2, card3, card4, card5};

        int[] cardRanks = createHandHelperRanks(cards);
        int[] cardSuits = createHandHelperSuits(cards);

        for (int i = 0; i < cards.length; i++) {
            Card card = new Card(cardRanks[i], cardSuits[i]);
            hand.addCard(card);
        }

        return hand;
    }

    private static int[] createHandHelperRanks(String[] cards){
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

    private static int[] createHandHelperSuits(String[] cards){
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

    private static void compareToTest(String msg, PokerHand hand1, PokerHand hand2, int expectedValue) {
        int result = hand1.compareTo(hand2);
        Testing.assertEquals(msg, expectedValue, result);
    }

    private static void getCorrectReasonTest(String msg, PokerHand hand1, PokerHand hand2, String expectedReason){
        String reason = hand1.getCorrectReason(hand2);
        Testing.assertEquals(msg, expectedReason, reason);
    }

}
