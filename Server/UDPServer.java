
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
    public static ArrayList <Client> network = new ArrayList <Client>();

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

			//ArrayList<InetAddress> ipArray = new ArrayList<InetAddress>();
			network.removeAll(network);

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
                
                boolean oldClient = false;
                //ipArray.add(address);
                for(int i = 0; i < network.size(); i++)
                {
                	//prints client list
                	System.out.println("Client " + (i + 1) + " has a IP address of " + network.get(i).getIP());
                	//checks client list
                	if(network.get(i).getIP() == address)
                	{
                		oldClient = true;
                		network.get(i).active();
                	}
                	//checks if client is down and updates client status accodringly 
                	if (!network.get(i).getIP().isReachable(10000))
                    {   
                        network.get(i).fail();
                    }
                }
                //adds a new client to the list if it is not on the list
                if(oldClient = false)
                {
                	Client newClient = new Client(address, port);
                	network.add(newClient);
                }
                	
                //System.out.println("First element of database " + network.get(0).getIP());

                

                String response = "Thank you for the message.";

                byte[] sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

                socket.send(sendPacket);

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