/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inno.backend;

/*
 * @author Qiaochu Chen
 * 
 * This is the main class.
 */
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
