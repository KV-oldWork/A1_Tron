import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.*;

public class MulticastClient extends Thread
{
    private InetAddress ADDR;
    final static int PORT = 8888;
    private DatagramSocket socket;
    private Integer serverStatus;
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
            String preGameSearchWord = "Waiting",inGameSearchWord = "Running";
            byte[] messageBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            System.out.println("server - "+ message);
            if(message.toLowerCase().indexOf(preGameSearchWord.toLowerCase()) != -1  && (counter < 20))
            {
                serverStatus = 1;
                System.out.println(counter);
            }
            counter ++;

            if(message.toLowerCase().indexOf(inGameSearchWord.toLowerCase()) != -1)
            {
                serverStatus = 2;
            }

        }
    }

    public void sendData(byte[] messageBuffer)
    {
        DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length, ADDR, PORT);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
