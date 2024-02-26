import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public  void start() throws IOException {
        ServerSocket ss = new ServerSocket(6688);
        List<Socket> connections = new ArrayList<>();

        while (true) {
            Socket s = ss.accept(); // Wait for a client to connect
            System.out.println("connection added " + s );
            // Create a thread to handle the client communication
            Thread clientThread = new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String str;

                    // Read messages from the client until "stop" is received
                    while (!(str = br.readLine()).equalsIgnoreCase("stop")) {
                        System.out.println("Client Says: " + str);

                        // Send the received message to all connected clients
                        for (Socket connection : connections) {
                            DataOutputStream dout = new DataOutputStream(connection.getOutputStream());
                            dout.writeUTF(str);
                            dout.flush();
                        }
                    }

                    // Remove the disconnected client from the list
                    connections.remove(s);
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Add the client's socket to the list of connections and start the client thread
            connections.add(s);
            clientThread.start();

        }
    }
}
