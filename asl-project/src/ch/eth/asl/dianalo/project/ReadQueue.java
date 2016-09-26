package ch.eth.asl.dianalo.project;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReadQueue extends ConcurrentLinkedQueue<ReadRequest> {
	
	public ReadQueue(){
		super();
	}
	
	@Override
	public boolean add(ReadRequest r){
		boolean success = super.add(r);
		//start measurement of queue time of request
		//r.queuetime.start
		return success;
	}
	
	@Override
	public ReadRequest poll(){
		ReadRequest req = super.poll();
		//end measurement of queue time of request
		//req.queuetime.end();
		return req;
	}
}
