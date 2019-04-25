/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokergame;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.security.PrivateKey;
import java.security.PublicKey;


public class House {
  public static final int ROYAL_FLUSH = 9000000; 
  public static final int STRAIGHT_FLUSH = 8000000; 
  public static final int FOUR_OF_A_KIND = 7000000; 
  public static final int FULL_HOUSE     = 6000000; 
  public static final int FLUSH          = 5000000;  
  public static final int STRAIGHT       = 4000000;   
  public static final int SET            = 3000000;    
  public static final int TWO_PAIRS      = 2000000;     
  public static final int ONE_PAIR       = 1000000;      
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  private static final int maxNumPlayers = 5;
  private static int check = 0;
  private static int pot = 0;
  private static int turn = 0;
  private static int folded = 0;
  private static final clientThread[] threads = new clientThread[maxNumPlayers];
  private static RSA keys = new RSA();
  private static final CardDealer dealer = new CardDealer();
  private static clientThread winner = null;
  private static PrivateKey priv = null;
  private static PublicKey pub = null;
  private static String[] playerSessionKeys;

  public static void main(String args[]) throws Exception {

    int port = 8765;
    
    if (args.length < 1) {
      System.out.println("House connected, now using port number=" + port);
    } else {
      port = Integer.valueOf(args[0]).intValue();
    }

    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.out.println(e);
    }

    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxNumPlayers; i++) {
          if (threads[i] == null) {
            keys = new RSA();
            keys.generateKey();
            pub = keys.PUB_KEY;
            priv = keys.PRIV_KEY;
            int threadId = i;
            (threads[i] = new clientThread(clientSocket, threads, pub, priv, threadId)).start();
    
            break;
          }
        }
        if (i == maxNumPlayers) {
          PrintStream output = new PrintStream(clientSocket.getOutputStream());
          output.println("Game is full, try again later.");
          output.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }

    }
  }
 public int getFolded() {
    return folded;
  }
 
   public void setFolded() {
    folded ++;
  }
  public int getPot() {
    return pot;
  }

  public Card getCard() {
    return dealer.getCard();
  }

  public void setPot(int bet) {
    pot += bet;
  }

  public int getCheck() {
    return check;
  }

  public void setCheck() {
    check++;
  }

  public String getSessionKey(int threadNum) {
    return playerSessionKeys[threadNum];
  }

  public void setSessionKey(String key, int threadNum) {
      int num = key.length();
      num = 89%num;
      String newKey = key+num;
      playerSessionKeys[threadNum] = newKey;
  }
  
  public int getTurn() {
    return turn;
  }

  public void setTurn() {
    if (turn == maxNumPlayers - 1) {
      turn = 0;
    } else {
      turn++;
    }
  }
    public void setWinner(int i) {
      winner = threads[i];
  }
   public clientThread getWinner() {
    return winner;
  }
  
  public int getHandValue(Card h[]){
       int val;
      if(isRoyalFlush(h))
          return ROYAL_FLUSH + valueHighCard(h);
      else if ( isStraightFlush(h) )
         return STRAIGHT_FLUSH + valueHighCard(h);
      else if ( isFourOfAKind(h) ){
          h=sortByRank(h);
        return FOUR_OF_A_KIND + h[2].getRankVal();
      }  
      else if ( isFullHouse(h) ){
         h=sortByRank(h);
        return FULL_HOUSE + h[2].getRankVal();
      }
      else if ( isFlush(h) )
         return FLUSH + valueHighCard(h);
      else if ( isStraight(h) )
         return STRAIGHT + valueHighCard(h);
      else if ( isThreeOfAKind(h) ){
        h=sortByRank(h);
        return SET + h[2].getRankVal();
      }
      else if ( isTwoPairs(h) ){
        val = 0;
        h= sortByRank(h);
        if ( h[0].getRankVal() == h[1].getRankVal() &&
             h[2].getRankVal() == h[3].getRankVal() )
           val = 14*14*h[2].getRankVal() + 14*h[0].getRankVal() + h[4].getRankVal();
        else if ( h[0].getRankVal() == h[1].getRankVal() &&
                  h[3].getRankVal() == h[4].getRankVal() )
           val = 14*14*h[3].getRankVal() + 14*h[0].getRankVal() + h[2].getRankVal();
        else 
           val = 14*14*h[3].getRankVal() + 14*h[1].getRankVal() + h[0].getRankVal();
        return TWO_PAIRS + val; 
      }
      else if ( isPair(h) ){
        val=0;
        h=sortByRank(h);
        if ( h[0].getRankVal() == h[1].getRankVal() )
           val = 14*14*14*h[0].getRankVal() +  
                  + h[2].getRankVal() + 14*h[3].getRankVal() + 14*14*h[4].getRankVal();
        else if ( h[1].getRankVal() == h[2].getRankVal() )
           val = 14*14*14*h[1].getRankVal() +  
                  + h[0].getRankVal() + 14*h[3].getRankVal() + 14*14*h[4].getRankVal();
        else if ( h[2].getRankVal() == h[3].getRankVal() )
           val = 14*14*14*h[2].getRankVal() +  
                  + h[0].getRankVal() + 14*h[1].getRankVal() + 14*14*h[4].getRankVal();
        else
           val = 14*14*14*h[3].getRankVal() +  
                  + h[0].getRankVal() + 14*h[1].getRankVal() + 14*14*h[2].getRankVal();
        return ONE_PAIR + val;
      }
      else
         return valueHighCard(h);
   }
     public int valueHighCard( Card[] h )
   {
      int val;

      h=sortByRank(h);

      val = h[0].getRankVal() + 14* h[1].getRankVal() + 14*14* h[2].getRankVal() 
            + 14*14*14* h[3].getRankVal() + 14*14*14*14* h[4].getRankVal();

      return val;
   } 
   
      public boolean isPair( Card[] h )
   {
      boolean a1, a2, a3, a4;


      if ( isFourOfAKind(h) || isFullHouse(h) || isThreeOfAKind(h) || isTwoPairs(h) )
         return(false);        // The hand is not one pair (but better)       

      h=sortByRank(h);
                          
      a1 = h[0].getRankVal() == h[1].getRankVal() ;
      a2 = h[1].getRankVal() == h[2].getRankVal() ;
      a3 = h[2].getRankVal() == h[3].getRankVal() ;
      a4 = h[3].getRankVal() == h[4].getRankVal() ;

      return( a1 || a2 || a3 || a4 );
   }
   public boolean isFullHouse( Card[] h )
   {
      boolean a1, a2;


      h=sortByRank(h);    

      a1 = h[0].getRankVal() == h[1].getRankVal() &&
           h[1].getRankVal() == h[2].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal();

      a2 = h[0].getRankVal() == h[1].getRankVal() &&
           h[2].getRankVal() == h[3].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal();

      return( a1 || a2 );
   }
        public boolean isTwoPairs( Card[] h )
   {
      boolean a1, a2, a3;

      if ( h.length != 5 )
         return(false);

     if ( isFourOfAKind(h) || isFullHouse(h) || isThreeOfAKind(h) )
         return(false);           

      h=sortByRank(h);
                      
      a1 = h[0].getRankVal() == h[1].getRankVal() &&
           h[2].getRankVal() == h[3].getRankVal() ;

      a2 = h[0].getRankVal() == h[1].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal() ;

      a3 = h[1].getRankVal() == h[2].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal() ;

      return( a1 || a2 || a3 );
   }
   public boolean isRoyalFlush(Card cards[]){
       boolean flag=false;
       cards=sortByRank(cards);
       if(cards[0].getRankVal()==10&&cards[1].getRankVal()==11&&cards[2].getRankVal()==12&&cards[3].getRankVal()==13&&cards[4].getRankVal()==14)
           flag=true;
       return (isStraight( cards ) && isFlush( cards )&&flag);
   }
   public boolean isStraightFlush(Card cards[]){
       return (isStraight( cards ) && isFlush( cards ));
   }
   public boolean isFlush(Card cards[]){
      cards=sortBySuit(cards);      

      return( cards[0].getSuitVal() == cards[4].getSuitVal() ); 
   }
    public boolean isStraight( Card[] h ){
      int i, testRank;

      if ( h.length != 5 )
         return(false);

      h=sortByRank(h);        
      if ( h[4].getRankVal() == 14 )
      {
         boolean a = h[0].getRankVal() == 2 && h[1].getRankVal() == 3 &&
                     h[2].getRankVal() == 4 && h[3].getRankVal() == 5 ;
         boolean b = h[0].getRankVal() == 10 && h[1].getRankVal() == 11 &&        
                     h[2].getRankVal() == 12 && h[3].getRankVal() == 13 ;

         return ( a || b );
      }
      else
      {

         testRank = h[0].getRankVal() + 1;

         for ( i = 1; i < 5; i++ )
         {
            if ( h[i].getRankVal() != testRank )
               return(false);        

            testRank++;  
         }

         return(true);     
      }
   }
 public Card[] sortBySuit( Card[] h )
   {
      int i, j, min_pos;
      Card min_card;


      for ( i = 0 ; i < h.length ; i ++ )
      {
         min_pos = i;   
         min_card=h[i];
         for ( j = i+1 ; j < h.length ; j++ )
         {
            if ( h[j].getSuitVal() < h[min_pos].getSuitVal() )
            {
               min_pos = j;       
            }
         }

         Card help = h[i];
         h[i] = h[min_pos];
         h[min_pos] = help;
      }
      return h;
   }
     public Card[] sortByRank( Card[] h )
   {
      int i, j, min_pos;
      Card min_card;

      for ( i = 0 ; i < h.length ; i ++ )
      {

         min_pos = i;  
         min_card=h[i];
         for ( j = i+1 ; j < h.length ; j++ )
         {
            if ( h[j].getRankVal() < h[min_pos].getRankVal() )
            {
               min_pos = j;        
            }
         }


         Card help = h[i];
         h[i] = h[min_pos];
         h[min_pos] = help;
      }
      return h;
   }
    
     public boolean isFourOfAKind( Card[] h )
   {
      boolean a1, a2;

      if ( h.length != 5 )
         return(false);

      sortByRank(h);            
      a1 = h[0].getRankVal() == h[1].getRankVal() &&
           h[1].getRankVal() == h[2].getRankVal() &&
           h[2].getRankVal() == h[3].getRankVal() ;
   
      a2 = h[1].getRankVal() == h[2].getRankVal() &&
           h[2].getRankVal() == h[3].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal() ;

      return( a1 || a2 );
   }
  public boolean isThreeOfAKind( Card[] h )
   {
      boolean a1, a2, a3;

      if ( h.length != 5 )
         return(false);

      if ( isFourOfAKind(h) || isFullHouse(h) )
         return(false);                

      h=sortByRank(h);
    
      a1 = h[0].getRankVal() == h[1].getRankVal() &&                  
           h[1].getRankVal() == h[2].getRankVal() ;

      a2 = h[1].getRankVal() == h[2].getRankVal() &&
           h[2].getRankVal() == h[3].getRankVal() ;

      a3 = h[2].getRankVal() == h[3].getRankVal() &&
           h[3].getRankVal() == h[4].getRankVal() ;

      return( a1 || a2 || a3 );
   }

}