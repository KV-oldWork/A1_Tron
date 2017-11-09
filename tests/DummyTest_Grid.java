import javax.swing.*;
import java.awt.*;

public class DummyTest_Grid extends JFrame
{
    int gridWidth, gridHeight;
    JFrame gridScreen = new JFrame("testingGrid");

    public DummyTest_Grid(int gridWidth, int gridHeight)
    {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        setResizable(false);

        start();

    }

    public void start()
    {
        randomLabel label1 = new randomLabel();
        gridScreen.setLayout(new BorderLayout());
        gridScreen.add(label1);
        gridScreen.pack();
        gridScreen.setVisible(true);
        gridScreen.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private class randomLabel extends JPanel
    {
        private int width = getGridWidth(), height = getGridHeight();
        private randomLabel()
        {
            setSize(new Dimension(width, height));
            setBackground(Color.YELLOW);
            pack();
            setVisible(true);
        }
    }

    public static void main(String[] args)
    {
        new DummyTest_Grid(500, 500);
    }

    private int getGridWidth()
    {
        return gridWidth;
    }

    private int getGridHeight()
    {
        return gridHeight;
    }
}
