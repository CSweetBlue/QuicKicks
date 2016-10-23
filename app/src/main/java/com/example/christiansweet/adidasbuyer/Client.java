package com.example.christiansweet.adidasbuyer;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

	//private static String serverName = "127.0.0.1";
	private static String serverName = "35.160.113.251";
	private final static int port = 8888;

	private static DataInputStream in;
	private static DataOutputStream out;
	private static Socket client;

	static {
		client = new Socket();
		try {
			client.connect(new InetSocketAddress(serverName, port), 10000);
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static boolean login(String username, String password) throws IOException {
		out.writeUTF("USER");
		out.writeUTF(username);
		in.readUTF();

		out.writeUTF("PASS");
		out.writeUTF(password);
		in.readUTF();

		out.writeUTF("LOGIN");
		String status = in.readUTF();
		if (status.equals("OK")) {
			return true;
		}
		return false;
	}

	public static String getSiteKey() throws IOException {
		out.writeUTF("SITE");
		String key = in.readUTF();
		return key;
	}

	public static String getSizes(String sku) throws IOException {
		out.writeUTF("SKU");
		out.writeUTF(sku);
		in.readUTF();

		out.writeUTF("SIZE_DATA");
		String result = in.readUTF();

		return result;
	}

	public static boolean addToCart(String CAPTCHA, String SKU, double size) throws IOException {
		out.writeUTF("SKU");
		out.writeUTF(SKU);
		in.readUTF();

		out.writeUTF("SIZE");
		out.writeUTF("" + size);
		in.readUTF();

		out.writeUTF("TOKEN");
		out.writeUTF(CAPTCHA);
		in.readUTF();

		out.writeUTF("ADD");
		String status = in.readUTF();
		if (status.equals("OK")) {
			return true;
		}
		return false;
	}

	public static void destroy() {
		try {
			out.writeUTF("EXIT");
			in.close();
			out.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}