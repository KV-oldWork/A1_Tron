import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MulticastServer extends Thread {
    Integer numberOfPlayers = 0, waitTimer =0;
    long startTime;
    ArrayList<String> playerDetails = new ArrayList<String>(4 * numberOfPlayers);
    final static int PORT = 8888;
    private DatagramSocket socket;
    List<Player> playerList = new ArrayList<Player>();
    private boolean preGame = true, inGame = false, finGame = false;



    public MulticastServer() {
        try {
            this.socket = new DatagramSocket(8888);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Thread newThread = new Thread();
        newThread.start();
        while (true) {
            String connectedPlayerWord = "connected", preGameWord = "waiting", inGameWord = "running";
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

                //takes the connection message from user and converts it into a player object
                List<String>  holdingList = new ArrayList<String>(Arrays.asList(message.split(" ")));
                Integer holdX = Integer.parseInt(holdingList.get(3)), holdY = Integer.parseInt(holdingList.get(4));
                Integer holdScore = Integer.parseInt(holdingList.get(5)), holdColor = Integer.parseInt(holdingList.get(6));
                Boolean holdTrail = Boolean.parseBoolean(holdingList.get(8));
                playerList.add(new Player(holdX, holdY, holdScore, holdColor, holdingList.get(7), holdTrail));
                waitTimer = 0;

                ///THIS WORKS LEL
                System.out.println(playerList.get(0).getX());
                numberOfPlayers ++;
            }

            ///if client is sending waiting phase messages
            if(message.toLowerCase().indexOf(preGameWord.toLowerCase()) != -1)
            {
                sendData("waiting for players".getBytes(), packet.getAddress(), packet.getPort());
                waitTimer ++;
                System.out.println(waitTimer);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ///Sends a running message to client if the server time is done
                if (waitTimer >= 5)
                {
                    String numberOfPlayersString = numberOfPlayers.toString();
                    sendData(("Number of players: "+numberOfPlayersString).getBytes(), packet.getAddress(), packet.getPort());
                    sendData("Game is running...".getBytes(), packet.getAddress(), packet.getPort());
                }
            }

            ///if game has started

            if (message.toLowerCase().indexOf(inGameWord.toLowerCase()) != -1)
            {
                System.out.println("Client IP: ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]- " + message);
                ArrayList<String> messageList = new ArrayList<String>();
                for(int i = 0; numberOfPlayers > i;)
                {
                    packData packPlayers = new packData(playerList.get(i).getX(), playerList.get(i).getY(), playerList.get(i).getScore(), playerList.get(i).getColour(), playerList.get(i).getPlayerName(), playerList.get(i).getTrailStatus());
                    String combinedVars = packPlayers.stringPacker();
                    messageList.add(combinedVars);
                    i++;
                }
                String finalMessage = String.join(" ", messageList);
                sendData(("RunningNow "+ finalMessage).getBytes(), packet.getAddress(), packet.getPort());
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

    public Integer getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public Integer getWaitTimer()
    {
        return waitTimer;
    }
}
