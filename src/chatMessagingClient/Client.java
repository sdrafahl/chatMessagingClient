package chatMessagingClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.omg.CORBA.portable.OutputStream;

public class Client {

	Socket socket;
	String host;
	int port;
	ServerListener sl;
	
	public Client(String host, int port) {
		
		this.port = port;
		this.host = host;
		
		try {
			socket = new Socket(this.host, this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sl = new ServerListener(this, socket);
		new Thread(sl).start();
	}
	
	public void sendTextMessage(String user, String msg) {
		
		try {
			PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
			out.println("TEXT:" + user + "|" + msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendImage(String fileName) {
		
		PrintWriter printOut = null;
		try {
			printOut = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		printOut.println(fileName);
		printOut.flush();
		File file = new File("images/" + fileName);
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = new byte[4096];
		
		try {
			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	    
	}
	
	
	
}
