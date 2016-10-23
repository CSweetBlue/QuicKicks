package com.example.christiansweet.adidasbuyer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class Client {

	private final static String serverName = "35.160.113.251";
	private final static int port = 8888;
	
	public static void main (String[] args) {
		Client c = new Client();
		System.out.println(Arrays.toString(c.getServerData()));
	}
	
	public String[] getServerData() {
		try {
			
			Socket client = new Socket();
			client.connect(new InetSocketAddress(serverName, port), 10000);
			
			DataInputStream in = new DataInputStream(client.getInputStream());
			// Site key, duplicate captcha, client ID
			String[] data = in.readUTF().split("\n");
			
			in.close();
			client.close();
			
			return data;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}