import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MulticastServer
{
    Integer numberOfPlayers = 1;
    ArrayList<String> playerDetails = new ArrayList<String>(4*numberOfPlayers);


    final static String ADDR = "224.0.0.3";
    final static int PORT = 8888;
    public static void main(String[]args) throws IOException, UnknownHostException
    {
        InetAddress address = InetAddress.getByName(ADDR);
        byte[] messageBuffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(PORT);
        socket.joinGroup(address);


        DatagramPacket receivePacket = new DatagramPacket(messageBuffer,messageBuffer.length);
        socket.receive(receivePacket);

        String resultStr = new String(messageBuffer, 0, messageBuffer.length);
        System.out.println("recieved: " + resultStr);
    }
}