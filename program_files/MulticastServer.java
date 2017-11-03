import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastServer extends Thread {
    Integer numberOfPlayers = 0;
    ArrayList<String> playerDetails = new ArrayList<String>(4 * numberOfPlayers);
    final static int PORT = 8888;
    private DatagramSocket socket;
    private boolean preGame = true, inGame = false, finGame = false;



    public MulticastServer() {
        try {
            this.socket = new DatagramSocket(8888);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            String preGameSearchWord = "Waiting", inGameSearchWord = "Running";
            byte[] messageBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());

            if(message.toLowerCase().indexOf(preGameSearchWord.toLowerCase()) != -1)
            {
                sendData("waiting for players".getBytes(), packet.getAddress(), packet.getPort());
                numberOfPlayers ++;
            }

            System.out.println("client ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]- " + message);
            if (message.trim().equalsIgnoreCase("RUNNING"))
            {
                sendData("program is running".getBytes(), packet.getAddress(), packet.getPort());
                System.out.println("dude, theres "+numberOfPlayers+" players connected!!!");
            }
        }

    }

    //function that sends a message to the client
    public void sendData(byte[] messageBuffer, InetAddress ADDR, int PORT) {
        DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length, ADDR, PORT);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
