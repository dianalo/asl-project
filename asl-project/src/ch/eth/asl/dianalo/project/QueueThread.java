package ch.eth.asl.dianalo.project;

import java.net.InetSocketAddress;

public abstract class QueueThread extends Thread {
	protected InetSocketAddress remoteServerAddress;
}
