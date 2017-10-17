public class Test_TimerTesting
{
    static String message = "This is testing how to implement a timer";
    static Integer count = 0, max = 5;

    public static void main (String[] args) throws Exception {
        while(count <= max)
        {
            System.out.println(message + count);
            Thread.sleep(1000);
            count ++;
        }

    }
}
