
import java.util.Date;

public class Transactions
{
	double amount;
	Date timestamp;
	String memo;
	Account inAccount;
	
	public Transactions(double amount,Account inAccount)
	{
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp  = new Date();
		this.memo = "";
		
	}//end constructor
	
	public Transactions(double amount,String memo,Account inAccount)
	{
		//call two-args constructor
		this(amount,inAccount);
		this.memo = memo;
		
	}//end constructor
	
	public double getAmount()
	{
		 return this.amount;
	}
	
	public String getSummaryLine()
	{
		if(this.amount >= 0)
		{
			return String.format("%s : Rs.%.02f : %s",this.timestamp.toString(),this.amount,this.memo);
		}
		else
		{
			return String.format("%s : Rs.(%.02f) : %s",this.timestamp.toString(),-this.amount,this.memo);
		}
	}
	
	
}//end class Transactions