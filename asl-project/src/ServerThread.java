import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ServerThread extends Thread{
	
	public ServerThread(){
		try {
			socket = ServerSocketChannel.open().bind(new InetSocketAddress("localhost", 55554)
			);
			stopServer = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ServerSocketChannel socket;
	private static boolean stopServer;
	private final static int BUFFER_SIZE = 48;
	private final static int NO_OF_SERVERS = 3;
	
	public ServerSocketChannel getSocket(){
		return socket;
	}
	
	public void stopServer(){
		stopServer = true;
	}

	@Override
	public void run() {
		while(!stopServer)
		{
			try {
				//listen to incoming connections
				SocketChannel conn =  socket.accept();
				if(conn != null)
				{
					ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
					buf.clear();
					int bytesRead;
					String message = "";
					while ((bytesRead = conn.read(buf)) >= 0)
					{
						buf.flip();
						message += new String(buf.array()).substring(0, bytesRead);			
						buf.clear();					
					}
					
					//PARSE MESSAGE
					//Request req = Request.parse(message);	
					System.out.println(message);
					
					//HASH key
					int hash = Math.abs(message.hashCode() % NO_OF_SERVERS);
					System.out.println("Hash: " + hash);
					
					//ENQUEUE
					//for WRITES&DELETES normal queue
					//for READS concurrent queue

					
					conn.close();
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
