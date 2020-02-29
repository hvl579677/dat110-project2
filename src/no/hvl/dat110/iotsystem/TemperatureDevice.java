package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();
		//Client client = new Client("temperatursensor", Common.BROKER);

		//client.connect();
		//client.publish("temperature", Integer.toString(sn.read());
		// TODO - START - COMPLETE

		// create a client object and use it to
		Client client = new Client("sensor", Common.BROKERHOST, Common.BROKERPORT);

		// - connect to the broker
		client.connect();
		// - publish the temperature(s)
		for(int i = 0; i < COUNT; i++){
			client.publish(Common.TEMPTOPIC, Integer.toString(sn.read()));
			try{
				Thread.sleep(5000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		// - disconnect from the broker
		client.disconnect();

		// TODO - END - COMPLETE

		System.out.println("Temperature device stopping ... ");

		throw new UnsupportedOperationException(TODO.method());

	}
}
