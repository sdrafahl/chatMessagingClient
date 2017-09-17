package chatMessagingClient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable {

	Scanner in;
	Client client;
	Socket socket;
	
	public ServerListener(Client client, Socket s) {
		this.socket = s;
		this.client = client;
		try {
			this.in = new Scanner(new BufferedInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean readFile = false;
		String fileName = "";
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(readFile) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ImageReader read = new ImageReader(socket);
				read.fileNameBuffer = fileName;
				new Thread(read).start();
				readFile = false;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				DataInputStream dis = null;
				try {
					dis = new DataInputStream(this.client.socket.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream("images/" + fileName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				int filesize = 8192;
				byte[] buffer = new byte[filesize];
				int read = 0;
				int totalRead = 0;
				int remaining = filesize;
				try {
					fos.flush();
						while((read = dis.read(buffer, 0, filesize)) != -1) {
							totalRead += read;
							remaining -= read;
							System.out.println("read " + totalRead + " bytes.");
							fos.write(buffer, 0, read);
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			*/}
			
			if(in.hasNextLine()) {
				String input = in.nextLine();
				System.out.println("Client Recieved: " + input);
				if(input.length() > 0 && input.charAt(0) == 'I') {
					readFile = true;
					fileName = input.substring(1);
				}
			}
			
		}
	}
	
}
