package ch.eth.asl.dianalo.project;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Helper {
	static byte[] concatenateBytes(byte[] a, byte[] b){
		int aLen = a.length;
		int bLen = b.length;
		
		byte[] c = new byte[aLen+bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		
		return c;
	}
	
	static String parseBytes(byte[] msg){
		String out = "";
		for(int i=0; i<msg.length; i++){
			out += Byte.toString(msg[i]);
		}
		return out;
	}
	
	static void sendByteArray(byte[] msg, ByteBuffer buf, SocketChannel socket) throws IOException{
		for(int j=0; j<=msg.length/Helper.BUFFER_SIZE; j++){
			for(int i=0; i<Helper.BUFFER_SIZE && i+j*Helper.BUFFER_SIZE < msg.length; i++){
				buf.put(msg[i + j*Helper.BUFFER_SIZE]);
			}
			buf.flip();
			socket.write(buf);
			buf.clear();
		}
		
		socket.close();
	}
	
	static final int BUFFER_SIZE = 48;
	static final int NO_OF_SERVERS = 1;
	static final String localAddress = "localhost";
	static final int[] localPorts = {55554, 55555, 55556, 55557, 55558};
	static final int memcachedPort = 11211;
}
