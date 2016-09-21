import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Main {

	public static void main(String[] args) {
		Thread t = new Thread(new ServerThread());
		t.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String newData = "blablablablablablablablablablablablablablablablabla";
		
		SocketChannel channel;
		try {
			channel = SocketChannel.open(new InetSocketAddress("localhost", 55554));
			ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
			buf.clear();
			byte[] newDataBytes = newData.getBytes();
			//buf.put(newData.getBytes());

			//buf.flip();
			
			for(int j=0; j<=newDataBytes.length/BUFFER_SIZE; j++){
				for(int i=0; i<BUFFER_SIZE && i+j*BUFFER_SIZE < newDataBytes.length; i++){
					buf.put(newDataBytes[i + j*BUFFER_SIZE]);
				}
				buf.flip();
				channel.write(buf);
				buf.clear();
			}
			
//			while(buf.hasRemaining()) {
//			    channel.write(buf);
//			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServerThread.stopServer = true;
		
	}
	
	private static final int BUFFER_SIZE = 48;

}
