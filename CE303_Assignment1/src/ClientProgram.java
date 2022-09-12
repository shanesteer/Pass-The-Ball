import java.util.Scanner;

public class ClientProgram {


    public static void main(String[] args) {

        int playerID;
        String playerChoice;
        boolean running = true;

        try{
            Scanner in = new Scanner(System.in);

            try  (Client client = new Client()) {
                playerID = client.createPlayer();
                System.out.println("Logged in successfully.");
                System.out.println("Your player ID is " + playerID);

                while (running) {

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("Type 'PlAYERS' (to see which players are in the game)");
                    System.out.println("Type 'BALL' (to see which player has the ball)");
                    System.out.println("Type 'PASS' (to pass the ball to another player)");
                    System.out.println("Type 'CLOSE' (to disconnect from the server)\n");

                    System.out.println("Choice: ");

                    playerChoice = in.nextLine().trim().toUpperCase();

                    switch (playerChoice){
                        case "PLAYERS":
                            client.getCurrentPLayerList();
                            break;

                        case "BALL":
                            client.playerHasBall();
                            break;

                        case "PASS":

                            if(client.playerIDHasBall() == playerID){
                                System.out.println("Who would you like to pass the ball to?");
                                client.getCurrentPLayerList();
                                int passedPlayerID = Integer.parseInt(in.nextLine());
                                client.passBall(playerID, passedPlayerID);

                                /*if(playerID != passedPlayerID){
                                    System.out.println("The ball was passed to Player " + passedPlayerID);
                                }

                                else{
                                    System.out.println("You did not pass the ball to anyone. The ball remains in your possession");
                                }*/

                            }

                            else{
                                System.out.println("You do not have the ball in your possession");
                            }

                            break;

                        case "CLOSE":

                            running = false;

                            break;

                        default:
                            System.out.println("Unknown command: " + playerChoice);
                            continue;

                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

