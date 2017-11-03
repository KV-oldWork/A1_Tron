public class testIf
{
    //Testing the if function that i'm using to determine ammount of players connected to the server.

    public static void main(String[]args)
    {
        String sentence = "woah book, it's a sentense!";
        String search  = "book";
        if (sentence.toLowerCase().indexOf(search.toLowerCase()) != -1 )
        {
            System.out.println("Found it!");
        }

        else
        {
            System.out.println("not found");
        }
    }

}
