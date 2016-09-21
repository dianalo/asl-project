import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ServerThread implements Runnable{
	
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
	
	public ServerSocketChannel socket;
	public static boolean stopServer;
	private final static int BUFFER_SIZE = 48;

	@Override
	public void run() {
		while(!stopServer)
		{
			try {
				SocketChannel conn =  socket.accept();
				if(conn != null)
				{
					ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
					buf.clear();
					int bytesRead = conn.read(buf);
					String message = "";
					while (bytesRead > 0)
					{
						buf.flip();
						message += new String(buf.array()).substring(0, bytesRead);
						buf.clear();
						//if(!conn.isBlocking()){
						System.out.println(conn.isOpen());
						System.out.println(buf.toString());
							bytesRead = conn.read(buf);
//						}
//						else{
//							break;
//						}
						
						//System.out.println(bytesRead + "\n");
						//buf.flip();						
					}
					
					
					System.out.println(message);
					conn.close();
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
