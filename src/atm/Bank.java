package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    
    private ArrayList<User> users;
    
    private ArrayList<Account> accounts;
    
    public Bank (String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }
    
    // создаем новый  универсальный айди юзеру
    public String getNewUserUUID() {
        //инициализируем
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        
        //продолжаем создавать пока не сделаем уникальный айди
        do {
            //создаем цифры
            uuid = "";
            for(int i =0; i<len; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            // удостовериваемся что айди уникальный
            nonUnique = false;
            for (User u: this.users) {
                if(uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        
        return uuid;
    }
    // создаем универсальный айди для аакаунта
    public String getNewAccountUUID() {
         //инициализируем
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        
        //продолжаем создавать пока не сделаем уникальный айди
        do {
            //создаем цифры
            uuid = "";
            for(int i =0; i<len; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            // удостовериваемся что айди уникальный
            nonUnique = false;
            for (Account a: this.accounts) {
                if(uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        
        return uuid;
        
    }
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }
    public User addUser(String firstName, String lastName, String pin) {
        
        //создаем нового юзера и добавляем в лист
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        
        //создаем сохраненный акк для юзера
        Account newAccount = new Account("Savings", newUser, this); 
         //добавляем держателя и банк лист
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);
        
        return newUser;
    }
    public User userLogin(String userID, String pin) {
        
        //ищем через лист юзеров
        for(User u: this.users) {
            
            // проверяем айди юзера если нашли его
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        
        //если не нашли юзера или неправельный пин
        return null;
    }
    public String getName(){
        return this.name;
    }
}