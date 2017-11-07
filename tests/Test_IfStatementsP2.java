import java.util.ArrayList;
import java.util.Arrays;

public class Test_IfStatementsP2
{
    public static void main(String[] args)
    {
        String message = "wow this program is totally running";
        Integer numberOfPlayers;
        String aMessage;
        if(message.toLowerCase().indexOf("running".toLowerCase()) != -1)
        {
            if (message.toLowerCase().contains("ally"))
            {
                System.out.println("THE SERVER GETS HERE XXXXXXXXXXX");
                ArrayList<String> findingNumOfPlayers = new ArrayList<String>(Arrays.asList(message.split(" ")));
                numberOfPlayers = (findingNumOfPlayers.size()-1) /6;
                aMessage = message;
            }
        }
    }
}
