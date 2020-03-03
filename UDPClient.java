package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient extends TimerTask
{
	DatagramSocket socket;
	public static InetAddress backup;

	
	
	//Constructor 
	public UDPClient()
	{
		
	}
	
	@Override
	public void run() 
	{
		try 
		{
			socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName("150.243.207.181");
			//InetAddress address = InetAddress.getByName("192.168.0.40");
			String message = "Hello! from Client Truman";
			byte[] sendMessage = message.getBytes();
			byte[] receiveMessage = new byte[1024];
			DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, address, 9876);
			socket.send(sendPacket);
			System.out.println("Message sent from client");
			DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
			socket.receive(receivePacket);
			String actualData = new String(receivePacket.getData());
			System.out.println("The ip address info received from server is: " + actualData);
			//socket.close();
			//if()
			//backup = InetAddress.getByName(actualData.substring(1));
			//InetAddress local = InetAddress.getByName("localhost");
			//System.out.println(backup.equals(local));
			//if(backup.equals(local))
			//{
			//	System.out.println("I need to take over as server");
			//}
		
			
			
			
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException 
	{
		Timer timer = new Timer();
		timer.schedule(new UDPClient(), 0, 2000);
	}

}