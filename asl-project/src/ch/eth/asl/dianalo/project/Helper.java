package ch.eth.asl.dianalo.project;

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
}
