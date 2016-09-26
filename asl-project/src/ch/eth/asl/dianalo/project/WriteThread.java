package ch.eth.asl.dianalo.project;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class WriteThread extends QueueThread {
	protected WriteQueue queue;
	protected boolean stopThread;
	protected ServerSocketChannel responseSocket;
	
	WriteThread(WriteQueue writeQueue, InetSocketAddress remoteServerAddress) throws IOException{
		this.queue = writeQueue;
		this.remoteServerAddress = remoteServerAddress;
		stopThread = false;
		responseSocket = ServerSocketChannel.open().bind(new InetSocketAddress(Helper.localAddress, Helper.localPorts[1]));
		responseSocket.configureBlocking(true);
	}
	
	public void stopThread(){
		stopThread = true;
	}
	
	@Override
	public void run(){
		while(!stopThread){
			WriteRequest rq = null;
			while (rq == null){ //spinning! bad!!
				rq = queue.poll();
			}
					
			//convert request to String
			String txtRq = "";
			//send to memcached server
			try {
				SocketChannel socket = SocketChannel.open(remoteServerAddress);
				ByteBuffer buf = ByteBuffer.allocate(Helper.BUFFER_SIZE);
				byte[] requestBytes = txtRq.getBytes();
				
				for(int j=0; j<=requestBytes.length/Helper.BUFFER_SIZE; j++){
					for(int i=0; i<Helper.BUFFER_SIZE && i+j*Helper.BUFFER_SIZE < requestBytes.length; i++){
						buf.put(requestBytes[i + j*Helper.BUFFER_SIZE]);
					}
					buf.flip();
					socket.write(buf);
					buf.clear();
				}
				
				socket.close();
				
				//wait for answer
				SocketChannel conn = responseSocket.accept();
				int bytesRead;
				byte[] totalReceivedMsg = null;
				while((bytesRead = conn.read(buf)) >= 0){
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
				
				//for logging
				String txtMsg = new String(totalReceivedMsg);
				System.out.println(txtMsg);
				
				//send response to client
				Helper.sendByteArray(totalReceivedMsg, buf, SocketChannel.open(rq.getClientAddress()));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
