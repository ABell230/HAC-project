package proj1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerGiven 
{
    DatagramSocket socket = null;

    public ServerGiven() 
    {

    }
    public void createAndListenSocket() 
    {
        try 
        {
            socket = new DatagramSocket(9876);
            byte[] incomingData = new byte[1024];

            while (true) 
            {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, 
                		incomingData.length);
                socket.receive(incomingPacket);
                String message = new String(incomingPacket.getData());
                InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                
                System.out.println("Received message from client: " + message);
                System.out.println("Client IP:"+IPAddress.getHostAddress());
                System.out.println("Client port:"+port);
                
                String reply = "Thank you for the message";
                byte[] data = reply.getBytes();
                
                DatagramPacket replyPacket =
                        new DatagramPacket(data, data.length, IPAddress, port);
                
                socket.send(replyPacket);
                Thread.sleep(2000);
                incomingPacket = new DatagramPacket(incomingData, 
                		incomingData.length);
                socket.receive(incomingPacket);
                message = new String(incomingPacket.getData());
                IPAddress = incomingPacket.getAddress();
                port = incomingPacket.getPort();
                
                System.out.println("Received message from client: " + message);
                System.out.println("Client IP:"+IPAddress.getHostAddress());
                System.out.println("Client port:"+port);
                
                reply = "Thank you for the message";
                data = reply.getBytes();
                
                replyPacket =
                        new DatagramPacket(data, data.length, IPAddress, port);
                
                socket.send(replyPacket);
                
                Thread.sleep(2000);
                incomingPacket = new DatagramPacket(incomingData, 
                		incomingData.length);
                socket.receive(incomingPacket);
                message = new String(incomingPacket.getData());
                IPAddress = incomingPacket.getAddress();
                port = incomingPacket.getPort();
                
                System.out.println("Received message from client: " + message);
                System.out.println("Client IP:"+IPAddress.getHostAddress());
                System.out.println("Client port:"+port);
                
                reply = "Thank you for the message";
                data = reply.getBytes();
                
                replyPacket =
                        new DatagramPacket(data, data.length, IPAddress, port);
                
                socket.send(replyPacket);
                socket.close();
            }
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        ServerGiven server = new ServerGiven();
        server.createAndListenSocket();
    }
}

