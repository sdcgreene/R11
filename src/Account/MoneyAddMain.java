public class MoneyAddMain {
    public static void main(String[] args) {
        final Account account = new Account(0, 0);
        
        MoneyAdd jack = new MoneyAdd("Jack", account, 100);
        MoneyAdd jill = new MoneyAdd("Jill", account, 100);
        
        Thread t1 = new Thread(jack);
        t1.start();
        Thread t2 = new Thread(jill);
        t2.start();
    }
}
