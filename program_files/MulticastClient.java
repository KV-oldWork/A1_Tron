import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;

public class MulticastClient extends Thread
{
    private InetAddress ADDR;
    final static int PORT = 8888;
    private DatagramSocket socket;
    private Integer serverStatus;
    /// 1 is player connected, 2 is waiting for players, 3 is in game
    private String outMessage;
    private Integer counter = 0;


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
            System.out.println("server - "+ message);

            ///checks for players connecting
            if(message.toLowerCase().indexOf(connectedPlayerWord.toLowerCase()) != -1)
            {
                serverStatus = 1;
                System.out.println("serverStatus 1 - connected");
            }

            if(message.toLowerCase().indexOf(preGameWord.toLowerCase()) != -1)
            {
                serverStatus = 2;
                System.out.println("serverStatus2 - waiting");
            }


            if(counter == 20)
            {
                message = "program is running";
            }

            if(message.toLowerCase().indexOf(inGameWord.toLowerCase()) != -1)
            {
                serverStatus = 3;
                System.out.println("serverStatus3 - running");
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

    public String getMessage()
    {
        return outMessage;
    }
}
