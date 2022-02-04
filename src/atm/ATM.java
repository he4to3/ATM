
package atm;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Bank theBank = new Bank("Bank of Vladi");
        
        //добавляем юзера и создаем сохр акк
        User aUser = theBank.addUser("Vlad", "Boya", "1234");
        
        //add a cheking account for our user
        Account newAccount  = new Account("Cheking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        
        User curUser;
        while (true) {
            //Stay in the login prompt untill successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);
            
            //Stay in mainmenu untill users quits
            ATM.printUserMenu(curUser, sc);
        }
    }
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        
        //init
        String userID;
        String pin;
        User  authUser;
        
        //запрашиваем юзера айди и пин 
        do{
            System.out.printf("\n\n Приветствую в %s\n\n",  theBank.getName() );
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: " );
            pin = sc.nextLine();
            
            //try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Неверный логин или пин. " + " Побробуйте снова.");
            }
        }while(authUser == null); // continue looping until succsesful login
        
        return authUser;
    }
    public static void printUserMenu(User theUser, Scanner sc) {
        
    // print a summary of the user's accounts
    theUser.printAccountsSummary();

    int choice;
    
    //user menu
    do{
        System.out.printf("Здраствуйте, %s, что вы хотите сделать?\n", theUser.getFirstName());
        System.out.println(" 1) Показать историю переводов.");
        System.out.println(" 2) Вывод.");
        System.out.println(" 3) Пополнить.");
        System.out.println(" 4) Перевести.");
        System.out.println(" 5) Выход.");
        System.out.println();
        System.out.println("Введите цифру соответвутствующую выбору: ");
        choice = sc.nextInt();
            if(choice <1 || choice >5) {
            System.out.println("Неверный ввод. Пожалуйста, выберете 1-5");
            }
        }while(choice <1 || choice >5);
     switch (choice)  {
         case 1:
             ATM.showTransHistory(theUser, sc);
             break;
         case 2:
             ATM.withdrawlFunds(theUser, sc);
             break;
         case 3:
             ATM.depositFunds(theUser, sc);
             break;
         case 4:
             ATM.transferFunds(theUser, sc);
             break;
         case 5: 
             //gobble up rest of previous input
        sc.nextLine();
        break;
     }
     
     //redisplay this menu unless the user wants to quit
     if(choice != 5) {
         ATM.printUserMenu(theUser, sc);
         
     }
    }
    public static void showTransHistory(User theUser, Scanner sc){
        
        int theAcct;
        
        do{
            System.out.printf("Enter the number (1-%d) of the account /n" + "whose transactions you want to see: ", 
                    theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct <0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(theAcct <0 || theAcct >= theUser.numAccounts());
        
        //print transactions history
        theUser.printAcctTransHistory(theAcct);
        
    }
    
    public static void transferFunds(User theUser, Scanner sc){
        
        //init
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct <0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        
        //get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct <0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct <0 || toAcct >= theUser.numAccounts());
        
        //get the amount transfer
        do{
            System.out.printf("Enter the amount to transfer (max P%.02f): P", acctBal);
            amount = sc.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount must be greater than zero.");
            }else if (amount>acctBal){
                System.out.printf("Amount must be not be greater than\n" +"balance of P%.02f.\n", acctBal);
            }
        }while(amount <0 || amount > acctBal);
        
        //do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }
    
    public static void withdrawlFunds(User theUser, Scanner sc) {
            
        //init
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct <0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        
        //get the amount transfer
        do{
            System.out.printf("Enter the amount to transfer (max P%.02f): P", acctBal);
            amount = sc.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount must be greater than zero.");
            }else if (amount>acctBal){
                System.out.printf("Amount must be not be greater than\n" +"balance of P%.02f.\n", acctBal);
            }
        }while(amount <0 || amount > acctBal);
        
        //gobble up rest of previous input
        sc.nextLine();
        
        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        
        //do the withdrawl
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }
    
    public static void depositFunds(User theUser, Scanner sc) {
             
        //init
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct <0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct <0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        
        //get the amount transfer
        do{
            System.out.printf("Enter the amount to transfer (max P%.02f): P", acctBal);
            amount = sc.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount must be greater than zero.");
            }
        }while(amount <0);
        
        //gobble up rest of previous input
        sc.nextLine();
        
        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        //do the withdrawl
        theUser.addAcctTransaction(toAcct, amount, memo);       
    }
}
