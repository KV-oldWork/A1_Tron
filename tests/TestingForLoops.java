import java.util.ArrayList;

public class TestingForLoops
{
    public static void main(String[] args) {

        int numOfPlayers = 3;
        for(Integer i = 0; i <= numOfPlayers;)
        {
            String playerDeclare = "Player", playerNumber = i.toString();
            String newString = playerDeclare+playerNumber;
            System.out.println(newString);
            i++;
        }

    }
}
