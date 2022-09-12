public class Player {

    private final int playerID;
    private boolean playerHasBall;
    private boolean isOnline;

    public Player(int playerID, boolean playerHasBall, boolean isOnline) {
        this.playerID = playerID;
        this.playerHasBall = playerHasBall;
        this.isOnline = isOnline;
    }

    public int getPlayerID(){ return playerID; }

    public boolean getIsOnline(){return isOnline; }

    public void setIsOnline(boolean newIsOnline){ isOnline = newIsOnline; }

    public boolean getPlayerHasBall(){ return playerHasBall; }

    public void setPlayerHasBall(boolean newPlayerHasBall){ playerHasBall = newPlayerHasBall; }


}
