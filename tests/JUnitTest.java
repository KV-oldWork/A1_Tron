import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;

public class JUnitTest
{
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testGrid()
    {
        GameScreen testScreen = new GameScreen();
        Assert.assertEquals(800, testScreen.getWidth());
        Assert.assertEquals(800, testScreen.getHeight());
    }

    @Test
    public void testTrailStatus()
    {
        assert !thrown.equals(ExpectedException.none());

        GameScreen testTrail = new GameScreen();
        Assert.assertEquals(true, testTrail.getTrailStatus());
    }

    @Test
    public void testClientValues()
    {
        ///Tests
        assert !thrown.equals(ExpectedException.none());

        GameScreen testingServer = new GameScreen();
        int numPlayers = testingServer.getNumPlayers();
        Assert.assertEquals(1, numPlayers);

        String serverMessage = testingServer.getServerMessage();
        Assert.assertEquals(testingServer.getServerMessage(),serverMessage);
    }

    @Test
    public void testClientToServer()
    {
        ///after user sends "connected to server", the server should go into the pre-game phase.
        assert !thrown.equals(ExpectedException.none());

        MulticastClient socketClient = new MulticastClient("localHost");
        socketClient.start();
        MulticastServer socketServer = new MulticastServer();
        socketServer.start();
        int serverStatus = 2;

        socketClient.sendData(("Connected to server "+1+" "+ 1 +" "+ 0 +" " +1 + " sam" +" "+ false).getBytes());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int serverRecievedStatus = socketClient.getServerStatus();
        Assert.assertEquals(serverStatus, serverRecievedStatus);
    }

    @Test
    public void testColorSetter()
    {
        assert !thrown.equals(ExpectedException.none());

        GameScreen colorTest = new GameScreen();
        int colorValue = colorTest.getColor();
        Assert.assertEquals(1, colorValue);
    }

}
