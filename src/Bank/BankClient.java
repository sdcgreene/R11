import java.io.*;
import java.net.Socket;

public class BankClient {
    public static void main(String[] args) throws IOException {
        Socket sock = new Socket("localhost", 1234);
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
        
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Command: ");
            String cmd = stdin.readLine();
            if (cmd.isEmpty()) { break; }
            out.println(cmd);
            System.out.println("Response: " + in.readLine());
        }
    }
}
