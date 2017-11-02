import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastServer extends Thread {
    Integer numberOfPlayers = 1;
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
            byte[] messageBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            System.out.println("client ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]- " + message);
            if (message.trim().equalsIgnoreCase("ping")) {
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
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
