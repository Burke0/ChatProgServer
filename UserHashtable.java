import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class UserHashtable extends Hashtable<String, User> 
{
	UserHashtable(DataInputStream dis) throws IOException, NullPointerException
	{
		int numElements;
		numElements = dis.readInt();
		System.out.println("found "+numElements+ " users in hashtable file");
		for( int i=0; i<numElements; i++ )
		{
			User tmpUser= new User();
			tmpUser.Construct(dis);
			if(tmpUser.getUsername()!=null && tmpUser.getPassword()!=null)
				put(tmpUser.getUsername(), tmpUser); // username is the key
		}
	}
	/*
	public UserHashtable() {
		// TODO Auto-generated constructor stub
		System.out.println("UserHastable empty constructor does nothing currently ");
	}
	*/

	void store(DataOutputStream dos) throws IOException
	{
		Enumeration<User> myEnum;
		dos.writeInt(size());  				//number of keys in Hashtable
		myEnum=elements();
		while(myEnum.hasMoreElements()) 	//loops through and stores all user's
			myEnum.nextElement().Store(dos);
		
	}
}
