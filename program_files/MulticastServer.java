import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastServer extends Thread {
    Integer numberOfPlayers = 0;
    long waitTimeLimit = 90000 * 70000, startTime;
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
            String connectedPlayerWord = "connected", preGameWord = "waiting", inGameWord = "Running";
            byte[] messageBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());

            ///if new player connects, plus 1
            if(message.toLowerCase().indexOf(connectedPlayerWord.toLowerCase()) != -1)
            {
                sendData("Successful: now waiting".getBytes(), packet.getAddress(), packet.getPort());
                numberOfPlayers ++;
            }

            ///if client is sending waiting phase messages
            if(message.toLowerCase().indexOf(preGameWord.toLowerCase()) != -1)
            {
                sendData("waiting for players".getBytes(), packet.getAddress(), packet.getPort());
                long startTime = System.nanoTime();
                if (startTime > waitTimeLimit)
                {
                    sendData("game is running".getBytes(), packet.getAddress(), packet.getPort());
                }
            }

            ///if game has started
            System.out.println("client ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]- " + message);
            if (message.trim().equalsIgnoreCase("RUNNING"))
            {
                sendData("program is running".getBytes(), packet.getAddress(), packet.getPort());
                System.out.println("dude, theres "+numberOfPlayers+" players connected!!!");
            }
        }

    }

    //function that sends a message to the client
    public void sendData(byte[] messageBuffer, InetAddress ADDR, int PORT)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length, ADDR, PORT);
                try
                {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
