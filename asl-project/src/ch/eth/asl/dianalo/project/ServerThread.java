package ch.eth.asl.dianalo.project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ServerThread extends Thread{
	
	public ServerThread(QueueManager qm){
		try {
			this.queueManager = qm;
			socket = ServerSocketChannel.open().bind(new InetSocketAddress("localhost", 55554)
			);
			stopServer = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ServerSocketChannel socket;
	private QueueManager queueManager;
	private static boolean stopServer;
	
	public ServerSocketChannel getSocket(){
		return socket;
	}
	
	public void stopServer(){
		stopServer = true;
	}

	@Override
	public void run() {
		try {
			while(!stopServer)
			{
			
				//listen to incoming connections
				SocketChannel conn =  socket.accept();
				if(conn != null)
				{
					InetSocketAddress clientAddress = (InetSocketAddress) conn.getRemoteAddress();
					
					ByteBuffer buf = ByteBuffer.allocate(Helper.BUFFER_SIZE);
					buf.clear();
					int bytesRead;
					byte[] totalReceivedMsg = null; //gets concatenated to full message
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
					
					conn.close();
										
					String txtMsg = new String(totalReceivedMsg);
					//System.out.println(txtMsg);
					
					FileOutputStream fos = new FileOutputStream("/Users/loris/git/asl-project/resources/LogFile", true);
					FileChannel f = fos.getChannel();
					
					ByteBuffer fBuf = ByteBuffer.wrap(totalReceivedMsg);
					f.write(fBuf);
					
					f.close();
					fos.close();
					
					//PARSE MESSAGE
					Request req = Request.makeRequest(clientAddress, txtMsg);	
					System.out.println("Request type: " + req.getClass());
					
					//HASH key
					int hash = Math.abs(req.getKey().hashCode() % Helper.NO_OF_SERVERS);
					System.out.println("Hash: " + hash);
					
					//ENQUEUE
					//for WRITES&DELETES write queue
					//for READS read queue
					queueManager.enqueue(req, hash);
					
					SetRequest sr = (SetRequest) queueManager.getWriteRequest(hash);
					System.out.println("Key: " + sr.getKey() + " Value: " + sr.getValue());
					
				}
			}
			
			socket.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
