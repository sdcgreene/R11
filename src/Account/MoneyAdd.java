public class MoneyAdd implements Runnable{
    
    final String name;
    final Account account;
    final int amount;
    
    public MoneyAdd(String name, Account account, int amount) {
        this.name = name;
        this.account = account;
        this.amount = amount;
    }
    
    public void run() {
        int before = account.getBalance();
        account.addMoney(amount);
        int after = account.getBalance();
        System.out.println(name + " updated balance from $" + before + " to $" + after);
    }
}
