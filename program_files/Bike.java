import java.awt.*;

public class Bike
{
    private int xPos, yPos, width, height;
    public Bike(int xPos, int yPos, int pixSize)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        width = pixSize;
        height = pixSize;
    }

    public void tick()
    {

    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(xPos * width, yPos * height, width, height);
        g.setColor(Color.GREEN);
        g.fillRect(xPos * width + 2, yPos *height + 2, width, height);
    }
}
