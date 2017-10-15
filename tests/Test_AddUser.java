import javax.swing.*;

public class Test_AddUser
{

    public static String getUserName()
    {
        String name;
        name = JOptionPane.showInputDialog("Please enter your name:");
        return name;
    }

    public static String getHostIP()
    {
        String getIP;
        getIP = JOptionPane.showInputDialog("Please enter the host IP:");
        return getIP;
    }

    public static Integer getHostPort()
    {
        Integer portNum;
        String getPort;
        getPort = JOptionPane.showInputDialog("Please enter host port number");
        portNum = Integer.parseInt(getPort);
        return portNum;
    }

    public static void main(String[] args)
    {
        String userName = getUserName();
        String hostIP = getHostIP();
        Integer hostPort = getHostPort();
        System.out.println("Name = " + userName + "\nIP Connecting to = "+ hostIP + "\nPort Connecting to = "+hostPort);
    }
}
