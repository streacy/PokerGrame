/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tex.classes;

/**
 *
 * @author tgreenid
 */
public class Card implements Comparable<Card>
{
  private Suit suit;
  private Value value;
  
  public Card (Suit suit, Value value)
  {
    this.suit = suit;
    this.value = value;
  }
  
  public Suit getSuit()
  {
    return suit;
  }
  public Value getValue()
  {
    return value;
  }

  @Override
  public int compareTo(Card o)
  {
    // You will need to implement the proper code here
    // so that the cards can be properly sorted
    return 0;
  }
}
