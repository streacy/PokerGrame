
package pokergame;


public class Card {
  private String card;
  private String rank;
  private String suit;

  /**
   * AS = Ace Spades, AH = Ace Hearts 2H = Two Hearts, 2D = Two Diamonds And so
   * forth.
   * 
   * @param card
   */
  public Card(String card) {
    this.card = card;
    String[] arr = card.split("\\.");
    this.rank = arr[0];
    this.suit = arr[1];
  }

  public String getCard() {
    return this.card;
  }
  
  public String getRank() {
      return this.rank;
  }
  
  public String getSuit() {
      return this.suit;
  }
  public int getSuitVal(){
      switch(this.suit){
          case "D":
             return 1;
          case "C":
              return 2;
          case "H":
              return 3;
          case "S":
              return 4;
          default:
              return 0;
                  
      }
  }
  public int getRankVal(){
      switch(this.rank){
          case "A":
              return 14;
          case "K":
              return 13;
          case "Q":
              return 12;
          case "J":
              return 11;
          default:
              return Integer.parseInt(this.rank);
      }
  }
  
  public String toString() { 
      String printRank = this.rank;
      if (printRank.equals("A")){
          printRank = "Ace";
      } else if (printRank.equals("J")){
          printRank = "Jack";
      } else if (printRank.equals("Q")){
          printRank = "Queen";
      } else if (printRank.equals("K")){
          printRank = "King";
      }
      
      String printSuit = this.suit;
      if (printSuit.equals("S")){
          printSuit = "Spades";
      } else if (printSuit.equals("D")){
          printSuit = "Diamonds";
      } else if (printSuit.equals("C")){
          printSuit = "Clubs";
      } else if (printSuit.equals("H")){
          printSuit = "Hearts";
      }
      return printRank + " of " + printSuit;
  } 
}
