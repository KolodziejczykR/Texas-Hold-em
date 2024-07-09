package proj4;

public class CardTest {
    public static void main(String[] args) {
        Testing.setVerbose(false);

        cardTests();

        Testing.finishTests();
    }

    private static void cardTests(){
        Card card1 = new Card(14, 3);
        Card card2 = new Card("AcE", "Diamonds");
        Testing.assertEquals("seeing if both constructors are equal", card1, card2);

        int rank = card1.getRank();
        Testing.assertEquals("getRank test", rank, 14);

        String suit = card1.getSuit();
        Testing.assertEquals("getSuit test", suit, "Diamonds");

        String cardPrinted = card1.toString();
        Testing.assertEquals("toString test", cardPrinted, "Ace of Diamonds");
    }
}
