package ch.eth.asl.dianalo.project;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Main {

	public static void main(String[] args) {
		int N,R,T;
		if(args.length == 3){
		//arguments
		N = Integer.parseInt(args[0]);
		R = Integer.parseInt(args[1]);
		T = Integer.parseInt(args[2]);
		}
		
		ServerThread t = new ServerThread();
		t.start();
		
		try {
			Thread.sleep(2000);
		
		
	
		
		String newData = "blabla";
		
		SocketChannel channel;
			channel = SocketChannel.open(new InetSocketAddress("localhost", 55554));
			ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
			buf.clear();
			byte[] newDataBytes = newData.getBytes();
			//buf.put(newData.getBytes());

			//buf.flip();
			
			//can maybe be done easier
			for(int j=0; j<=newDataBytes.length/BUFFER_SIZE; j++){
				for(int i=0; i<BUFFER_SIZE && i+j*BUFFER_SIZE < newDataBytes.length; i++){
					buf.put(newDataBytes[i + j*BUFFER_SIZE]);
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
		
		
	}
	
	private static final int BUFFER_SIZE = 48;

}
