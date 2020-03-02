package proj1;
import java.io.Serializable;
import java.net.InetAddress;




public final class Client implements Serializable 

{

	private boolean active;
	private InetAddress IPAddress; 
	private int port;
	private static final long serialVersionUID = 1L;






    public Client(int clientNo, InetAddress IP, int port)
    {
    	this.IPAddress = IP;
        this.active = true;
        this.port = port;
    }
    
    public int getPort()
    {
    	return port;
    }

    public InetAddress getIP()
    {
    	return IPAddress;
    }
    
    public void fail()
    {
    	this.active = false;
    }
    
    public void active()
    {
    	this.active = true;
    }

    public boolean status()
    {
    	return active;
    }
    



}