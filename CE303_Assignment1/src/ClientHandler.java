import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private BallGame bg;
    boolean playerHasBall;

    public ClientHandler(Socket socket, BallGame bg) {
        this.socket = socket;
        this.bg = bg;
    }

    @Override
    public void run() {

        int playerID = 0;
        int passingPlayerID;
        int passedPlayerID;

        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "new":

                            //Checks if the initial player list is empty then creates the first player
                            //and sets it so that they have the ball
                            if(bg.getPlayerList().size() == 0){
                                playerID++;
                                bg.createPlayer(playerID, true, true);
                                System.out.println("Player " + playerID + " has the ball.");
                            }

                            //when all the players leave, checks to see if the updated player list is empty
                            //then creates a new player and sets that they have the ball
                            else if(bg.getCurrentPlayerList().size() == 0){
                                playerID = bg.getPlayerList().size() + 1;
                                bg.createPlayer(playerID, true, true);
                                System.out.println("Player " + playerID + " has the ball.");
                            }

                            //keeps adding new players to the game but sets it so that they
                            //do not have the ball
                            else{
                                playerID = bg.getPlayerList().size() + 1;
                                bg.createPlayer(playerID, false, true);
                            }

                            writer.println(playerID);
                            System.out.println("New Connection: Player " + playerID);

                            break;

                        case "playerid":

                            writer.println(bg.playerIDHasBall());
                            break;


                        case "players":

                            writer.println("NUMBER OF PLAYERS IN GAME: " + bg.getCurrentPlayerList().size());
                            writer.println("PLAYERS IN GAME: " + bg.getCurrentPlayerList());

                            break;

                        case "ball":

                            //Checks to see which player has the ball
                            //if the current player has the ball then it prints as below
                            if(bg.playerHasTheBall(playerID) == true){
                                writer.println("You have the ball in your possession.");
                            }

                            //if the current player does not have the ball then it prints as below
                            else{
                                writer.println("Player " + bg.playerIDHasBall() + " has the ball in their possession");
                            }

                            break;

                        case "pass":

                            passingPlayerID = Integer.parseInt(substrings[1]);
                            passedPlayerID = Integer.parseInt(substrings[2]);

                            if(bg.playerIDHasBall() == playerID){

                                if(bg.getIsOnline(passedPlayerID)){

                                    bg.passBall(passingPlayerID, passedPlayerID);
                                    writer.println("Player " + passingPlayerID + " passed the ball to Player " + passedPlayerID);
                                }

                                else{
                                    writer.println("This player is not in the game.");
                                    System.out.println("This player is not in the game.");
                                }

                            }



                            break;


                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        } finally {

            bg.setIsOnline(playerID, false);
            System.out.println("Player " + playerID + " disconnected.");
            playerHasBall = true;

            if(playerID == bg.playerIDHasBall()){

                    try{

                        while(playerHasBall){
                            for(int i = 0; i <= bg.getPlayerList().size(); i++){
                                if(bg.getIsOnline(i)){
                                    bg.passBall(playerID, i);
                                    playerHasBall = false;

                                }

                            }
                        }


                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
            }



        }
    }
}
