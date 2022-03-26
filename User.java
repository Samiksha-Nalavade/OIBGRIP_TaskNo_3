
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User
{
	String first_name;
	String last_name;
	String uuid;
	byte pinhash[]; //MD5 hash of users pin number
	
	 ArrayList<Account> accounts ; //List of accounts for this user
	
	public User(String first_name,String last_name,String pin,Bank thebank)
	{
		this.first_name = first_name;
		this.last_name = last_name;
		
		//Store the pin's MD5 hash than original value for security
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinhash = md.digest(pin.getBytes());
		}
		catch(NoSuchAlgorithmException e)
		{
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		//Get a new Unique Universal ID for user
		this.uuid = thebank.getNewUserUUID();
		
		//Create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		System.out.printf("\nNew User %s,%s with ID %s created.\n",last_name,first_name,this.uuid);
	}//end constructor
	
	public void addAccount(Account onAcct)
	{
			this.accounts.add(onAcct);
	}
	
	public String getUUID()
	{
		return this.uuid;
	}
	
	public boolean validatePin(String aPin)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinhash);
			
		}
		catch(NoSuchAlgorithmException e)
		{
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
		
	}
	
	public String getFirstName()
	{
		return this.first_name;
	}
	
	public void printAccountsSummary()
	{
		System.out.printf("%s's accounts summary\n",this.first_name);
		for(int a=0;a<this.accounts.size();a++)
		{
			System.out.printf("%d) %s\n",a+1,this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	public int numAccounts()
	{
		return this.accounts.size();
	}
	
	public void printAcctTransHistory(int acctIdx){
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	public double getAcctBalance(int acctIdx)
	{
		return this.accounts.get(acctIdx).getBalance();
	}//end getAcctBalance
	
	public String getAcctUUID(int acctIdx)
	{
		return this.accounts.get(acctIdx).getUUID();
	}//end getUUID
	
	public void addAcctTransaction(int acctIdx,double amount,String memo)
	{
		this.accounts.get(acctIdx).addTransaction(amount,memo);
	}//end addAcctTransaction
	
	
}//end class User
