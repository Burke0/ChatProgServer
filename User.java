import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class User 
{
	private String 			username;
	private String 			password;
	ConnectionToClient 		ctc = null;
	private Vector<String> 	buddiesList;
	
		User(String username, String password, ConnectionToClient ctc)//new user?
		{
			this.username=username;
			this.password=password;
			this.ctc=ctc;
			
		}
		User()
		{
			System.out.println(" Temp user created ");
		}
		public void Store(DataOutputStream dos) throws IOException
		{
			
			dos.writeUTF(username);
			dos.writeUTF(password);
			System.out.println(username + " " + password + " User.Store method called ");
			//buddy list eventually	
		}
		public void Construct(DataInputStream dis) throws IOException
		{	
			username=dis.readUTF();
			password=dis.readUTF();
			System.out.println("User.Construct method called with " + username +" "+ password);//remove later
		}
		public String getUsername() 
		{
			return username;
		}
		public void setUsername(String username) 
		{
			this.username = username;
		}
		public String getPassword() 
		{
			return password;
		}
		public void setPassword(String password) 
		{
			this.password =  password;
		} 
}	
