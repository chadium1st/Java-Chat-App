package ChatApp;

import java.io.*;
import java.net.*;

public final class Client {
    
    Socket socket;
    
    BufferedReader bfR;
    BufferedReader bfR2;
    PrintWriter prW;
    
    public Client() {
        try {
            System.out.println("Sending request to server..");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection established.");
            
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
                        System.out.println("Server terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + message);
    
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
            while(true) {   
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
    
    public static void main(String[] args) {
        new Client();
    }
}
