package chatMessagingClient;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 5030);
		
		Random rand = new Random();
		client.sendTextMessage("Shane " + rand.nextInt(), "This is a test message " + rand.nextInt());
		client.sendImage("images.jpg");
		while(true) {
			
		}
	}

}
