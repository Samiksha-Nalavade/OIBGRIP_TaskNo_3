
import java.util.ArrayList;

public class Account
{
	String name;
	String uuid;
	User holder;
	
	ArrayList<Transactions> transactions;
	
	public Account(String name,User holder,Bank thebank)
	{
		this.name = name;
		this.holder = holder;
		
		//get new account uuid
		this.uuid = thebank.getNewAccountUUID();
		
		//Initialize transactions
		this.transactions = new ArrayList<Transactions>();
		
		
		
		
	}//end constructor
	
	public String getUUID()
	{
		return this.uuid;
	}
	
	public String getSummaryLine()
	{
		double balance = this.getBalance();
		
		if(balance >= 0)
		{
			return String.format("%s : Rs.%.02f : %s",this.uuid,balance,this.name);
		}
		else
		{
			return String.format("%s : Rs.(%.02f) : %s",this.uuid,-balance,this.name);
		}
	}
	
	public double getBalance(){
		double balance =0;
		for(Transactions t: this.transactions )
		{
			balance += t.getAmount();
		}
		return balance;
	}
	
	public void printTransHistory()
	{
			System.out.printf("\nTransaction history for account %s\n",this.uuid);
			for(int t=this.transactions.size()-1;t>=0;t--)
			{
				System.out.println(this.transactions.get(t).getSummaryLine());
			}
			System.out.println();
	}
	
	public void addTransaction(double amount,String memo)
	{
		//Create new transaction object and add it out list
		Transactions newTrans = new Transactions(amount,memo,this);
		this.transactions.add(newTrans);
		
	}//end addTransaction
	
}//end class Account