import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable
{
    public static final int Width = 800, Height = 800;
    private Thread thread;
    private boolean running = false;

    private Bike b;
    private ArrayList<Bike> bikesBody;

    private int xPos = 10, yPos = 40;
    private int size = 5;

    private boolean up = true, down = false, left = false, right = false;

    private int ticks = 0;

    private userKey userKey;

    public Screen()
    {
        setFocusable(true);
        userKey = new userKey();
        addKeyListener(userKey);

        setPreferredSize(new Dimension(Width, Height));

        bikesBody = new ArrayList<Bike>();

        start();
    }

    public void tick()
    {
        if(bikesBody.size() == 0)
        {
            b = new Bike(xPos, yPos, 10);
            bikesBody.add(b);
        }

        /// stops the game if the bike collides with itself
        for(int i = 0; i < bikesBody.size(); i++)
        {
            if(xPos == bikesBody.get(i).getxPos() && yPos == bikesBody.get(i).getyPos())
            {
                if(i != bikesBody.size() - 1)
                {
                    stop();
                }
            }
        }

        /// stops the game if the bike goes out of bounds
        if(xPos < 1 || xPos > 78 || yPos < 1 || yPos > 78)
        {
            stop();
        }

        ticks++;
        if(ticks > 400000)
        {
            if(right) xPos++;
            if(left) xPos--;
            if(up) yPos--;
            if(down) yPos++;

            ticks = 0;

            b = new Bike(xPos, yPos, 10);
            bikesBody.add(b);

            if(bikesBody.size() > size)
            {
                bikesBody.remove(0);
            }
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
        running = true;
        thread = new Thread(this, "Game Loop");
        thread.start();
    }

    public void stop()
    {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while(running)
        {
            tick();
            repaint();
        }
    }
    private class userKey implements KeyListener
    {
        public void keyTyped(KeyEvent e)
        {
        }

        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            if(up == true)
            {
                if(key == KeyEvent.VK_RIGHT)
                {
                    up = false;
                    down = false;
                    left = false;
                    right = true;
                }
                if(key== KeyEvent.VK_LEFT)
                {
                    down = false;
                    up = false;
                    left = true;
                    right = false;
                }
            }
            if(left == true)
            {
                if(key == KeyEvent.VK_RIGHT)
                {
                    up = true;
                    down = false;
                    left = false;
                    right = false;
                }
                if(key== KeyEvent.VK_LEFT)
                {
                    down = false;
                    up = false;
                    left = true;
                    right = false;
                }
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
