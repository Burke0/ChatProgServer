import java.io.IOException;
import java.net.UnknownHostException;

public class ConnectionToClient implements Runnable //handles communication from the server to various clients
{
		
	Talker talker;
	Server server;
	
	ConnectionToClient(Talker talker, Server server)
	{
		this.talker=talker;
		this.server=server;
		new Thread(this).start();
	}
	
	ConnectionToClient(String domain, int port, String id, Server server) throws UnknownHostException, IOException
	{
		talker=new Talker(domain,port,id);	
		this.server=server;
		new Thread(this).start();
	}
	
	@Override
	public void run() //short synchronous exchange with server to either register or to log in if server accepted account
	{
		
		while(true) 
		{
			
			try 
			{
				String str= talker.recieve();
				if(str.equals("login"))
				{
					talker.send("Welcome! Enter Username and Password");
					str=talker.recieve();
					String[] temp= str.split(":");//format is username:pass
					String tempUsername=temp[0];
					String tempPassword=temp[1];
					System.out.println("searching for: " + tempUsername+" "+ tempPassword+" ");
					System.out.println("hashtable contains key? " + server.getMyUserHashtable().containsKey(tempUsername));
					
					if(server.getMyUserHashtable().containsKey(tempUsername))//if the hashtable contains username, check if username and pass match user input.
					{
						if(server.getMyUserHashtable().get(tempUsername).getPassword().equals(tempPassword))
						{
							User user=server.getMyUserHashtable().get(tempUsername);//make a temp user
							user.ctc=this;//point it to this CTC
							server.getMyUserHashtable().replace(tempUsername, user);// Replace old one in hashtable with this new one
							talker.send("User Logged in");
						}
						else
							talker.send("Username or Password incorrect");
					}
					else
						talker.send("Username or Password incorrect");
					
				}
				else if(str.equals("register"))
				{
					talker.send("Welcome! Enter Username");
					str=talker.recieve();
					//if(str.equals(":"||str.equals(" : ")))
					if(server.getMyUserHashtable().containsKey(str))	// check if username is in hashtable already
					{
						talker.send("Username taken: try something else or logging in instead");
					}
					else	 //check password and add user to server hashtable
					{
						String tempUsername, tempPassword;
						tempUsername=str;
						talker.send("What Password?");
						str=talker.recieve();
						//TODO if password is weak try again loop
						tempPassword=str;
						User newUser= new User(tempUsername,tempPassword, this);
						server.addUser(newUser);
						talker.send("User Logged in");
					}
					
				}
				
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
				new Thread(this).start();
			
			}
			
		}
	
			
		}


	
	
}
