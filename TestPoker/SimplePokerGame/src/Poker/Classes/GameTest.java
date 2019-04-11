/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;

import java.io.IOException;

/**
 *
 * @author tgreenid
 */
public class GameTest 
{

  public static void main(String[] args) throws IOException
	{
		
		// make game
		Game game = Game.getGame();
				
		// play game
		game.play();

	}

}