#Cribbage Program
Command line program that can either tell you the optimal cards to discard (either 1 out of 5 or 2 out of 6) before a cribbage hand. or can count the score of a hand for you.  
#Usage
Compile Card.java, Deck.java, CribScorer.java, and Statistics.java, then run CribStrategy.java

The program will print all of the instructions that you need when first run. 

Just type in the card codes for each of the cards that you have in your hand. 
The card code is the rank of the card (ten is "T"), followed by the suit. 

For instance, if I were in a two player game, holding the Queen of Hearts, Five of Spades, 8 of Diamonds, Ace of Hearts, Ten of Clubs, and 2 of Diamonds, I would type
>>>QH 5S 8D AH TC 2D
Spaces and capitalization are both optional, "qh  5s8dahtc2d" would also work for the example above.

The program will then print three options, one to maximize the mean score, one to maximize the median score, and one to maximize the highest possible score, depending on the possible cuts. 
Usually these are the same. 

Type "score" to enter a hand to be counted for points. 
Type "exit" to leave the program. 

##Limitations
- The program has no concept of pegging, so it cannot determine which hands are good pegging hands or what to do if you're super close to the end of the board. 
- The program does not take into account whose crib it currently is and thus may recommend throwing points to the opponent. If I come back and improve this, that is the next thing that I will work on.
