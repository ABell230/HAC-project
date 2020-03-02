package server;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.io.ObjectOutputStream;

import java.io.Serializable;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.net.SocketTimeoutException;

import java.util.ArrayList;





public class UDPServer implements Serializable 

{

    public DatagramSocket socket = null;

    
	private static final long serialVersionUID = 1L;
    public static ArrayList <Packet> packets = new ArrayList<Packet>();
    //public static ArrayList <InetAddress> IPs = new ArrayList <InetAddress>();
    //public static ArrayList <Integer> ports = new ArrayList <Integer>();    
    public static ArrayList <Client> network = new ArrayList <Client>();

    
    public UDPServer() 

    {



    }

    public void createAndListenSocket() throws IOException, InterruptedException 

    {
        try 
        {
            socket = new DatagramSocket(9876);
            byte[] incomingData = new byte[1024];
            System.out.println(packets.size());

            while(true)

            {      
            	long begin = System.currentTimeMillis();
            	long end = begin + 10*1000; 
            	packets.removeAll(packets);
            	//IPs.removeAll(IPs);
            	//ports.removeAll(ports);
            	network.removeAll(network);
            	int count = 0;
            	while(begin < end)
            	{
            		//receive packet
            		DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            		socket.receive(incomingPacket);
            		//create packet
            		Packet newPacket = new Packet(incomingData, incomingData.length); 
            		InetAddress IPAddress = incomingPacket.getAddress();
            		int port = incomingPacket.getPort();
            		//update lists
            		packets.add(newPacket);
            		//IPs.add(IPAddress);
            		//ports.add(port);
            		Client newClient = new Client(count, IPAddress, port);
            		network.add(newClient);
            		System.out.println(packets.size());
            		Thread.sleep(3000);
                	begin = System.currentTimeMillis();
                	System.out.println(begin < end);
            	}
            	//create output stream
                ByteArrayOutputStream out = new ByteArrayOutputStream();
           		ObjectOutputStream os = new ObjectOutputStream(out);
           		//write to the stream
           		os.writeObject(packets);           		          	
           		System.out.println("Checkpoint");
           		//respond to clients
           		for (int j = 0; j < network.size(); j++) 
           		{
           			if (network.get(j).getIP().isReachable(5000))
           			{
               			DatagramPacket replyPacket = new DatagramPacket(incomingData, incomingData.length, network.get(j).getIP(), network.get(j).getPort());
               			socket.send(replyPacket);
           			}
           			else
           			{
           	            byte[] notIncomingData = new byte[1024];
           	            String failData = "Client " + j + 1 +" is down.";
           	            network.get(j).fail();
           	            notIncomingData = failData.getBytes();
           				DatagramPacket replyPacket = new DatagramPacket(notIncomingData, notIncomingData.length, network.get(j).getIP(), network.get(j).getPort());
						socket.send(replyPacket);
           			}
           		} 
            }
        }
        catch (SocketTimeoutException e)
        {
        	e.printStackTrace();
        }
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        }
    }
    
 static void main(String[] args) throws IOException, InterruptedException 
    {
        UDPServer server = new UDPServer();
        server.createAndListenSocket();
    }

}
