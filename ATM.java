
/*
	Oasis Infobyte
	Task 3: ATM INTERFACE
*/

import java.util.Scanner;

public class ATM
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		Bank thebank = new Bank("Bank Of India");
		
		User theUser = thebank.addUser("Arundhati","Patil","a34n");
		
		Account newAccount = new Account("Checking",theUser,thebank);
		theUser.addAccount(newAccount);
		thebank.addAccount(newAccount);
		
		User curUser;
		while(true)
		{
			//Stay in login prompt until successful login
			curUser = ATM.mainMenuPrompt(thebank,sc);
			
			//Stay in main menu until user quits
			ATM.printUserMenu(curUser,sc);
			
		}
		
	}//end main
	
	public static User mainMenuPrompt(Bank thebank,Scanner sc)
	{
		String userID;
		String pin;
		User authUser;
		
		do
		{
			System.out.printf("\nWelcome to %s\n\n",thebank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			//try to get user object => ID and pin
			authUser = thebank.userLogin(userID,pin);
			if(authUser == null)
			{
				System.out.printf("Incorrect user ID/pin  Please try again..");
				
			}
		}while(authUser == null);  //continue looping until successful login
		
		return authUser;
	}// end mainMenuPrompt
	
	public static void printUserMenu(User theUser,Scanner sc)
	{
		//print users summary
		theUser.printAccountsSummary();
		
		
		int choice;
		
		//user menu
		do
		{
			System.out.printf("Welcome %s, What would you like to do?\n",theUser.getFirstName());
			System.out.println(" 1)Transaction History");
			System.out.println(" 2)Withdraw");
			System.out.println(" 3)Deposit");
			System.out.println(" 4)Transfer");
			System.out.println(" 5)Quit\n");
			
			System.out.println("Enter your choice: ");
			choice = sc.nextInt();
			
			if(choice<1 || choice>5)
			{
				System.out.println("Invalid choice! Please choose 1-5");
			}
		}while(choice<1 || choice>5);
		
		switch(choice)
		{
			case 1:
				ATM.showTransHistory(theUser,sc);
				break;
			case 2:
				ATM.withdrawFunds(theUser,sc);
				break;
			case 3:
				ATM.depositFunds(theUser,sc);
				break;
			case 4:
				ATM.transferFunds(theUser,sc);
				break;
			case 5:
				System.exit(0);
				
		}//end switch
		
		//ReDisplay this menu unless user wants to quit
		if(choice!=5)
		{
			ATM.printUserMenu(theUser,sc);
		}
		
	}//end printUserMenu
	
	public static void showTransHistory(User theUser,Scanner sc)
	{
		int theAcct;
		
		do
		{
			System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ",theUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if(theAcct<0 || theAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid Account.Please try again.");
			}
		}while(theAcct<0 || theAcct>=theUser.numAccounts());
		
		theUser.printAcctTransHistory(theAcct);
	}//end showtranshistory
	
	public static void transferFunds(User theUser,Scanner sc)
	{
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		//account to transfer from
		do{
			System.out.printf("Enter the number (1-%d) of the account to transfer from: ",theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if(fromAcct <0 || fromAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid Account.Please try again.");
			}
		}while(fromAcct<0 || fromAcct>=theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//account to transfer to
		do{
			System.out.printf("Enter the number  of the account to transfer to: ",theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if(toAcct <0 || toAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid Account.Please try again.");
			}
		}while(toAcct<0 || toAcct>=theUser.numAccounts());
		
		//amount to transfer
		do
		{
			System.out.printf("Enter the amount to transfer(max Rs.%.02f): Rs.",acctBal);
			amount = sc.nextDouble();
			
			if(amount<0)
			{
				System.out.println("Amount must be greater than zero");
			}
			else if(amount>acctBal)
			{
				System.out.printf("Amount must not be greater than\n balance of Rs.%.02f\n",acctBal);
			}
		}while(amount <0 || amount>acctBal);
		
		//do transfer
		theUser.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to account %s",theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct,amount,String.format("Transfer to account %s",theUser.getAcctUUID(fromAcct)));
		
		
		
	}//end transferFunds
	
	
	public static void withdrawFunds(User theUser,Scanner sc)
	{
		int fromAcct;
		String memo;
		double amount;
		double acctBal;
		
		//account to transfer from
		do{
			System.out.printf("Enter the number (1-%d) of the account to withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if(fromAcct <0 || fromAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid Account.Please try again.");
			}
		}while(fromAcct<0 || fromAcct>=theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//amount to transfer
		do
		{
			System.out.printf("Enter the amount to withdraw(max Rs.%.02f): Rs.",acctBal);
			amount = sc.nextDouble();
			
			if(amount<0)
			{
				System.out.println("Amount must be greater than zero");
			}
			else if(amount>acctBal)
			{
				System.out.printf("Amount must not be greater than\n balance of Rs.%.02f\n",acctBal);
			}
		}while(amount <0 || amount>acctBal);
		
		sc.nextLine();
		
		//get the memo
		System.out.printf("Enter the memo: ");
		memo = sc.nextLine();
		
		//do withdrawl
		theUser.addAcctTransaction(fromAcct,-1*amount,memo);
		
	}//end withdrawlFunds
	
	public static void depositFunds(User theUser,Scanner sc)
	{
		int toAcct;
		String memo;
		double amount;
		double acctBal;
		
		//account to transfer from
		do{
			System.out.printf("Enter the number (1-%d) of the account  to deposit in: ",theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if(toAcct <0 || toAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid Account.Please try again.");
			}
		}while(toAcct<0 || toAcct>=theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(toAcct);
		
		//amount to transfer
		do
		{
			System.out.printf("Enter the amount to deposit(max Rs.%.02f): Rs.",acctBal);
			amount = sc.nextDouble();
			
			if(amount<0)
			{
				System.out.println("Amount must be greater than zero");
			}
			
		}while(amount <0);
		
		sc.nextLine();
		
		//get the memo
		System.out.printf("Enter the memo: ");
		memo = sc.nextLine();
		
		//do withdrawl
		theUser.addAcctTransaction(toAcct,amount,memo);
	}//end depositFunds
	
}//end class ATM