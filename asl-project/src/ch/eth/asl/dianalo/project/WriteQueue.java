package ch.eth.asl.dianalo.project;
import java.util.concurrent.LinkedBlockingQueue;

public class WriteQueue extends LinkedBlockingQueue<Request> {
	@Override
	public boolean add(Request r){
		boolean success = super.add(r);
		//start measurement of queue time of request
		//r.queuetime.start
		return success;
	}
	
	@Override
	public Request poll(){
		Request req = super.poll();
		//end measurement of queue time of request
		//req.queuetime.end();
		return req;
	}
}
