import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel implements Runnable
{
    public static final int Width = 800, Height = 800;
    private Thread thread;
    private boolean running = false;

    private final double UPDATE_CAP = 1.0/20.0;

    private ObjectPiece b;
    private ArrayList<ObjectPiece> bikesBody;

//    TODO: private int numberOfPlayers; (total number of players connectected
//    to the server, loops through drawing method)
    Player clientSidePlayer = new Player(1, 1, 0, 1, "dad", false);

    private int clientSideSize = 4;
    private boolean up, down, left, right;

    private MulticastClient socketClient;
    private MulticastServer socketServer;

    private int ticks = 0;

    private userKey userKey;

    public GameScreen()
    {
        this.right = true;
        this.left = false;
        this.up = false;
        this.down = false;
        setFocusable(true);
        userKey = new userKey();
        addKeyListener(userKey);

        setPreferredSize(new Dimension(Width, Height));

        bikesBody = new ArrayList<ObjectPiece>();

        start();
    }


    public void tick()
    {
        if(bikesBody.size() == 0)
        {
            b = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10);
            bikesBody.add(b);
        }

        /// stops the game if the bike collides with itself
        for(int i = 0; i < bikesBody.size(); i++)
        {
            if(clientSidePlayer.getX() == bikesBody.get(i).getxPos() && clientSidePlayer.getY() == bikesBody.get(i).getyPos())
            {
                if(i != bikesBody.size() - 1)
                {
                    stop();
                }
            }
        }

        /// stops the game if the bike goes out of bounds
        if(clientSidePlayer.getX() < 1 || clientSidePlayer.getX() > 78 || clientSidePlayer.getY() < 1 || clientSidePlayer.getY() > 78)
        {
            stop();
        }

        ticks++;

        if(ticks > 1)
        {
            int currentX = clientSidePlayer.getX(), currentY = clientSidePlayer.getY();

            if(right) clientSidePlayer.setX(currentX +=1);
            if(left) clientSidePlayer.setX(currentX -= 1) ;
            if(up) clientSidePlayer.setY(currentY -= 1) ;
            if(down) clientSidePlayer.setY(currentY += 1) ;

            ticks = 0;

            b = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10);
            bikesBody.add(b);

            // Removes a block if it exceeds the maximum number of size bois.
            if(bikesBody.size() > clientSideSize)
            {
                bikesBody.remove(0);
            }

            ///TODO: this line of code will eventually send the packet with details about this player.
            socketClient.sendData("running".getBytes());
        }
    }

    public void paint(Graphics g)
    {
        g.clearRect(0, 0, Width, Height);

        ///draws a number of lines over the screen, for testing purposes
        g.setColor(Color.BLACK);
        for(int i = 0; i < Width / 10; i++)
        {
            g.drawLine(i * 10, 0, i * 10, Height);
        }
        for(int i = 0; i < Height / 10; i ++)
        {
            g.drawLine(0, i * 10, Width, i * 10);
        }

        for(int i = 0; i < bikesBody.size(); i ++)
        {
            bikesBody.get(i).draw(g);
        }
    }

    public void start()
    {

        ///Asks the user if they want to host the server
        if(JOptionPane.showConfirmDialog(this, "Do you want to run the server?")==0)
        {
            socketServer = new MulticastServer();
            socketServer.start();
        }

        String name = JOptionPane.showInputDialog("What's your name?");
        String getColor = JOptionPane.showInputDialog("What's your color? (1-4)");
        Integer color = Integer.parseInt(getColor);

        // randomizes spawn position of the main player
        Random rand = new Random();
        int x = rand.nextInt(50) + 20;
        int y = rand.nextInt(50) + 20;
        socketClient = new MulticastClient( "localHost");
        socketClient.start();
        clientSidePlayer.setX(x);
        clientSidePlayer.setY(y);
        clientSidePlayer.setColour(color);
        clientSidePlayer.setPlayerName(name);
        socketClient.sendData("Connected to server".getBytes());
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ///your trying to make a 10 second count down from the server to user in waiting period, good luck future kyle. You'll need it.
        Integer serverStatus = socketClient.getServerStatus();

        while (serverStatus == 2)
        {
            serverStatus = socketClient.getServerStatus();
            socketClient.sendData("waiting(IN GAME SCREEN".getBytes());
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        running = true;
        thread = new Thread(this, "Game boop");
        thread.start();

    }

    public void stop()
    {
        running = false;
        try
        {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    ///game loop
    public void run()
    {
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running)
        {
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= UPDATE_CAP)
            {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                if(frameTime >= 1.0)
                {
                    frameTime = 0;
                    ///fps = frames;
                    frames = 0;
                    ///System.out.println(fps);
                }
                tick();

            }

            if(render)
            {
                repaint();
                frames ++;
            }
            else
                {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    ///Gets User KeyBoard Input
    private class userKey implements KeyListener
    {
        public void keyTyped(KeyEvent e)
        {
        }

        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_RIGHT && !left)
            {
                up = false;
                down = false;
                left = false;
                right = true;
            }
            if(key== KeyEvent.VK_LEFT && !right)
            {
                down = false;
                up = false;
                left = true;
                right = false;
            }

            if(key == KeyEvent.VK_UP && !down)
            {
                up = true;
                down = false;
                left = false;
                right = false;
            }

            if(key == KeyEvent.VK_DOWN && !up)
            {
                up = false;
                down = true;
                left = false;
                right = false;

            }


        }

        public void keyReleased(KeyEvent e)
        {
        }
    }

}
