import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class GameScreen extends JPanel implements Runnable
{
    private static final int Width = 800, Height = 800;
    private Thread thread;
    private boolean running = false;
    private ArrayList<ObjectPiece> bikesBody;
    private Integer numPlayers;
    private Player clientSidePlayer;
    private boolean up, down, left, right, trail, speed, firstTime;

    private MulticastClient socketClient;

    private int ticks = 0;

    GameScreen()
    {
        this.right = true;
        this.left = false;
        this.up = false;
        this.down = false;
        this.trail = true;

        setFocusable(true);
        GameScreen.userKey userKey = new userKey();
        addKeyListener(userKey);

        setPreferredSize(new Dimension(Width, Height));

        bikesBody = new ArrayList<>();
        clientSidePlayer = new Player(1, 1, 0, 1, "Sam", true);

        start();
    }


    private void tick()
    {
        //initializes the clients bike(this code does nothing)
//        if(bikesBody.size() == 0)
//        {
//            trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10);
//            bikesBody.add(trailPiece);
//        }

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
            ///client side code to set coordinates.
            String finalMessage = socketClient.getFinalMessage();
            System.out.println("this is the final message"+finalMessage);
            int currentX = clientSidePlayer.getX(), currentY = clientSidePlayer.getY();

            //client position setting based of keyPresses.
            if(speed)
            {
                if(right) clientSidePlayer.setX(currentX +=2);
                if(left) clientSidePlayer.setX(currentX - 2) ;
                if(up) clientSidePlayer.setY(currentY -= 2) ;
                if(down) clientSidePlayer.setY(currentY + 2) ;
                if(trail) clientSidePlayer.setTrailStatus(true);
                if(!trail) clientSidePlayer.setTrailStatus(false);
                switch (ticks = 0) {
                }
            }
            else{
                if(right) clientSidePlayer.setX(currentX +=1);
                if(left) clientSidePlayer.setX(currentX - 1) ;
                if(up) clientSidePlayer.setY(currentY -= 1) ;
                if(down) clientSidePlayer.setY(currentY + 1) ;
                if(trail) clientSidePlayer.setTrailStatus(true);
                if(!trail) clientSidePlayer.setTrailStatus(false);
                switch (ticks = 0) {
                }
            }



            ObjectPiece trailPiece;
            if (!clientSidePlayer.getTrailStatus()) {
            } else {
                if(speed)
                {
                    if(up)
                    {
                        trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY()+1, 10, clientSidePlayer.getColour());
                        bikesBody.add(trailPiece);
                    }
                    if(down)
                    {
                        trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY()-1, 10, clientSidePlayer.getColour());
                        bikesBody.add(trailPiece);
                    }
                    if(left)
                    {
                        trailPiece = new ObjectPiece(clientSidePlayer.getX()+1, clientSidePlayer.getY(), 10, clientSidePlayer.getColour());
                        bikesBody.add(trailPiece);
                    }
                    if(right)
                    {
                        trailPiece = new ObjectPiece(clientSidePlayer.getX()-1, clientSidePlayer.getY(), 10, clientSidePlayer.getColour());
                        bikesBody.add(trailPiece);
                    }
                    System.out.println(firstTime+" this is the first");
                    trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10, clientSidePlayer.getColour());
                    bikesBody.add(trailPiece);

                }
                else{
                    trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10,clientSidePlayer.getColour());
                    bikesBody.add(trailPiece);
                    firstTime = true;
                }

            }
            if (clientSidePlayer.getTrailStatus()) {
            } else {
                trailPiece = new ObjectPiece(clientSidePlayer.getX(), clientSidePlayer.getY(), 10, clientSidePlayer.getColour());
                bikesBody.add(trailPiece);
                int clientSideSize = 1;
                if(bikesBody.size() > clientSideSize)
                {
                    bikesBody.remove(bikesBody.size()-2);
                }

            }

            ///this line of code sends the packet with details about this player.
            packData clientPlayerPack = new packData(clientSidePlayer.getX(), clientSidePlayer.getY(), clientSidePlayer.getScore(),clientSidePlayer.getColour(),clientSidePlayer.getPlayerName(),clientSidePlayer.getTrailStatus());
            String clientPlayerMessage = clientPlayerPack.stringPacker();
            socketClient.sendData(("running: "+clientPlayerMessage).getBytes());

        }
    }

    public void paint(Graphics g)
    {
        g.clearRect(0, 0, Width, Height);

//        ///draws a number of lines over the screen, for testing purposes
//        g.setColor(Color.BLACK);
//        for(int i = 0; i < Width / 10; i++)
//        {
//            g.drawLine(i * 10, 0, i * 10, Height);
//        }
//        for(int i = 0; i < Height / 10; i ++)
//        {
//            g.drawLine(0, i * 10, Width, i * 10);
//        }

        //draws the bikes body part
        for (ObjectPiece aBikesBody : bikesBody) {
            aBikesBody.draw(g);
        }
    }

    private void start()
    {

        ///Asks the user if they want to host the server
        Boolean clientRunningServer = false;
        if(JOptionPane.showConfirmDialog(this, "Do you want to run the server?")==0)
        {
            MulticastServer socketServer = new MulticastServer();
            socketServer.start();
            clientRunningServer = true;
        }

        clientSidePlayer.setPlayerName(JOptionPane.showInputDialog("What's your name?"));
        String getColor = JOptionPane.showInputDialog("What's your color? (1-4)");
        clientSidePlayer.setColour(Integer.parseInt(getColor));

        // randomizes spawn position of the main player
        Random rand = new Random();
        int x = rand.nextInt(50) + 20;
        int y = rand.nextInt(50) + 20;
        socketClient = new MulticastClient( "localHost");
        socketClient.start();
        clientSidePlayer.setX(x);
        clientSidePlayer.setY(y);

        ///this code runs all of the player variables through a string converter, packs them and then finally sends to server.
        packData packClient = new packData(clientSidePlayer.getX(), clientSidePlayer.getY(), clientSidePlayer.getScore(), clientSidePlayer.getColour(), clientSidePlayer.getPlayerName(), clientSidePlayer.getTrailStatus());
        String wowString = packClient.stringPacker();
        socketClient.sendData(("Connected to server: "+wowString).getBytes());
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ///your trying to make a 10 second count down from the server to user in waiting period
        Integer serverStatus = socketClient.getServerStatus();

        while (serverStatus == 2)
        {
            serverStatus = socketClient.getServerStatus();
            socketClient.sendData("waiting".getBytes());
            try {
                Thread.sleep(800
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        packData clientPlayerPack = new packData(clientSidePlayer.getX(), clientSidePlayer.getY(), clientSidePlayer.getScore(),clientSidePlayer.getColour(),clientSidePlayer.getPlayerName(),clientSidePlayer.getTrailStatus());
        String clientPlayerMessage = clientPlayerPack.stringPacker();
        socketClient.sendData(("running: "+clientPlayerMessage).getBytes());

        try {
            System.out.println("I sleep...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(clientRunningServer)
        {
            numPlayers = socketClient.getNumberOfPlayers();
//            yesDaddy = socketClient.getAMessage();
//            System.out.println("THIS IS THE EVIL"+yesDaddy);
        }

        if(!clientRunningServer)
        {
            numPlayers  = socketClient.getNumberOfPlayers();
            System.out.println(numPlayers);
        }
        System.out.println("Game can see number of players, Total is: "+numPlayers);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ///THAT WORKS LEL
        running = true;
        thread = new Thread(this, "Game boop");
        thread.start();





    }

    private void stop()
    {
        running = false;
        System.out.println("Game Over!");
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
        boolean render;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;

        while(running)
        {
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            double UPDATE_CAP = 1.0 / 20.0;
            while(unprocessedTime >= UPDATE_CAP)
            {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                if(frameTime >= 1.0)
                {
                    frameTime = 0;
                    ///fps = frames;
                    ///System.out.println(fps);
                }
                tick();

            }

            if(render)
            {
                repaint();
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
                System.out.println("RIGHT");
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

            if(key == KeyEvent.VK_SPACE)
            {
                if (!trail) {
                    trail = true;
                }
                else trail = false;
            }

            if(key == KeyEvent.VK_W)
            {
                if(!speed)
                {
                    speed = true;
                }
                else
                    {
                        speed = false;
                    }
            }


        }

        public void keyReleased(KeyEvent e)
        {
        }
    }

}
