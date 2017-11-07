import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MulticastClient extends Thread
{
    private InetAddress ADDR;
    final static int PORT = 8888;
    private DatagramSocket socket;
    private Integer serverStatus, numberOfPlayers;
    /// 1 is player connected, 2 is waiting for players, 3 is in game
    private String finalMessage;



    public MulticastClient( String ADDR)
    {
        try {
            this.socket = new DatagramSocket();
            this.ADDR = InetAddress.getByName(ADDR);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        Thread newThread = new Thread();
        newThread.start();
        while (true)
        {
            String connectedPlayerWord = "Connected", preGameWord = "waiting", inGameWord = "Running";
            byte[] messageBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            System.out.println("Server - "+ message);

            ///checks for players connecting
//            if(message.toLowerCase().indexOf(playersNumWord.toLowerCase()) != -1)
//            {
//                String poop = message;
//                List<String> numberFindList = new ArrayList<String>(Arrays.asList(poop.split(" ")));
//                String coolString = numberFindList.get(3);
//                //I guess you don't have to have it as a int to send it away, maybe convert it to a string later?
//                //coolString = coolString.replaceAll("\\s+","");
//                //Integer wow = Integer.parseInt(coolString);
//            }

            if(message.toLowerCase().indexOf(connectedPlayerWord.toLowerCase()) != -1)
            {
                serverStatus = 1;
            }

            if(message.toLowerCase().indexOf(preGameWord.toLowerCase()) != -1)
            {
                serverStatus = 2;

            }

            if(message.toLowerCase().indexOf(inGameWord.toLowerCase()) != -1)
            {
                serverStatus = 3;
                if (message.toLowerCase().indexOf("Game is running".toLowerCase()) == -1)
                {
                    ArrayList<String> findingNumOfPlayers = new ArrayList<String>(Arrays.asList(message.split(" ")));
                    numberOfPlayers = (findingNumOfPlayers.size()-1) /6;
                }
                finalMessage = message;
            }

        }
    }

    public void sendData(byte[] messageBuffer)
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

    public Integer getServerStatus()
    {
        return serverStatus;
    }

    public Integer getNumberOfPlayers()
    {

        return numberOfPlayers;
    }

    public String getFinalMessage()
    {
        return finalMessage;
    }
}
