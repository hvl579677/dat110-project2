package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.TODO;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");
		
		// TODO - START - COMPLETE
				
		// create a client object and use it to
		Client client = new Client("sensors", Common.BROKERHOST, Common.BROKERPORT);


		// - connect to the broker
		client.connect();
		// - create the temperature topic on the broker
		client.createTopic(Common.TEMPTOPIC);
		// - subscribe to the topic
		client.subscribe(Common.TEMPTOPIC);
		// - receive messages on the topic
		for(int i = 0; i < COUNT; i++){
			PublishMsg msg = (PublishMsg) client.receive();
			System.out.println("Temperature is: " + msg.getMessage());

			try{
				Thread.sleep(5000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}

		// - unsubscribe from the topic
		client.unsubscribe(Common.TEMPTOPIC);
		// - disconnect from the broker
		client.disconnect();
		
		// TODO - END - COMPLETE
		
		System.out.println("Display stopping ... ");
	}
}
