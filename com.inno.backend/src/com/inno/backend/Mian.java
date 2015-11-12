package com.inno.backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Mian {

	static Database db;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Socket socket;
		db = new Database();
		int port = 8000;
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			socket = serverSocket.accept();
			Counter counter = new Counter(socket, db);
			counter.run();
		}
	}
}
