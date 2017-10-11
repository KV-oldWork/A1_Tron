import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel implements Runnable
{
    public static final int Width = 800, Height = 800;
    private Thread thread;
    private boolean running = false;

    public Screen()
    {
        setPreferredSize(new Dimension(Width, Height));

        start();
    }

    public void tick()
    {
        System.out.println("Running");
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        for(int i = 0; i < Width / 10; i++)
        {
            g.drawLine(i * 10, 0, i * 10, Height);
        }
        for(int i = 0; i < Height / 10; i ++)
        {
            g.drawLine(0, i * 10, Width, i * 10);
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

    }

    public void run()
    {
        while(running)
        {
            tick();
            repaint();
        }
    }
}
