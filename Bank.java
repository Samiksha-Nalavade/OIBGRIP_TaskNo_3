
import java.util.ArrayList;
import java.util.Random;

public class Bank
{
	String name;
	ArrayList<User> users;
	ArrayList<Account> accounts;
	
	public Bank(String name)
	{
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
		
	}//end constructor
	
	public String getNewUserUUID()
	{
		String uuid;
		Random rng = new Random();
		int len=6;
		boolean nonUnique;
		
		//Continue looping until we get unique ID
		do
		{
			//generate number
			uuid="";
			for(int c=0;c<len;c++)
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
				
			}
			
			//make sure its unique ID
			nonUnique=false;
			for(User u:this.users)
			{
				if(uuid.compareTo(u.getUUID())==0){
					nonUnique = true;
					break;
					
				}
			}
		}while(nonUnique);
		
		
		return uuid;
	}
	
	public String getNewAccountUUID()
	{
		String uuid;
		Random rng = new Random();
		int len=10;
		boolean nonUnique;
		
		//Continue loopin until we get unique ID
		do
		{
			//generate number
			uuid="";
			for(int c=0;c<len;c++)
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
				
			}
			
			//make sure its unique ID
			nonUnique=false;
			for(Account a:this.accounts)
			{
				if(uuid.compareTo(a.getUUID())==0){
					nonUnique = true;
					break;
					
				}
			}
		}while(nonUnique);
		
		
		return uuid;
	}
	
	public void addAccount(Account onAcct)
	{
		this.accounts.add(onAcct);
	}
	
	public User addUser(String first_name,String last_name,String pin)
	{
		//create new user object and add it to our list
		User newUser = new User(first_name,last_name,pin,this);
		this.users.add(newUser);
		
		//Create savings account for a user and add to user and bank accounts list
		Account newAccount = new Account("Savings",newUser,this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
		
	}
	
	public User userLogin(String userID,String pin)
	{
		//Search through list of user
		for(User u:this.users)
		{
			if(u.getUUID().compareTo(userID)==0 && u.validatePin(pin))
			{
				return u;
			}
		}
		return null;
	}
	
	public String getName()
	{
			return this.name;
	}
	
}//end class Bank