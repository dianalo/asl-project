package ch.eth.asl.dianalo.project;
import java.util.concurrent.LinkedBlockingQueue;

public class WriteQueue extends LinkedBlockingQueue<WriteRequest> {
	@Override
	public boolean add(WriteRequest r){
		boolean success = super.add(r);
		//start measurement of queue time of request
		//r.queuetime.start
		return success;
	}
	
	@Override
	public WriteRequest poll(){
		WriteRequest req = super.poll();
		//end measurement of queue time of request
		//req.queuetime.end();
		return req;
	}
}
