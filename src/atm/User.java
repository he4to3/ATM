package atm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private String firstName;
    
    private String lastName;
    
    private String uuid;
    
    private byte pinHash[];
    
    private ArrayList<Account> accounts;
    
    public User(String firstName, String lastName, String pin, Bank theBank) {
        // присваеваем переменные выше
        this.firstName = firstName;
        this.lastName = lastName;
        // храним пин хеш MD5, вместо оригинального для безопасности
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        // получиаем новый универсальный ID (uuid) для клиента
        this.uuid = theBank.getNewUserUUID();
        
        //создаем пустой аккаунт
        this.accounts = new ArrayList<Account>();
        
        // публикуем лог текст
        System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.uuid);
    }
    //акк для юзера
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }
    
    public String getUUID() {
        return this.uuid;
    }
    public  boolean validatePin(String aPin) {
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        return false;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    public void printAccountsSummary() {
        System.out.printf("\n\n%s accounts summary\n", this.firstName);
        for(int i= 0; i<this.accounts.size(); i++){
            System.out.printf("  %d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
    }
        System.out.println();
}
    public int numAccounts(){
        return this.accounts.size();
    }
    public  void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction(int acctIdx, double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}