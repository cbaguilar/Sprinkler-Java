package com.christian;
//import java.io.*;  
//import java.net.*;  

import java.io.*;  
import java.net.*;  
public class Networking {  
	
	DataOutputStream out;
	Socket client;
	BufferedReader inFromUser;
	BufferedReader inbound;
	
	
	
    Networking(String address,int port) throws UnknownHostException, IOException{  
        
        //String modifiedSentence;  
        //Create a standard input stream  
        inFromUser=new BufferedReader(new InputStreamReader(System.in));  
        //Create a client socket and connect to the server  
        client=new Socket(address,port );  
        //Create output Stream attached to socket  
        out=new DataOutputStream(client.getOutputStream());  
        inbound = new BufferedReader(new InputStreamReader(client.getInputStream()));
        
        
        //Create input stream and attached it to socket  
        //BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
        //Read text from user  
        //sentence=inFromUser.readLine();  
        //Send line to server  
        //length = intToByteArray( sentence.length() );
        //System.out.println(length+sentence);

   
       
        //Read a modified sentence from the user 
        
        //modifiedSentence=inFromServer.readLine();  
        //System.out.println("FROM SERVER"+modifiedSentence);  
        //--client.close();  
  
  
  
    }  
    
    public void send(String data) throws IOException{
       // write message size, then the message 
           // byte[] sizeBytes = intToByteArray( data.length() ); 
            //for (int i = 0; i < 4; i++)
         //   {
            //out.write(sizeBytes[i]); 
            //System.out.println(sizeBytes[i]);
            //}
            out.write(data.getBytes());
            //out.write("end".getBytes());
            //System.out.println("derp");   
    }

	public String receive() throws IOException, NullPointerException{
		
			String receive = inbound.readLine();	
			return receive;	
	}
	
	public void close() throws IOException, IOException{
		
			client.close();
		
	}
	
	
  
} 