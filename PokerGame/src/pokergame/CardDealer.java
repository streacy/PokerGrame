
package pokergame;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Random;

public class CardDealer {
   private static final String[] allCardsArr = {"A.S", "A.D", "A.H", "A.C", "2.S", "2.D", "2.H", "2.C", "3.S", "3.D", "3.H",
      "3.C", "4.S", "4.D", "4.H", "4.C", "5.S", "5.D", "5.H", "5.C", "6.S", "6.D", "6.H", "6.C", "7.S", "7.D", "7.H",
      "7.C", "8.S", "8.D", "8.H", "8.C", "9.S", "9.D", "9.H", "9.C", "10.S", "10.D", "10.H", "10.C", "J.S", "J.D",
      "J.H", "J.C", "Q.S", "Q.D", "Q.H", "Q.C", "K.S", "K.D", "K.H", "K.C"};

   private static HashSet<String> allCards =new HashSet<String>(Arrays.asList(allCardsArr));  
   private static HashSet<String> availableCards = new HashSet<String>(Arrays.asList(allCardsArr));

  public CardDealer() {
   
  }
  public static Card getCard() {
    int size = availableCards.size();
    int item = new Random().nextInt(size);
    int i = 0;
    for (String card: availableCards) {
      if (i == item){
        availableCards.remove(card);
        Card newCard = new Card(card);
        return newCard;
      }
      i++;
    }
    return null;
  }
  // This resets the pack so that all cards are now included
  public static void shuffle() {
    availableCards = allCards;
  }  
}
