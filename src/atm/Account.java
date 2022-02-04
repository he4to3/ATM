package atm;

import java.util.ArrayList;

public class Account {
    private String name;
    
    private String uuid;
    
    // информация о держателе
    private User holder;
    
    private ArrayList<Transaction> transactions;
    
    public Account (String name, User holder, Bank theBank) {
        //присваем имя и держателя
        this.name = name;
        this.holder = holder;
        
        // получаем новый  UUID
        this.uuid = theBank.getNewAccountUUID();
        
        //инициируем транзакции
        this.transactions = new ArrayList<Transaction>();
        
       
    }
    public String getUUID() {
        return this.uuid;
    }
    public String getSummaryLine() {
        
        //получаем баланс
        double balance =this.getBalance();
        
        //форматируем отрицательный баланс
        if(balance >=0){
            return String.format("%s : P%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : P(%.02f) : %s", this.uuid, balance, this.name);
        }
    }
    public double getBalance() {
        double balance = 0;
        for(Transaction t : this.transactions) {
            balance  += t.getAmount();
        }
        return balance;
    }
    public void printTransHistory (){
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for(int t = this.transactions.size()-1; t>=0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
    public void addTransaction(double amount, String memo) {
        
        //Create new transaction object and add it to out list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}