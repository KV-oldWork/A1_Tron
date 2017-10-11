import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel
{
    public static final int Width = 800, Height = 800;
    public Screen()
    {
        setPreferredSize(new Dimension(Width, Height));
    }
}
