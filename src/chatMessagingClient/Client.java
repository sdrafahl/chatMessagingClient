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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.omg.CORBA.portable.OutputStream;

public class Client {

	Socket socket;
	String host;
	int port;
	ServerListener sl;
	String name;
	
	public Client(String host, int port, String name) {
		
		this.name = name;
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
	
	public void sendTextMessage(String msg) {
		String user = this.name;
		try {
			PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
			out.println("TEXT:" + user + "|" + msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendImage(String fileName) {
		String user = this.name;
		PrintWriter printOut = null;
		try {
			printOut = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String extension = fileName.substring(fileName.length()-4);
		String outFileName = fileName.substring(0, fileName.length()-4);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		printOut.println("I" + outFileName + "_" + user + "_" + dtf.format(now) + extension);
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
			System.out.println("sending " + file.toString());
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = new byte[10000];
		
		try {
			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
