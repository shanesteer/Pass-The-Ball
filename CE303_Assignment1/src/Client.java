import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client implements AutoCloseable {
    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;

    public Client() throws Exception {
        // Connecting to the server and creating objects for communications
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        // Automatically flushes the stream with every command
        writer = new PrintWriter(socket.getOutputStream(), true);

    }

    public int createPlayer(){
        // Sending command
        writer.println("NEW");

        // reading response
        String line = reader.nextLine();
        return Integer.parseInt(line);

    }


    public void getCurrentPLayerList() {
        // sending command
        writer.println("PLAYERS");

        // reading response
        String line = reader.nextLine();
        System.out.println(line);

        // reading response
        String line2 = reader.nextLine();
        System.out.println(line2);
    }

    public boolean playerHasBall(){
        // sending command
        writer.println("BALL");

        // reading response
        String line = reader.nextLine();
        System.out.println(line);

        return Boolean.parseBoolean(line);
    }

    public int playerIDHasBall(){
        writer.println("PLAYERID");
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }


    public String passBall(int playerID, int passedPlayerID){
        // Sending the command
        writer.println("PASS" + " " + playerID + " " + passedPlayerID);

        // Reading the response
        String line = reader.nextLine();
        System.out.println(line);
        return line;
    }



    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
