import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame
{
    public Window()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tron");
        setResizable(false);

        init();
    }

    public void init()
    {
        setLayout(new GridLayout(1, 1, 0, 0));
        Screen x = new Screen();
        add(x);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Window();
    }
}
