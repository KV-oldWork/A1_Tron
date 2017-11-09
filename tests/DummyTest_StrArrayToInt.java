import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyTest_StrArrayToInt
{
    public static void main(String[] args)
    {
        String Message = "wow, cool, fresh: 23";
        List<String> newList = new ArrayList<String>(Arrays.asList(Message.split(" ")));
        String thisGuy = newList.get(3);
        Integer thisIsAnInt = Integer.parseInt(thisGuy);
        System.out.println(thisIsAnInt);
        thisIsAnInt += 2;
        System.out.println(thisIsAnInt);

    }

}
