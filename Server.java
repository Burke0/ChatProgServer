import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable 
{
		ServerSocket     			serverSocket;
		Socket 						normalSocket;
		static String 				filename= "hashtablefile.txt";
		private UserHashtable 		myUserHashtable;
		Talker 						talker;
	Server()
	{
		try
		{
			serverSocket = 	new ServerSocket(3737); //listen on port 3737
			
			File userHashtableFile = new File(filename);
			if(!userHashtableFile.exists()) 
			{
				userHashtableFile.createNewFile();//only creates file iff it doesn't exist already
				FileOutputStream fos = new FileOutputStream(filename);
				DataOutputStream dos	= new DataOutputStream(fos);
				dos.writeInt(0);// 0 users
				System.out.println("new hashtablefile created... loading file...");
			}
			FileInputStream fis =	new FileInputStream(userHashtableFile);
			DataInputStream dis = 	new DataInputStream(fis);
			myUserHashtable		=	new UserHashtable(dis); //attempt to load hashtable from file
			
			System.out.println("user hashtable file loaded successfully, starting thread.. ");
			new Thread(this).start();
		} 
		catch (FileNotFoundException e) 
		{

			System.out.println("User Hashtable file not found error");
			e.printStackTrace();
			System.exit(3);
			
		}
		catch (IOException s) 
		{
			s.printStackTrace();
			System.exit(3);
		}
		catch( NullPointerException np)
		{
			System.out.println("null pointer in user data file needs corrected");
			np.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) 
	{
		Server server= new Server();
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			try
			{
				System.out.println("waiting for connection");
				normalSocket = 	serverSocket.accept(); //waiting to accept a connection
				talker		 =	new Talker(normalSocket);
				ConnectionToClient ctc=new ConnectionToClient(talker,this);
			}
			catch(IOException aaah)
			{
				aaah.printStackTrace();
				System.exit(2);
			}
		}
	}

	void saveUsers() throws IOException
	{
		FileOutputStream fos = new FileOutputStream(filename);
		DataOutputStream dos	= new DataOutputStream(fos);
		getMyUserHashtable().store(dos);
		dos.flush();
	}
	public void addUser(User user) throws IOException  
	{
		myUserHashtable.put(user.getUsername(), user);
		saveUsers();
	}

	public UserHashtable getMyUserHashtable() 
	{
		return myUserHashtable;
	}

	public void setMyUserHashtable(UserHashtable myUserHashtable) 
	{
		this.myUserHashtable = myUserHashtable;
	}


}
