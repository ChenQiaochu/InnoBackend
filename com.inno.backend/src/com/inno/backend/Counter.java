package com.inno.backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Counter {

	Socket socket;
	Database db;

	public Counter(Socket socket, Database db) throws Exception {
		this.socket = socket;
		this.db = db;
	}

	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processRequest() throws Exception {
		InputStream input = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String requestLine = br.readLine();
		GetRequest(requestLine);
	}

	String name;
	int value;

	public void GetRequest(String requestLine) throws Exception {
		PrintWriter output = new PrintWriter(socket.getOutputStream());
		/*
		 * for (Item item : list) { System.out.println("Name: " + item.getName()
		 * + " " + "value: " + item.getValue()); output.print("Name: " +
		 * item.getName() + " " + ",value: " + item.getValue()); }
		 */
		// String decode = null;
		value = 1;
		System.out.println(requestLine);
		// decode = URLDecoder.decode(requestLine, "UTF-8");
		// System.out.println("decode:" + decode);
		if (!requestLine.contains("=")) {
			System.out.println("Wrong command!");
		} else {
			if (!requestLine.contains("&")) {
				String[] array = requestLine.split("=");
				System.out.println(array[1]);
				String temp = array[1];
				System.out.println(temp);
				String[] arrayName = temp.split(" ");
				String name = arrayName[0];
				System.out.println(name);
				System.out.println(" 1 value" + value);
				if (name.equalsIgnoreCase("getallvalue")) {
					output.print(db.getAllAccounts());
				} else {
					if (db.doesAccountExsists(name)) {
						value = db.getOneAccount(name) + 1;
						db.setValueDirect(name, value);
					} else {
						db.addDirect(name, value);
					}
				}

				/*
				 * if (list.isEmpty()) { System.out.println("empty");
				 * list.add(item); System.out.println("2" +
				 * list.get(0).getName() + list.get(0).getValue());
				 * System.out.println("3" + item.getName() + item.getValue()); }
				 * else { if (list.contains(item)) {
				 * System.out.println(item.getValue()); value = item.getValue()
				 * + 1; item.setValue(value); System.out.println(value); } else
				 * { list.add(item); System.out.println("4value" +
				 * item.getValue()); } }
				 */

			} else {
				String[] array = requestLine.split("&");
				String nameArray = array[0];
				String[] tempName = nameArray.split("=");
				String name = tempName[1];
				System.out.println(name);
				String actArray = array[1];
				String[] temp = actArray.split(" ");
				String command = temp[0];
				String[] tempAct = command.split("=");
				String act = tempAct[1];
				System.out.println(act);
				output.print("Name: " + name + " Value: " + db.getOneAccount(name));

				/*
				 * for (Item item : list) { if (item.getName().contains(name)) {
				 * System.out.println(item.getValue()); output.print("Name: " +
				 * name + ",value" + item.getValue());
				 * 
				 * } }
				 */
			}
		}
		output.flush();
	}
}
