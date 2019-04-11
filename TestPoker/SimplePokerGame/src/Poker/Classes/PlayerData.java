/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;

/**
 *
 * @author Brian
 */
public class PlayerData {
    private final int ID;
    private final String playThreadname;
    private Card[] playerhand = new Card[5];
    private double bet;
    private double chips;
    
    
    public PlayerData(int id, String name, int val){
        this.ID = id;
        this.playThreadname = name;
        this.bet = val;
    }
    
    public void setBet(double newbet){
        this.bet = newbet;
    }
        
    public void addChips(double amt){
        this.chips = amt;
    }
    
    public void updateHand(Card[] cards){
        this.playerhand = cards;
    }
    
    
}
