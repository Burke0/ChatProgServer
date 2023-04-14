import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Talker
{
	
	BufferedReader 		br;
	DataOutputStream	dos;
	String 				id;
	Socket 				socket;
	
	Talker(Socket socket) throws IOException//for server
	{
		id="pending";
		dos = new DataOutputStream (socket.getOutputStream());
	    br  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	Talker(String domain, int port, String id) throws UnknownHostException, IOException //for client
	{
		this.id=id;

		socket = new Socket(domain, port);
		dos  = new DataOutputStream(socket.getOutputStream());
		br   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	};
	
	public void send(String message) throws IOException
	{
		message=message+"\n";
		dos.writeBytes(message);
		System.out.println("ID "+ id + " Message Sent: " + message);
	}
	
	public String recieve() throws IOException 
	{
		String s = br.readLine();
		System.out.println("ID "+ id + " Message Recieved: " + s);
		return s;
	}
	
	public void recieveMessage() throws IOException 
	{
		String s;
		s=br.readLine();
		System.out.println("ID "+ id + " Message Recieved: " + s);
	}
	
	
	
}
