package ch.eth.asl.dianalo.project;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int N,R,T;
		if(args.length == 3){
		//arguments
		N = Integer.parseInt(args[0]);
		R = Integer.parseInt(args[1]);
		T = Integer.parseInt(args[2]);
		}
		
		QueueManager qm = new QueueManager(Helper.NO_OF_SERVERS);
		
		ServerThread t = new ServerThread(qm);
		t.start();
		
		try {
			WriteThread w = new WriteThread(qm.getWriteQueue(0), new InetSocketAddress(Helper.localAddress, Helper.memcachedPort));
			w.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*		try {
			Thread.sleep(2000);
		
		
		String msg = "add newKey 0 60 500";
		byte[] value = new byte[500];
		new Random().nextBytes(value);
		byte[] requestBytes = Helper.concatenateBytes(msg.getBytes(), value);
		
		
		
		SocketChannel channel;
			channel = SocketChannel.open(new InetSocketAddress("localhost", 55554));
			ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
			buf.clear();
			//buf.put(newData.getBytes());

			//buf.flip();
			
			//can maybe be done easier
			for(int j=0; j<=requestBytes.length/BUFFER_SIZE; j++){
				for(int i=0; i<BUFFER_SIZE && i+j*BUFFER_SIZE < requestBytes.length; i++){
					buf.put(requestBytes[i + j*BUFFER_SIZE]);
				}
				buf.flip();
				channel.write(buf);
				buf.clear();
			}
			//IMPORTANT!! otherwise read on server site never ends
			channel.close();
			

		
			Thread.sleep(3000);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.stopServer();
		*/
		
	}

}
