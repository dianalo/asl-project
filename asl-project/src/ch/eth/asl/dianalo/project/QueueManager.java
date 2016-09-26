package ch.eth.asl.dianalo.project;

public class QueueManager {
	private ReadQueue[] readQueues;
	private WriteQueue[] writeQueues;
	
	public QueueManager(int nOfServers){
		readQueues = new ReadQueue[nOfServers];
		writeQueues = new WriteQueue[nOfServers];
		for(int i=0; i<nOfServers; i++){
			readQueues[i] = new ReadQueue();
			writeQueues[i] = new WriteQueue();
		}
	}
	
	public boolean enqueue(Request rq, int hash) throws Exception{
		if(rq instanceof ReadRequest){
			return readQueues[hash].add((ReadRequest) rq);
		}
		else if(rq instanceof WriteRequest){
			return writeQueues[hash].add((WriteRequest) rq);
		}
		else{
			throw new Exception("Unknown request type.");
		}
	}
	
	public boolean enqueue(ReadRequest rq, int hash){
		return readQueues[hash].add(rq);
	}
	
	public boolean enqueue(WriteRequest rq, int hash){
		return writeQueues[hash].add(rq);
	}
	
	public ReadRequest getReadRequest(int serverNumber){
		return readQueues[serverNumber].poll();
	}
	
	public WriteRequest getWriteRequest(int serverNumber){
		return writeQueues[serverNumber].poll();
	}
	
	public ReadQueue getReadQueue(int serverNumber){
		return readQueues[serverNumber];
	}
	
	public WriteQueue getWriteQueue(int serverNumber){
		return writeQueues[serverNumber];
	}
}
