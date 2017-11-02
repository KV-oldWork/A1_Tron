import java.io.IOException;
import java.net.*;

public class MulticastSender
{
    final static String ADDR = "224.0.0.3";
    final static int PORT = 8888;
    public static void main(String[]args) throws IOException, InterruptedException
    {


        InetAddress address = InetAddress.getByName(ADDR);
        DatagramSocket serverSocket = new DatagramSocket();
        String message = "boi";

        DatagramPacket packet = new DatagramPacket(message.getBytes(),message.getBytes().length, address, PORT);

        serverSocket.send(packet);
        System.out.println("sent" + message);
        Thread.sleep(500);



        serverSocket.close();


    }
}
