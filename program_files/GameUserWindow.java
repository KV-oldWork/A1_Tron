import javax.swing.*;
import java.awt.*;

public class GameUserWindow extends JFrame
{
    JFrame mainFrame = new JFrame("tronBoi");
    JButton randBut1 = new JButton("swag");


    public GameUserWindow()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tron");
        setResizable(false);



        init();
    }

    public void init()
    {
        GameScreen x = new GameScreen();
        displayLabel y = new displayLabel();
        displayLabel y2 = new displayLabel();
        displayLabel y3 = new displayLabel();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(x,BorderLayout.CENTER);
        mainFrame.add(randBut1,BorderLayout.NORTH);
        mainFrame.add(y,BorderLayout.SOUTH);
        mainFrame.add(y2,BorderLayout.EAST);
        mainFrame.add(y3,BorderLayout.WEST);
        mainFrame.pack();
        x.requestFocus();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    ///Black borders of the tron game
    private class displayLabel extends JPanel
    {
        private final Integer minWidth = 100, minHeight = 100;
        public displayLabel()
        {
            setMinimumSize(new Dimension(minWidth, minHeight));
            setBackground(Color.black);
            pack();
            setVisible(true);

        }
    }

    public static void main(String[] args)
    {
        new GameUserWindow();
    }

}
