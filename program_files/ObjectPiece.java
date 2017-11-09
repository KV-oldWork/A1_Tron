import java.awt.*;

public class ObjectPiece
{
    private int xPos, yPos, width, height, color;
    public ObjectPiece(int xPos, int yPos, int pixSize, int color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = pixSize;
        this.height = pixSize;
        this.color = color;

    }

    public void draw(Graphics g)
    {
        if(color == 1)
        {
            g.setColor(Color.BLUE);
            g.fillRect(xPos * width, yPos * height, width, height);
            g.setColor(Color.BLUE);
            g.fillRect(xPos * width + 2, yPos *height + 2, width - 4, height - 4);
        }
        if(color == 2)
        {
            g.setColor(Color.BLACK);
            g.fillRect(xPos * width, yPos * height, width, height);
            g.setColor(Color.BLACK);
            g.fillRect(xPos * width + 2, yPos *height + 2, width - 4, height - 4);
        }
        if(color == 3)
        {
            g.setColor(Color.GREEN);
            g.fillRect(xPos * width, yPos * height, width, height);
            g.setColor(Color.GREEN);
            g.fillRect(xPos * width + 2, yPos *height + 2, width - 4, height - 4);
        }
        if(color == 4)
        {
            g.setColor(Color.RED);
            g.fillRect(xPos * width, yPos * height, width, height);
            g.setColor(Color.RED);
            g.fillRect(xPos * width + 2, yPos *height + 2, width - 4, height - 4);
        }
    }

    public int getxPos()
    {
        return xPos;
    }

    public void setxPos(int xPos)
    {
        this.xPos = xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }
}
