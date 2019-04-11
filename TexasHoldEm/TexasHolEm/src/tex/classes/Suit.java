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
public enum Suit
{
  HEARTS(1), 
  CLUBS(2), 
  DIAMONDS(3), 
  SPADES(4);
  
  private int suitValue;
  
  private Suit (int suitValue)
  {
    this.suitValue = suitValue;
  }

  public int getSuitValue()
  {
    return suitValue;
  }
  
}