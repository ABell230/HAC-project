package proj1;

import java.io.IOException;
import java.net.*;

public class clientgiven 
{
    DatagramSocket Socket;

    public clientgiven() 
    {

    }

    public void createAndListenSocket() 
    {
        try 
        {
            Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] incomingData = new byte[1024];
            String sentence = "Viehmann";
            byte[] data = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            Socket.send(sendPacket);
            System.out.println("Message sent from client");
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            Socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server:" + response);
            
            String message = new String(incomingPacket.getData());
            IPAddress = incomingPacket.getAddress();
            int port = incomingPacket.getPort();
            
            System.out.println("Received message from client: " + message);
            System.out.println("Client IP:"+IPAddress.getHostAddress());
            System.out.println("Client port:"+port);
            
            String reply = "Test to server";
            data = reply.getBytes();
            
            DatagramPacket replyPacket =
                    new DatagramPacket(data, data.length, IPAddress, port);
            
            Socket.send(replyPacket);
            
            incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            Socket.receive(incomingPacket);
            response = new String(incomingPacket.getData());
            System.out.println("Response from server:" + response);
            
            message = new String(incomingPacket.getData());
            IPAddress = incomingPacket.getAddress();
            port = incomingPacket.getPort();
            
            System.out.println("Received message from client: " + message);
            System.out.println("Client IP:"+IPAddress.getHostAddress());
            System.out.println("Client port:"+port);
            
            reply = "Test to server2";
            data = reply.getBytes();
            
            replyPacket =
                    new DatagramPacket(data, data.length, IPAddress, port);
            
            Socket.send(replyPacket);
            
            Socket.close();
        }
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        clientgiven client = new clientgiven();
        client.createAndListenSocket();
    }
}

