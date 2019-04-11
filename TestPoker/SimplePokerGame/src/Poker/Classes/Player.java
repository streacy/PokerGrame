/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;

/**
 *
 * @author tgreenid
 */
public class Player 
{   private final int HAND_SIZE = 5;


	// gets 5 cards from deck
	public Card[] draw(Deck deck)
	{
		Card[] hand = deck.deal();
		return hand;
	}
	
	// switches card for a new card
	public Card redraw(int counter, Deck deck)
	{
		Card card = deck.redeal();
		return card;
	}
        
        
        
        
    
}


