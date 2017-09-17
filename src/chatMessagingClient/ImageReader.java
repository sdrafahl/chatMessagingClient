package chatMessagingClient;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ImageReader implements Runnable {

	public String fileNameBuffer;
	private Socket socket;
	
	public ImageReader(Socket socket) {
		this.fileNameBuffer = "";
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		while(true) {
			if (!fileNameBuffer.isEmpty()) {
				DataInputStream dis = null;
				try {
					dis = new DataInputStream(this.socket.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream("images/" + this.fileNameBuffer);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				int filesize = 600000;
				byte[] buffer = new byte[filesize];
				int read = 0;
				int totalRead = 0;
				int remaining = filesize;
				this.fileNameBuffer = "";
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
		}
		
	}
	}
}
