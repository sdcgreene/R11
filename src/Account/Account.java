/** Bank account with ID number and monetary balance. */
public class Account {
    
    private static int count = 0; // total number of accounts
    
    public final int id; // ID number
    private int balance; // monetary balance
    
    /** Returns the total number of accounts. */
    public static int getCount() {
        return count;
    }
    
    /** Create a new account. */
    public Account(int balance, int id) {
        this.balance = balance;
        this.id = id;
        count += 1;
    }
    
    /** Returns the balance in the account. */
    public int getBalance() {
        return balance;
    }
    
    /** Add a (possibly negative) amount of money to the balance. */
    public void addMoney(int money) { // this code is 100% equivalent to
        int temp = balance;           //   balance += money;
        balance = temp + money;       // but is expanded for clarity
    }
}
