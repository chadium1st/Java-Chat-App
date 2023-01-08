package ChatApp;

import java.io.*;
import java.net.*;
//import java.util.*;

public final class Server {
    
    ServerSocket server;
    Socket socket;
    
    BufferedReader bfR;
    BufferedReader bfR2;
    PrintWriter prW;
    
    //constructor
    public Server() {
        try{
            System.out.println("Sending request to client..");
            server = new ServerSocket(2003);
            System.out.println("Connection established.");
            socket = server.accept();
            
            bfR = new BufferedReader(new InputStreamReader
            (socket.getInputStream()));
            
            prW = new PrintWriter(socket.getOutputStream());
            
            startReadProcess();
            startWriteProcess();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void startReadProcess() {
        //this thread reads the data from the client.
        Runnable r1 = () -> {
            
            System.out.println("reader started.");
            try {
            while(true) {
                    String message = bfR.readLine();
                    if(message.equals("exit")) {
                        System.out.println("Client has stopped.");
                        socket.close();
                        break;
                    }
                    
                    System.out.println("Client: " + message);
                    
            } 
                } catch(Exception e) {
                    e.printStackTrace();
                  }
        };
        
        new Thread(r1).start();
    }
    
    public void startWriteProcess() {
        //this thread takes data from the server and sends it to the client.
        Runnable r2 = () -> {
            
            System.out.println("writer started.");
            try {
            while(true && !socket.isClosed()) {      
                    bfR2 = new BufferedReader(new InputStreamReader
                    (socket.getInputStream()));
                    String data = bfR2.readLine();
                    
                    prW.println(data);
                    prW.flush();
                    
                    if (data.equals("exit")) {
                        socket.close();
                        break;
                    }
                    
            } 
                } catch(Exception e) {
                    e.printStackTrace();
                  }
        };
        
        new Thread(r2).start();
    }
    
    public static void main(String[] args){
        try {
            System.out.println("AAAAAAAAAAA");
            new Server();
            
        
        } catch(Exception Exception) {
            Exception.printStackTrace();   
        }
        
    }
}
