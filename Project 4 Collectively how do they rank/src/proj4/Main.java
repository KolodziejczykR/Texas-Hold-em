package proj4;
import java.util.Scanner;

/*
    I affirm that I have carried out the attached academic endeavors with full academic honesty,
    in accordance with the Union College Honor Code and the course syllabus.

    Ryan Kolodziejczyk

*/

public class Main {

    private static int playerPoints;

    private static void remakeStudHand(Deck deck, StudPokerHand studHand){
        studHand.emptyHand();

        for(int i = 0; i < StudPokerHand.STUD_HAND_SIZE; i++){
            studHand.addCard(deck.deal());
        }
    }

    /**
     * Runs the game allowing the user to guess between 2 hands of which is stronger,
     * giving the player points for every correct guess
     *
     * Prints when the player either guesses wrong or beats the deck, with their respective point total
     * Also if the user guesses wrong, prints a reason for why the guess was wrong for those 2 specific hands
     */
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        CommunityCardSet communityCards = new CommunityCardSet();

        for (int i = 0; i < CommunityCardSet.COMMUNITY_CARD_SIZE; i++) {
            communityCards.addCard(deck.deal());
        }

        while(deck.size() > StudPokerHand.STUD_HAND_SIZE * 2){
            StudPokerHand handOne = new StudPokerHand(communityCards);
            StudPokerHand handTwo = new StudPokerHand(communityCards);

            remakeStudHand(deck, handOne);
            remakeStudHand(deck, handTwo);

            System.out.println("The Community Cards are... ");
            System.out.println(communityCards);
            System.out.println("Hole Hand 1: " + handOne);
            System.out.println("Hole Hand 2: " + handTwo + "\n");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Do you think Hand 1 or Hand 2 is better, or are they equal?");
            System.out.println("Put 1 for Hand 1, 2 for Hand 2, or 0 for equal: ");
            int userChoice = scanner.nextInt();

            if (userChoice == 1 && handOne.compareTo(handTwo) == 1) {
                playerPoints++;
                System.out.println("Nice job, you got in right!");
                System.out.println("--------------------------------------------------------------------------");
            }

            else if (userChoice == 2 && handOne.compareTo(handTwo) == -1) {
                playerPoints++;
                System.out.println("Nice job, you got in right!");
                System.out.println("--------------------------------------------------------------------------");
            }

            else if (userChoice == 0 && handOne.compareTo(handTwo) == 0) {
                playerPoints++;
                System.out.println("Nice job, you got in right!");
                System.out.println("--------------------------------------------------------------------------");
            }

            else {
                System.out.println("Sorry, you got this one wrong");
                System.out.println("The correct reasoning: " + handOne.getCorrectReason(handTwo));

                if (playerPoints == 1) {
                    System.out.println("You got: 1 point");
                }
                else {
                    System.out.println("You got: " + playerPoints + " points");
                }
                return;
            }
        }
        System.out.println("Congratulations, you got the entire deck right. " + playerPoints + " points!");
    }
}
