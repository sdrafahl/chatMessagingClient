package chatMessagingClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable {

	Scanner in;
	Client client;
	
	public ServerListener(Client client, Socket s) {
		this.client = client;
		try {
			this.in = new Scanner(new BufferedInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
		}
	}
	
}
