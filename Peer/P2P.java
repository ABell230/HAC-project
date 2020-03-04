package Peer;
import java.io.IOException;

import java.net.*;

import java.util.ArrayList;
import java.util.Calendar;


import java.net.InetAddress;




public class P2P 

{

  public static ArrayList<Client> network = new ArrayList<>();



  public static void main(String[] args) throws Exception 

  {
	InetAddress ad1 = InetAddress.getByName("150.243.216.26");
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.SECOND, 30) ;

	network.add(new Client(ad1, cal)); //Client 1
	
	InetAddress ad2 = InetAddress.getByName("150.243.16.52");
	network.add(new Client(ad2, cal)); //Client 2

	InetAddress ad3 = InetAddress.getByName("150.243.16.154");
	network.add(new Client(ad3, cal)); //Client 3

	//InetAddress ad4 = InetAddress.getByName("150.243.16.154");
	//network.add(new Client(ad4, cal)); //Client 4
	
    startServer();

    startSender();

  }



  public static void startSender() throws UnknownHostException

  {

    (new Thread() 

    {

        @Override

        public void run() 

        {

            byte data[] = "Client is up".getBytes();

            DatagramSocket socket = null;

            try 

            {

                socket = new DatagramSocket();

                socket.setBroadcast(true);

            } 

            catch (SocketException ex) 

            {

                ex.printStackTrace();

            }

            while (true)

            {

	        	for (int j = 0; j < network.size(); j++) 

	        	{

	        		InetAddress ipAddress;

					try 

					{

						ipAddress = network.get(j).getIP();


						DatagramPacket sendPacket = new DatagramPacket(data,data.length,ipAddress,9876);
			            socket.send(sendPacket);
			            
			            

						

							//for (int i = 0; i < network.size(); i++)

							//{

								//String downString = "Client " + network.get(j).getIp() + " is down";

					            //byte dataFail[] = downString.getBytes();

								//DatagramPacket sendPacket = new DatagramPacket(dataFail, dataFail.length, InetAddress.getByName(network.get(i)), 9876);

								//socket.send(sendPacket);

							//}

						

		                

		                Thread.sleep(5000);

					} 

					catch (UnknownHostException e) 

					{

						System.out.println("Client " + j + 1 + " is down.");

					}

					catch (IOException ex) 

	                {

						ex.printStackTrace();

	                }

					catch (InterruptedException e)

					{

						e.printStackTrace();

					}

	        	}

            }

        }

        }).start();

    }



  public static void startServer() 

  {

    (new Thread() 

    {

        @Override

        public void run() 

        {

                DatagramSocket serverSocket = null;

                try 

                {

                	serverSocket = new DatagramSocket(9876);

                } 

                catch (SocketException ex) 

                {

                    ex.printStackTrace();

                }

                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

                while (true) 

                {

                	try 

                	{

                		serverSocket.receive(receivePacket);

	                    InetAddress address = receivePacket.getAddress();


	                    


						

	                    System.out.println("msg from: " + receivePacket.getAddress());
	    				Calendar cal = Calendar.getInstance();
	    				cal.add(Calendar.SECOND, 30) ;


	                    
	                  //Loops through the arraylist and removes the client that has not responded for more than 30 seconds.

	    				for (int i = 0; i<network.size(); i++)

	    				{
							if(network.get(i).getIP().equals(address) == true)

							{

								network.set(i, new Client(address, cal));

							}

	    					Calendar right_now = Calendar.getInstance();
	    					

	    					int j = right_now.compareTo(network.get(i).getCal());

	    					if (j == 1)

	    					{

	    						System.out.println("Server did not hear back from the client: " + network.get(i).getIP().getHostAddress());
	    						System.out.println("Client: " + network.get(i).getIP().getHostAddress() + " is down");

	    					}

	    					

	    				}//for-end
	    				


                	} 

                	catch (IOException ex) 

                	{

	                    ex.printStackTrace();

	                    //parent.quit();

                	}

                }

            }

    }).start();

 }

}