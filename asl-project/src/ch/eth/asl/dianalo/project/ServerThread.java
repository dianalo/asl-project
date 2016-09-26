package ch.eth.asl.dianalo.project;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.stream.FileImageInputStream;

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
					byte[] totalReceivedMsg = null; //gets concatenated to full message
					String message = "";
					while ((bytesRead = conn.read(buf)) >= 0) // maybe cover case for 0 bytes are read!!
					{
						buf.flip();
						byte[] roundReceivedMsg = new byte[bytesRead];
						//write from buffer to array
						buf.get(roundReceivedMsg, 0, bytesRead);
						if(totalReceivedMsg == null){
							totalReceivedMsg = new byte[bytesRead]; 
							System.arraycopy(roundReceivedMsg, 0, totalReceivedMsg, 0, bytesRead);
						}
						else{
							totalReceivedMsg = Helper.concatenateBytes(totalReceivedMsg, roundReceivedMsg);
						}
						buf.clear();					
					}
										
					String txtMsg = new String(totalReceivedMsg);
					//System.out.println(txtMsg);
					
					FileOutputStream fos = new FileOutputStream("/Users/loris/git/asl-project/resources/LogFile", true);
					FileChannel f = fos.getChannel();
					
					ByteBuffer fBuf = ByteBuffer.wrap(totalReceivedMsg);
					f.write(fBuf);
					
					f.close();
					fos.close();

					
					//PARSE MESSAGE
					Request req = Request.parse(txtMsg);	
					System.out.println("Request type: " + req.getClass());
					
					//HASH key
					int hash = Math.abs(req.getKey().hashCode() % NO_OF_SERVERS);
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
