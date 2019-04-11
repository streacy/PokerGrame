/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tex.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author tgreenid
 */
public class Deck 
{
  private List<Card> cards = new ArrayList<Card>();
  
  public Deck ()
  {
    for (Suit suit : Suit.values())
    {
      for (Value value : Value.values())
      {
        Card card = new Card(suit, value);
        cards.add(card);
      }
    }
  }
  
  public List<Card> getCards()
  {
    return cards;
  }
  
  public void shuffleDeck()
  {
    Collections.shuffle(cards);
  }
}