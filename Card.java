public class Card implements Comparable<Card> {
    
    public int  face; //this one goes up to 13
    public int value; //this one stops at 10
    public char  suit; //D, H, S, C
    
    public Card(int face, char suit) throws IllegalArgumentException {
        switch (suit) {
            case 'D': this.suit = 'D'; break;
            case 'd': this.suit = 'D'; break;
            case 'H': this.suit = 'H'; break;
            case 'h': this.suit = 'H'; break;
            case 'C': this.suit = 'C'; break;
            case 'c': this.suit = 'C'; break;
            case 'S': this.suit = 'S'; break;
            case 's': this.suit = 'S'; break;
            default: throw new IllegalArgumentException("Suit was invalid"); 
            }          
        if ((face < 1)||(face > 13)) {
            throw new IllegalArgumentException("Card value is out of bounds");                 
            }
        else {
            this.face = face;
            if (face >= 10) {
                value = 10;                    
                }
            else {
                value = face;                    
                }          
            }
        }
    
   public Card(char face, char suit) throws IllegalArgumentException {
        switch (suit) {
            case 'D': this.suit = 'D'; break;
            case 'd': this.suit = 'D'; break;
            case 'H': this.suit = 'H'; break;
            case 'h': this.suit = 'H'; break;
            case 'C': this.suit = 'C'; break;
            case 'c': this.suit = 'C'; break;
            case 'S': this.suit = 'S'; break;
            case 's': this.suit = 'S'; break;
            default: throw new IllegalArgumentException("Suit was invalid"); 
            } 
        switch (face) {
            case 'A': this.face = 1; break;
            case '1': this.face = 1; break;
            case '2': this.face = 2; break;
            case '3': this.face = 3; break;
            case '4': this.face = 4; break;
            case '5': this.face = 5; break;
            case '6': this.face = 6; break;
            case '7': this.face = 7; break;
            case '8': this.face = 8; break;
            case '9': this.face = 9; break;
            case 'T': this.face = 10; break;
            case '0': this.face = 10; break;
            case 'J': this.face = 11; break;
            case 'Q': this.face = 12; break;
            case 'K': this.face = 13; break;
            default: throw new IllegalArgumentException("Illegal Card Face Value"); 
            }  
            if (this.face >= 10) {
                value = 10;                    
                }
            else {
                value = this.face;                    
                }          
        
        } 
    
    @Override
    public String toString() {
        String output;
        switch (face) {
            case 13: output = "King"; break;
            case 12: output = "Queen"; break;
            case 11: output = "Jack"; break;
            case  1: output = "Ace"; break;
            default: output = Integer.toString(face);           
            }  
        output = output + " of ";
        switch (suit) {
            case 'H': output = output + "hearts"; break; 
            case 'D': output = output + "diamonds"; break;
            case 'S': output = output + "spades"; break;
            case 'C': output = output + "clubs";  break;        
            }
        return output;
        }
    @Override
    public int compareTo(Card other) {
        return (this.face - other.face); //suits don't matter for sorting, only for rouges and flushes which are special checks
        }
    @Override
    public boolean equals(Object other) {
        try {
            Card thing = (Card)other;        
            return ((thing.suit == this.suit)&&(thing.face == this.face));    
            }       
        catch (Exception broken) { //if the compared object is not a card, it should throw a ClassCastException or something like that
            return false;            
            }
        }
    }

