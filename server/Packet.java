package proj1;

import java.io.Serializable;

import java.net.InetAddress;



public final class Packet implements Serializable 

{

    /**

	 * 

	 */

	private static final long serialVersionUID = 1L;

	private byte[] stringValue;

//    private boolean flagValue;

    private int length;

	private InetAddress IPAddress; 

	private int port; 







    

    public Packet(byte[] stringValue, int length)

    {

    	this.stringValue = stringValue; 

    	this.length = length; 



    }

    public InetAddress getIP()
    {
    	return IPAddress;
    }
    
    public int getPort()
    {
    	return port;
    }

    public byte[] getString()
    {
        return stringValue;
    }


    public int getLength()
    {
    	return length; 
    }

    

    

    @Override

    public String toString() 

    {

    	String formatted = new String(stringValue); 

        String value = formatted + "\nLength : " + length;

        return value;

    }

}

