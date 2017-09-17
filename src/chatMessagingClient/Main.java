package chatMessagingClient;

import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter Your Name");
		System.out.println("");
		String name = scan.next();
		
		Client client = new Client("localhost", 6009, name);
		
		while(true) {
			
			System.out.println("Please enter 1 to send a message or 2 to send an image.");
			
			int choice = scan.nextInt();
			if(choice == 1) {
				scan.nextLine();
				System.out.println("Enter Your Message");
				System.out.println("");
				String message = scan.nextLine();
				System.out.println("Sending Message....");
				client.sendTextMessage(message);
			}
			
			if(choice == 2) {
				scan.nextLine();
				System.out.println("Enter the file name");
				System.out.println("");
				String fileName = scan.nextLine();
				client.sendImage(fileName);
			}
		}
	}

}
