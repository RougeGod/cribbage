import java.util.Arrays;
import java.util.ArrayList;

public class CribScorer {
    private static ArrayList<ArrayList<Card>> sequenceList = new ArrayList<ArrayList<Card>>(26);
    //where 26 is the number of subsequences af a 5-card hand

    public static int getScore (Card[] hand, Card cut) {
        int score = 0;
        checkLegality(hand, cut);
        score += checkRouge(hand, cut);
        score += countFlush(hand, cut);
        score += findRuns(hand, cut);
        score += scorePairs(hand, cut);
        score += score15s(hand, cut);
        return score;
        }

    //should probably be a boolean
    private static void checkLegality(Card[] hand, Card cut) {
        if (hand.length != 4) {
            System.out.println("Incorrect hand length");
            System.exit(37);
            }

        for (int count = 0; count < 4; count++) {
            if (hand[count] == null) {
                System.out.println("Null card detected in spot " + count);
                System.exit(37);
                }
            }
            if (cut == null) {
                System.out.println("Null cut detected");
                System.exit(37);
                }
        }

    private static int checkRouge(Card[] hand,Card cut) {
        for (int count = 0; count < 4; count++) {
            if ((hand[count].face == 11) && (hand[count].suit == cut.suit)) {
                return 1;
                }
            }
        return 0;
        }

    private static int countFlush(Card[] hand, Card cut) {
        //if all four cards in the Hand have the same suit, advance
        if (((hand[0].suit == hand[1].suit) && (hand[2].suit == hand[3].suit)) && (hand[1].suit == hand[2].suit)) {
            //return 5 or 4 depending on whether or not the cut suit matches one of the hand cards' suit
            return ((cut.suit == hand[0].suit) ? 5 : 4);
            }
        //if the four hand cards don't have the same suit
        return 0;
        }

    //this method finds the longest run, if one exists, and returns 0 if no runs in the hand
    private static int findRuns (Card[] hand, Card cut) {
        Card[] fullHand = Arrays.copyOf(hand, 5);
        fullHand[4] = new Card(cut.face, cut.suit);
        //probably will cause a bug in future as we are duplicating a card because i'm paranoid about references messing things up.
        ArrayList<Card> handList = new ArrayList<Card>(5);
        int longestRun = 0;
        int runLen = 0;

        Arrays.sort(fullHand);
        for (int count = 0; count < 5; count++) {
            handList.add(fullHand[count]);
            }
        sequenceList.clear();
        generateSubsequences(handList, 3);
        for (int count = 0; count < sequenceList.size(); count++) {
            runLen = runLength(sequenceList.get(count));
            longestRun = Math.max(runLen, longestRun);
            }
        if (longestRun > 0) {
            return countRuns(handList, longestRun);
            }
        else {
            return 0;
            }
        }

    //this one takes in just the array with 5 cards. the cut is not seperated
    //and the hand has already been sorted
    private static int countRuns(ArrayList<Card> handList, int longestRun) {
        int runPoints = 0;
        if (longestRun < 3) { //no runs, so no points
             return 0;
             }
        if (longestRun == 5) {
             //there can't be duplicates if all five cards are in sequence
             //(i.e. 8, 9, 10, J, Q cannot have double-runs)
             return 5;
             }
        //if there is a run...
        sequenceList.clear();
        generateSubsequences(handList, longestRun);


        int runLen = 0;
        //go through all sequences, and if they are a run, add the points. 
        //This properly counts double runs (e.g. 6, 6, 7, 8)
        for (int count = 0; count < sequenceList.size(); count++) {
            runLen = runLength(sequenceList.get(count));
            runPoints += runLen;
            }
        return runPoints;

    }

    //runLength will return either the length of the run
    //or 0 if the whole thing isn't a run or isn't long enough
    private static int runLength(ArrayList<Card> theList) {
        Card[] cardArray = new Card[theList.size()];
        int arrayFill = 0;
        while (theList.size() > 0) {
            cardArray[arrayFill] = (Card)theList.remove(0);
            arrayFill++;
            }
        for (int count = 0; count < cardArray.length; count++) {
            if (cardArray[count].face != (cardArray[0].face + count)) {
                return 0;
                }
            }
            //if and only if the run is long enough is it counted.
            //this shouldn't be an issue but might be depending on whether
            //the full subsequence list is generated ahead of time.
            return ((cardArray.length >= 3) ? cardArray.length : 0);
        }

    private static void generateSubsequences(ArrayList<Card> theList, int minLength) {
        ArrayList subsequence = theList;
        subsequence = (ArrayList) subsequence.clone();
        //will always run, even on length 0 Lists
        if ((!sequenceList.contains(subsequence)) && (subsequence.size() >= minLength)) {
            sequenceList.add(subsequence);
        }
        ArrayList<Card> recurse = (ArrayList<Card>) subsequence.clone();
        //this loop doesn't start if the List is empty
        for (int count = 0; count < recurse.size(); count++) {
            //while we do remove the elements in turn, in order for
            //the recursion to come back and not have the item
            //permanently removed, we must re-add the removed item
            Card addBack = recurse.remove(count);
            generateSubsequences(recurse, minLength);
            recurse.add(count, addBack);
        }

    }

    private static int scorePairs(Card[] hand, Card cut) {
        int pairs = 0;
        checkLegality(hand, cut);
        Card[] fullHand = Arrays.copyOf(hand, 5);
        fullHand[4] = new Card(cut.face, cut.suit);
        for (int out = 0; out < 4; out++) {
            for (int in = out + 1; in < 5; in++) {
                if ((fullHand[out].face == fullHand[in].face) && (out != in)) {
                    pairs++;
                    }
                }
            }
        return pairs*2;
        }

    private static int score15s(Card[] hand, Card cut) {
        int score = 0;
        Card[] fullHand = Arrays.copyOf(hand, 5);
        fullHand[4] = new Card(cut.face, cut.suit);
        //probably will cause a bug in future as we are duplicating a card because i'm paranoid about references messing things up.
        ArrayList<Card> handList = new ArrayList<Card>(5);
        for (int count = 0; count < 5; count++) {
            handList.add(fullHand[count]);
            }
        sequenceList.clear();
        generateSubsequences(handList, 2);
        for (int count = 0; count < sequenceList.size(); count++) {
            if (is15(sequenceList.get(count))) {
                score += 2;
                }
            }
        return score;
        }

    private static boolean is15(ArrayList<Card> handList) {
        int runningTotal = 0;
        while (handList.size() > 0) {
            runningTotal += handList.remove(0).value;
            }
        if (runningTotal == 15) {
            return true;
            }
        else {
            return false;
            }
        }

}//end of class
