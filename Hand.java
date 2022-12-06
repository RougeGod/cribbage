import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Hand { 
    private Card[] deck = new ArrayList<Card>(52);
    private Card cut; //has to be seperate
    private ArrayList<Card> hand = new ArrayList<Card>(6); //two of these should be null when scoring
    private int numInHand; //does not include cut
    private ArrayList<ArrayList<Card>> metaList = new ArrayList<ArrayList<Card>>(20);  
    private boolean runFlag = false;
    
    public Hand() {
        //initialize deck
        runFlag = false;
        for (int count = 0; count < 13; count++) {
            deck.add = new Card(count + 1, 'S');
            }
        for (int count = 0; count < 13; count++) {
            deck.add = new Card(count + 1, 'D');
            }
        for (int count = 0; count < 13; count++) {
            deck.add = new Card(count + 1, 'H');
            }
        for (int count = 0; count < 13; count++) {
            deck.add = new Card(count + 1, 'C');
            }
        //shuffle deck and deal 5 cards. 
        shuffleDeck();
        for (int count = 51; count >= 47; count++) {
            hand.add(deck.remove(count));
            numInHand++;
            }
        }

    //TODO: After initial testing, add support for 6-card hands (will be a boolean constructor)
    //numInHand is pretty useless now
    
    private static shuffleDeck() {
        Random rand = new Random();
        Card temp;
        for (int count = 0; count < 52; count++) {
            r = rand.nextInt(52);
            temp = deck[count];
            deck[count] = deck[r];
            deck[r] = temp
            }        
        } //end of shuffle
    
    //generates subsequences to put them in the metaList
    //this is necessary to count all of the 15s
    public static void generateSubsequences(List<Card> theList) {
        ArrayList subsequence = (ArrayList) theList;
        subsequence = (ArrayList) subsequence.clone();
        //will only run if more than one card, as single cards never score 15
        if (!metaList.contains(subsequence) && (subsequence.size() > 1)) {
            metaList.add(subsequence);
        }
        ArrayList<Card> recurse = (ArrayList<Card>)subsequence.clone();
        //this loop doesn't start if the List is empty
        for (int count = 0; count < recurse.size(); count++) {
            //while we do remove the elements in turn, in order for
            //the recursion to come back and not have the item 
            //permanently removed, we must re-add the removed item
            Card addBack = recurse.remove(count);
            generateSubsequences(recurse);
            recurse.add(count, addBack);
        }
        
    } //end of generatesubequences. 
    
    public static int countScore(List<Card> theList) {
        generateSubsequences(theList);
        }
    
    

    
}
