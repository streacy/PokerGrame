/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pokergame;

import java.net.ServerSocket;
import java.security.PublicKey;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author stephanietreacy
 */
public class House{
    private ServerSocket serverSocket;
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private Thread[] playerList = new Thread[5];
    private int count = -1;
    private static House house = null;
    
    private House(){
        System.out.println("House created\n");
    }
    
    public static House getHouse(){
        
     if(house == null){
         
         house = new House();
     }
     
     return house;
    }    
    
    
 
    public void start(int port) throws IOException {
        
        serverSocket = new ServerSocket(port);
       
        while(true){
            Socket s = null;
            try{
                if (count == 4) {
                    System.out.println("To many players playing");
         
                    continue;
                }
                s = serverSocket.accept();
                
                System.out.println("A new Client is connect");
                count++;
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                
                System.out.println("Assigning new thread to the client");
                
                //create new thread for client
                Thread t = new ClientHandler(s,dis,dos,count);
                playerList[count]=t;
                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
       
        }
    }
 
    public void stop() throws IOException {
        in.close();
        out.close();
        s.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        House server= House.getHouse();
        server.start(6789);
    }
}
