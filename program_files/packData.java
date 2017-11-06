public class packData
{
    Integer x, y, score, color;
    String playerName;
    Boolean trailStat;

    public packData(int x, int y, int score, int color, String playerName, boolean trailStat)
    {
        this.x = x;
        this.y = y;
        this.score = score;
        this.color = color;
        this.playerName = playerName;
        this.trailStat = trailStat;
    }

    public String getX()
    {
        String convertX = x.toString();
        return convertX;
    }

    public String getY()
    {
        String convertY = y.toString();
        return convertY;
    }

    public String getScore()
    {
        String convertScore = score.toString();
        return convertScore;
    }

    public String getColor()
    {
        String convertColor = color.toString();
        return convertColor;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public String getTrailStat()
    {
        String convertTrailStat = trailStat.toString();
        return convertTrailStat;
    }

    public String buildStuffs()
    {
        String totals = getX()+" "+getY()+" "+getScore()+" "+getColor()+" "+getPlayerName()+" "+getTrailStat();
        return totals;
    }



}
