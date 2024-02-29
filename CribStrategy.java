import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class CribStrategy {

    public static final Scanner userInput = new Scanner(System.in);
    public static boolean easterEgg = false;
    public static CribScorer scorer = new CribScorer();

    public static void main(String[] cli) {
        printHelp();
        acceptData();
        }//end of main

    public static void printHelp() {
    System.out.println("This program calculates the best cards to throw out of your");
    System.out.println("cribbage hand, using a few different methods.\n");
    System.out.println("To tell it what your cards are, type your \"card codes\" in when it displays >>>.");
    System.out.println("The card code is the rank of the card and the suit.");
    System.out.println("For example, 9 of Spades is \"9S\", 10 of Hearts is \"TH\", and Ace of Clubs is \"AC\"");
    System.out.println("Spaces and capitalization are optional, but a hand must have 5 or 6 cards.\n");
    System.out.println("AVERAGE SCORE: This one is applicable to most situations, especially");
    System.out.println("in the early and mid game. It takes into account score across every");
    System.out.println("possible cut in the deck.\n");
    System.out.println("MEDIAN SCORE: Similar to average score, but uses the middle score of possible cuts,");
    System.out.println("instead of the average of all cuts.\n");
    System.out.println("MAXIMUM SCORE: Losing late in the game and need a miraculous cut?");
    System.out.println("This is the score to pay attention to.\n");
    System.out.println("LIMITATIONS: This program does not take into account pegging, or the effect");
    System.out.println("of putting the card(s) in your crib or your opponents' crib, it only seeks to");
    System.out.println("maximize the amount of points that you get when counting your hand at the end.\n");
    System.out.println("SPECIAL OPTIONS: ");
    System.out.println("Type HELP to read this help message.");
    System.out.println("Type SCORE to have the program count your final hand for you.");
    System.out.println("Type EXIT to exit the program.");
    }

    public static void acceptData() {
        String inputtedLine = "";
        while (true) {
            System.out.print(">>> ");
            inputtedLine = userInput.nextLine().toUpperCase().replaceAll("\\s+","");
            //remove spaces and capitalizations from the user-entered string
            if (inputtedLine.equals("HELP")) {
                printHelp();
                }
            else if (inputtedLine.equals("SCORE")) {
                acceptScoreData();
                }
            else if (inputtedLine.equals("EXIT")) {
                System.exit(0);
                }
            else if (inputtedLine.equals("XKCD")) {
                easterEgg = true;
                }
            else {
                processHand(inputtedLine);
                }
            }
        }//end of acceptData

    public static void processHand(String input) throws IllegalArgumentException{
        int handLength;
        if ((input.length() != 12) && (input.length() != 10)) {
            throw new IllegalArgumentException("Illegal hand length: " + input.length());
        } else {
            handLength = input.length() / 2;
        }
        ArrayList<Card> hand = new ArrayList<Card>(handLength);
        hand.add(new Card(input.charAt(0), input.charAt(1)));
        hand.add(new Card(input.charAt(2), input.charAt(3)));
        hand.add(new Card(input.charAt(4), input.charAt(5)));
        hand.add(new Card(input.charAt(6), input.charAt(7)));
        hand.add(new Card(input.charAt(8), input.charAt(9)));
        if (handLength == 6) {
            hand.add(new Card(input.charAt(10), input.charAt(11)));
        }

        Deck possibleCuts = new Deck();
        possibleCuts.initializeDeckWithout(hand, false);
        int[][] scores = new int[1][1]; //if uninitialized here, throws a "may not be initialized" error later.
        String[] options = new String[1];
        //run through possibilities differently depending on how many cards are in hand
        if (handLength == 5) {
            options = new String[5]; //this array matches the first dimension of scores array
            scores = new int[5][52 - handLength];
            for (int outer = 0; outer < handLength; outer++) {
                Card[] speculativeHand = new Card[4];
                //getScore wants an array of cards instead of an ArrayList
                options[outer] = hand.get(outer).toString(); //before it is removed
                ArrayList<Card> aListHand = new ArrayList<Card>(hand); //copy hand to a new arraylist
                aListHand.remove(outer);
                speculativeHand = aListHand.toArray(new Card[4]);
                for (int inner = 0; inner < (52 - 5); inner++) {
                    scores[outer][inner] = scorer.getScore(speculativeHand, possibleCuts.gimme(inner));
                }
            }

        }
        if (handLength == 6) {
        options = new String[15]; //each element holds two cards
        int optionTracker = 0;
        //15 possible inputtedLinebinations of cards to throw out, each with two cards that we need to keep track of
        //so can display to the user what cards lead to optimal results
        scores = new int[15][52 - handLength];
        //mmmm, triple nested loops
        for (int firstCard = 0; firstCard < handLength - 1; firstCard++) {
            for (int secondCard = firstCard + 1; secondCard < handLength; secondCard++) {
                Card[] speculativeHand = new Card[4];
                options[optionTracker] =  (hand.get(firstCard).toString() + " and " + hand.get(secondCard).toString());
                ArrayList<Card> aListHand = new ArrayList<Card>(hand);
                aListHand.remove(secondCard);
                aListHand.remove(firstCard);
                //they MUST be removed in this order! removing card 1 first leads to the list being shifted
                speculativeHand = aListHand.toArray(new Card[4]);
                for (int cut = 0; cut < (52 - 6); cut++) {
                    scores[optionTracker][cut] = scorer.getScore(speculativeHand, possibleCuts.gimme(cut));
                    }
                optionTracker++;
                }
            }
        }

    //find the mean, median, maximum, and meandian (if enabled) for each possibility
    getAndDisplayResults(scores, options);
    }

    private static void getAndDisplayResults(int[][] scores, String[] options) {
        int maxAvgOption = 0;
        double maxAvg = 0.0;
        int maxMedOption = 0;
        int maxMed = 0;
        int maxMaxOption = 0;
        int maxMax = 0;
        int maxMeandianOption = 0;
        double maxMeandian = 0;
        Statistics statKeeper = new Statistics();
        for (int count = 0; count < scores.length; count++) {
            double avg = statKeeper.getArithmeticMean(scores[count]);
            int med = statKeeper.getMedian(scores[count]);
            int max = statKeeper.getMax(scores[count]);
            double meandian = statKeeper.getMeandian(scores[count]);
            if (avg > maxAvg) {
                maxAvgOption = count;
                maxAvg = avg;
                }
            if (med > maxMed) {
                maxMedOption = count;
                maxMed = med;
                }
            if (max > maxMax) {
                maxMaxOption = count;
                maxMax = max;
                }
            if (meandian > maxMeandian) {
                maxMeandianOption = count;
                maxMeandian = meandian;
                }
            }
        System.out.format("For the best AVERAGE score of %.02f throw out the " +
                           options[maxAvgOption] + ".%n", maxAvg);
        System.out.format("For the best MEDIAN score of %d throw out the " +
                           options[maxMedOption] + ".%n", maxMed);
        System.out.format("For the best MAXIMUM score of %d throw out the " +
                           options[maxMaxOption] + ".%n", maxMax);
        if (easterEgg) {
            System.out.format("For the best GEOTHMETIC MEANDIAN score of %.02f throw out the " +
                           options[maxMeandianOption] + ".%n", maxMeandian);
            }
        }


    public static void acceptScoreData() {

        Card[] hand = new Card[4];
        Card cut;
        System.out.print("Enter the card code of the cut: ");
        String inputtedLine = userInput.nextLine().toUpperCase().replaceAll("\\s+","");
        cut = new Card(inputtedLine.charAt(0), inputtedLine.charAt(1));
        System.out.print("Enter the card code of the four hand cards: ");
        inputtedLine = userInput.nextLine().toUpperCase().replaceAll("\\s+","");
        hand[0] = new Card(inputtedLine.charAt(0), inputtedLine.charAt(1));
        hand[1] = new Card(inputtedLine.charAt(2), inputtedLine.charAt(3));
        hand[2] = new Card(inputtedLine.charAt(4), inputtedLine.charAt(5));
        hand[3] = new Card(inputtedLine.charAt(6), inputtedLine.charAt(7));
        System.out.println(" Hand: " + Arrays.toString(hand) + "\n  Cut:  " + cut);
        System.out.println("Score: " + scorer.getScore(hand, cut));
        }

}//end of class
