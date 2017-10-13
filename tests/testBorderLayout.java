import javax.swing.*;
import java.awt.*;

public class testBorderLayout
{
    JFrame frame1 = new JFrame("BorderLayout Demo");
    JButton btn1 = new JButton("nowth");
    JButton btn2 = new JButton("sowfth");
    JButton btn3 = new JButton("est");
    JButton btn4 = new JButton("westsss");
    JButton btn5 = new JButton("CENTA BOIZ");

    public testBorderLayout()
    {
        frame1.setLayout(new BorderLayout());
        frame1.add(btn1,BorderLayout.NORTH);
        frame1.add(btn2,BorderLayout.SOUTH);
        frame1.add(btn3,BorderLayout.EAST);
        frame1.add(btn4,BorderLayout.WEST);
        frame1.add(btn5,BorderLayout.CENTER);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }

    public static void main(String[] args) {
        new testBorderLayout();

    }
}
