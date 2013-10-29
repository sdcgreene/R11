import java.io.*;
import java.net.*;

public class BankServer implements Runnable {
    
    private final Account[] accounts = new Account[10];
    
    private final ServerSocket serverSocket;
    private boolean running = true;
    
    public BankServer(int port) throws IOException {
        for (int ii = 0; ii < accounts.length; ii++) {
            accounts[ii] = new Account(0, ii);
        }
        serverSocket = new ServerSocket(port);
    }
    
    private enum Command {
        BALANCE {
            String execute(BankServer bank, String[] args) {
                int id = Integer.parseInt(args[1]);
                return "Account " + id + " has balance $" + bank.accounts[id].getBalance();
            }
        },
        DEPOSIT {
            String execute(BankServer bank, String[] args) {
                int id = Integer.parseInt(args[1]);
                int amount = Integer.parseInt(args[2]);
                bank.accounts[id].addMoney(amount);
                return "Account " + id + " deposited $" + amount;
            }
        },
        // ...
        ;
        abstract String execute(BankServer bank, String[] args);
    }
    
    public void run() {
        while (running) {
            Socket socket = null;
            try {
                System.out.println("Waiting for connection");
                // this call will block until a client connects
                socket = serverSocket.accept();
                System.out.println("New connection!");
                // BufferedReaders and PrintWriters will allow us to read & write lines at a time
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // use autoFlush so we do not have to flush writes manually
                // but note that only certain methods respect autoFlush
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                
                String request = in.readLine();
                String[] args = request.split(" ");
                // succinct but dangerous because valueOf throws unchecked exceptions
                String result = Command.valueOf(args[0]).execute(this, args);
                // anyone can crash our server with a bad command -- never trust user input!
                // see e.g. EnumMap for one alternative idea, and *always* sanitize user input
                out.println(result);
                
            } catch (IOException ioe) {
                if ( ! running) {
                    System.out.println("Server shutting down");
                } else {
                    ioe.printStackTrace();
                }
            } finally {
                if (socket != null && ! socket.isClosed()) {
                    try {
                        // make sure we close the socket (and associated I/O streams)
                        socket.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
    }
    
    void close() throws IOException {
        running = false;
        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        BankServer server = new BankServer(1234);
        Thread thread = new Thread(server, "BankServer");
        thread.start();
        
        System.out.println("Press enter to stop the server");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println("Requesting shutdown...");
        server.close();
        thread.join();
        System.out.println("Done");
    }
}
