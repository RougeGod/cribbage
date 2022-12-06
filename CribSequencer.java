
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Subsequencer accepts a line full of integers, and prints out all unique
 * subsequences of those integers, including the empty set. It skips over all
 * non-integers. This implementation uses ArrayLists.
 *
 * @author Baxter Madore (A00454037)
 */
public class Subsequencer {

    public static ArrayList<ArrayList<Card>> metaList 
            = new ArrayList<ArrayList<Card>>();
    public static final Scanner userInput = new Scanner(System.in);

    /**
     * The main method simply acts as an entry point, printing the introduction,
     * then getting into the main loop of the program.
     *
     * @param cli command line input. This is ignored. Though it would be
     * reasonable to take the command line input as an inputted line, this
     * program does not do that.
     */
    public static void main(String[] cli) {
        printIntroduction();
        acceptLine(true);
        printResults();
    }

    /**
     * Parses a line of text, puts all of the integers into a List and prints
     * out a warning for any non-integers it encounters. After that, it sends
     * the list through to be processed.
     *
     * @param enteredLine the line that is peing parsed
     */
    public static void parseLine(String enteredLine) {
        ArrayList<Integer> output = new ArrayList<Integer>();
        String[] lineArray = enteredLine.split(" ");
        //run through the entire list, adding all integers to the
        //list that gets processed 
        for (int count = 0; count < lineArray.length; count++) {
            if (isInteger(lineArray[count])) {
                output.add(Integer.parseInt(lineArray[count]));
            } else {
                System.out.println("Skipped " + lineArray[count]
                        + " - Not an Integer");
            }
        }
        generateSubsequences(output);
    }

    /**
     * Prompts for and reads in a line of input from the user, then sends it
     * along to be parsed. If there is nothing in an entered line, quits the
     * program.
     *
     * @param firstTime whether this is the first time since the start of the
     * program that the method is being executed. Not particularly important but
     * I don't want the program to start off asking for "more numbers"
     */
    public static void acceptLine(boolean firstTime) {
        System.out.print("Enter " + (firstTime ? "some" : "more")
                        + " numbers >>> ");
        String lineFeed = userInput.nextLine();
        if (lineFeed.length() == 0) {
            System.out.println("Good-Bye!");
            System.exit(0);
        } else {
            parseLine(lineFeed);
            printResults();
            acceptLine(false);
        }

    }

    /**
     * Recursively determines all unique subsequences of a given List of
     * Integers, and puts them into the static main list (metaList). It does
     * this by first adding the sequence it's been given into the main list,
     * then removing each element in turn and getting all subsequences of those
     * recursively, unless the given list is length 0. The copious object
     * cloning is to avoid messing with referenced objects.
     *
     * @param theList the list of Integers to find the subsequences of.
     */
    public static void generateSubsequences(List<Integer> theList) {
        ArrayList subsequence = (ArrayList) theList;
        subsequence = (ArrayList) subsequence.clone();
        //will always run, even on length 0 Lists
        if (!metaList.contains(subsequence)) {
            metaList.add(subsequence);
        }
        ArrayList<Integer> recurse = (ArrayList<Integer>) subsequence.clone();
        //this loop doesn't start if the List is empty
        for (int count = 0; count < recurse.size(); count++) {
            //while we do remove the elements in turn, in order for
            //the recursion to come back and not have the item 
            //permanently removed, we must re-add the removed item
            Integer addBack = recurse.remove(count);
            generateSubsequences(recurse);
            recurse.add(count, addBack);
        }
    }
    /**
     * Tests whether a given String is a valid integer
     * 
     * @param candidate the string being tested for integer-ness
     * @return true if the candidate is an integer, false otherwise
     */
    public static boolean isInteger(String candidate) {
        try {
            Integer.parseInt(candidate);
        } catch (NumberFormatException no) {
            return false;
        }
        return true;
    }
    /**
     * Prints an introduction of myself and the program, as well as 
     * a brief pointer on how to use it. 
     */
    public static void printIntroduction() {
        System.out.println("Subsequence Calculator");
        System.out.println("----------------------");
        System.out.println("\nBy Baxter Madore (A00454037)\n");
        System.out.println("This program calculates all subsequences"
                + " of a line of numbers you enter.");
        System.out.println("Enter an empty line to end the program.\n");
    }

    /**
     * Prints all of the subsequences generated by generateSubsequences()
     * while also clearing the main list for reuse on the next line.  
     * 
     */
    public static void printResults() {
        System.out.println("Subsequences of " + metaList.get(0) + ": ");
        while (metaList.size() > 0) {
            System.out.println("\t- " + metaList.remove(0));
        }
        //juuuust in case it didn't catch everything
        metaList.clear();

    }
}

