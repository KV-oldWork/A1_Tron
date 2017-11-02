import java.util.Random;

public class Player
{
    private int x, y, score, colour;
    private String playerName;
    private boolean trailStatus;

    Player(int x, int y, int score, int colour, String playerName, boolean trailStatus)
    {
        this.x = x;
        this.y = y;
        this.score = score;
        this.colour = colour;
        this.playerName = playerName;
        this.trailStatus = trailStatus;
        setStartingPos();
    }

    public void setStartingPos()
    {
        Random rand = new Random();
        int x = rand.nextInt(650) + 150;
        int y = rand.nextInt(650) + 150;
        System.out.println("random x = "+x+"\nrandom y = "+y);
    }
    public static void main(String[]args)
    {
        new Player(2,2,2,2,"ye",true);
    }

}
