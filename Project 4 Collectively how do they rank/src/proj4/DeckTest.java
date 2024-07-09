package proj4;

public class DeckTest {
    public static void main(String[] args) {
        Testing.setVerbose(false);

        deckTests();

        Testing.finishTests();
    }

    public static void deckTests(){
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        Deck deck3 = new Deck();

        Testing.assertEquals("deck constructor", deck1, deck2);

        for (int i = 0; i < 3; i++){
            deck1.deal();
            deck2.deal();
        }
        Testing.assertEquals("deck deal with cards", deck1, deck2);
        Testing.assertEquals("deck isEmpty", false, deck1.isEmpty());

        deck1.gather();
        Testing.assertEquals("deck gather", deck1, deck3);

        deck1.shuffle();
        Testing.assertEquals("if the shuffled deck is different than an original deck", false, deck1.equals(deck3)); //shuffled deck different than normal
        Testing.assertEquals("if the deck is still 52 cards after being shuffled", 52, deck1.size());
    }
}
