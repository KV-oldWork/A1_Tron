

public class testPlayerClass {
}
    private int xPos = 10, yPos = 40;
    public void tick()
    {
        if(bikesBody.size() == 0)
        {
            b = new ObjectPiece(xPos, yPos, 10);
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

        if(ticks > 1)
        {
            if(right) xPos += 1;
            if(left) xPos -= 1;
            if(up) yPos -= 1;
            if(down) yPos += 1;

            ticks = 0;

            b = new ObjectPiece(xPos, yPos, 10);
            bikesBody.add(b);

            if(bikesBody.size() > size)
            {
                bikesBody.remove(0);
            }
        }
    }