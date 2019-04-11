/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab5;

/**
 *
 * @author mnithiya
 */
import java.util.Scanner;

public class SimpleUI implements UserInterface {
    private BlackjackGame game;
    private Scanner user = new Scanner(System.in);

  @Override
    public void setGame(BlackjackGame game) {
        this.game = game;
    }

  @Override
    public void display() {
        System.out.println("\nHouse holds:");
        for (Card i:game.getHouseCards().getCards()) {
            System.out.println(i);
        }
        System.out.println("\nYou Hold:");
        for (Card i:game.getYourCards().getCards()){
            System.out.println (i);
        }
    }

  @Override
    public boolean hitMe() {
        if (game.score(game.getYourCards()) > 21) {
            return false;
        }
        System.out.println("Another card? y for yes and n for no");
        String p = user.next();
        
        if (p.equals("y") || p.equals("Y")) {
            return true;
        }
        return false;   
    }

  @Override
    public void gameOver() {
        int yourScore;
        int houseScore;
        System.out.println("Game Over: \nHouse holds: \n");
        for (Card i:game.getHouseCards().getCards()) {
            System.out.println(i + "\n");
        }
        System.out.println("You Hold: \n");
        for (Card i:game.getYourCards().getCards()){
            System.out.println (i + "\n");
        }
        houseScore = game.score(game.getHouseCards());
        yourScore = game.score(game.getYourCards());
        System.out.println ("Your Score = " + yourScore + "\nHouse Score = " + houseScore);
        if (yourScore > 21) {
            System.out.println ("House Wins");
        }
        else if (yourScore <= houseScore && houseScore <= 21){
            System.out.println ("House Wins");
        }
        else {
            System.out.println ("You Win!");
        }
    }

}
