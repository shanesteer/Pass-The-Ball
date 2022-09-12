import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BallGame {
    private final TreeMap<Integer, Player> players = new TreeMap<>();

    boolean playerHasTheBall = false;
    int playerIDHasBall = 0;

    //Creating the player
    public void createPlayer(int playerID, boolean playerHasBall, boolean isOnline)
    {

        //initializing which player has the ball and their online status
        Player player = new Player(playerID, playerHasBall, isOnline);
        player.setPlayerHasBall(playerHasBall);
        player.setIsOnline(isOnline);
        players.put(playerID, player);

    }

    //gets the total number of players that have joined the game
    public List<Integer> getPlayerList(){
        List<Integer> result = new ArrayList<Integer>();

        for(Player player : players.values()){

                result.add(player.getPlayerID());

        }

        return result;
    }

    //gets the list of players that are online
    public List<Integer> getCurrentPlayerList(){
        List<Integer> result = new ArrayList<Integer>();


        //Checks to see which players are online then adds them to the list
        for(Player player : players.values()){
            if(player.getIsOnline() == true){
                result.add(player.getPlayerID());
            }

        }

        return result;
    }

    //sets the players online status
    public void setIsOnline(int playerID, boolean isOnline){

        players.get(playerID).setIsOnline(isOnline);

    }


    //checks to see which players are online by checking which ID is in the
    //current player list and returns true if they are online
    public boolean getIsOnline(int playerID){

        if(getCurrentPlayerList().contains(playerID)){

            if(players.get(playerID).getIsOnline()){
                return true;
            }
            else {
                return false;
            }

        }

        else{
            return false;
        }

    }


    public void passBall(int playerID, int passedPlayerID){

        synchronized (players){
            if(players.get(playerID).getPlayerHasBall()){

                if(playerID != passedPlayerID && players.get(passedPlayerID).getIsOnline()){

                    players.get(playerID).setPlayerHasBall(false);
                    players.get(passedPlayerID).setPlayerHasBall(true);

                    System.out.println("Player " + playerID + " has passed the ball to the player " + passedPlayerID);
                }

                else{
                    System.out.println("The ball was not passed to anyone. The ball remains in " + passedPlayerID + " possession.");
                }

            }

        }
        //return null;
    }


    //Checks whether the player has the ball or not
    public boolean playerHasTheBall(int playerID){

        for(Player player : players.values()){
            if(playerID == player.getPlayerID()){
                playerHasTheBall = player.getPlayerHasBall();
            }
        }

        return playerHasTheBall;
    }


    //gets the ID of the player with the ball
    public int playerIDHasBall(){

        for(Player player : players.values()){
            if(player.getPlayerHasBall() == true){
                playerIDHasBall = player.getPlayerID();
            }
        }

        return playerIDHasBall;
    }

}