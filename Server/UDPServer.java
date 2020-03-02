
package Server;



import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.util.ArrayList;



public class UDPServer 

{

	DatagramSocket socket = null;
   // public static ArrayList <Client> network = new ArrayList <Client>();

	//Constructor

	public UDPServer()

	{

		

	}

	

	public void listenToSocket() 

	{

		try 

		{

			socket = new DatagramSocket(9876);

			byte [] incomingData = new byte[1024];

			ArrayList <Client> network = new ArrayList <Client>();
			//network.removeAll(network);

			while(true) 

			{

				// Declaring the socket to receive data and store the message in message.

				DatagramPacket receivePacket = new DatagramPacket(incomingData, incomingData.length);

				System.out.println("--------Server is listening ----------\n");

				socket.receive(receivePacket);

				String message = new String(receivePacket.getData());

				InetAddress address = receivePacket.getAddress();

				int port = receivePacket.getPort();

				System.out.println("Received message from client: " + message);

                System.out.println("Client IP:"+ address.getHostAddress());

                System.out.println("Client port: "+ port);
                
                Client newClient = new Client(address, port);
                
                if(network.size() == 0) {
                	network.add(newClient);
                }
                
                boolean oldClient;
                for(int i = 0; i < network.size(); i++)
                {
                	//prints client list
                	System.out.println("Client " + (i + 1) + " has a IP address of " + network.get(i).getIP());
                	//checks client list
                	if(address.equals(network.get(i).getIP()))
                	{
                		oldClient = true;
                	} else {
                		oldClient = false;
                	}
                	//checks if client is down and updates client status accodringly 
                	if (!network.get(i).getIP().isReachable(1000))
                    {   
                        network.get(i).fail();
                    } else {
                    	network.get(i).active();
                    }
                }
                //adds a new client to the list if it is not on the list
                if(oldClient = false)
                {
                	network.add(newClient);
                	System.out.println("new client");
                }
                
               
                for(int i = 0; i < network.size(); i++ )
                {
                	if(network.get(i).status() == true)
                	{
                		String response = (network.get(i).getIP()).toString();
                		byte[] sendData = response.getBytes();

                		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

                		socket.send(sendPacket);
                		break;
                	}
                }
 
                String response = "Thank you for the message.";

                Thread.sleep(2000);

                //socket.close();

				

				

			}

		} catch (SocketException e) 

		{	

			e.printStackTrace();

		} 

		catch (IOException e) 

		{

			e.printStackTrace();

		} 

		catch (InterruptedException e) 

		{

			e.printStackTrace();

		}

	}

	public static void main(String[] args) 

	{

		UDPServer theServer = new UDPServer();

		theServer.listenToSocket();



	}



}