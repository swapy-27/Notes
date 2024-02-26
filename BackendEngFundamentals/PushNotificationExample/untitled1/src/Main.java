import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Socket client = new Socket("localhost",6688);
        Scanner sc = new Scanner(System.in);
        DataOutputStream dout = new DataOutputStream(client.getOutputStream());

        while(true){
            String s = sc.next();
            System.out.println(s);
            dout.writeUTF(s);
            dout.flush();
        }
    }
}