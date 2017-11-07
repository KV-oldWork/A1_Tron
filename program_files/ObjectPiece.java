import java.awt.*;

public class ObjectPiece
{
    private int xPos, yPos, width, height;
    public ObjectPiece(int xPos, int yPos, int pixSize)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = pixSize;
        this.height = pixSize;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(xPos * width, yPos * height, width, height);
        g.setColor(Color.GREEN);
        g.fillRect(xPos * width + 2, yPos *height + 2, width - 4, height - 4);
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