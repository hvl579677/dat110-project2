package no.hvl.dat110.broker;

import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.common.Stopable;
import no.hvl.dat110.messages.*;
import no.hvl.dat110.messagetransport.Connection;

public class Dispatcher extends Stopable {

	private Storage storage;

	public Dispatcher(Storage storage) {
		super("Dispatcher");
		this.storage = storage;

	}

	@Override
	public void doProcess() {

		Collection<ClientSession> clients = storage.getSessions();

		Logger.lg(".");
		for (ClientSession client : clients) {

			Message msg = null;

			if (client.hasData()) {
				msg = client.receive();
			}

			// a message was received
			if (msg != null) {
				dispatch(client, msg);
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void dispatch(ClientSession client, Message msg) {

		MessageType type = msg.getType();

		// invoke the appropriate handler method
		switch (type) {

		case DISCONNECT:
			onDisconnect((DisconnectMsg) msg);
			break;

		case CREATETOPIC:
			onCreateTopic((CreateTopicMsg) msg);
			break;

		case DELETETOPIC:
			onDeleteTopic((DeleteTopicMsg) msg);
			break;

		case SUBSCRIBE:
			onSubscribe((SubscribeMsg) msg);
			break;

		case UNSUBSCRIBE:
			onUnsubscribe((UnsubscribeMsg) msg);
			break;

		case PUBLISH:
			onPublish((PublishMsg) msg);
			break;

		default:
			Logger.log("broker dispatch - unhandled message type");
			break;

		}
	}

	// called from Broker after having established the underlying connection
	public void onConnect(ConnectMsg msg, Connection connection) {

		String user = msg.getUser();

		Logger.log("onConnect:" + msg.toString());

		storage.addClientSession(user, connection);

		if(storage.getSession(user) == null){
			storage.addClientSession(user, connection);
		} else {
			storage.reconnectUser(user, connection);
			ArrayList<Message> messages = storage.getMessageBuffers(user);
			for(Message message : messages){
				storage.getSession(user).send(message);
			}
			storage.emptyMessageBuffers(user);
		}

	}

	// called by dispatch upon receiving a disconnect message
	public void onDisconnect(DisconnectMsg msg) {

		String user = msg.getUser();

		Logger.log("onDisconnect:" + msg.toString());

		storage.removeClientSession(user);

	}

	public void onCreateTopic(CreateTopicMsg msg) {
		Logger.log("onCreateTopic:" + msg.toString());
		String topic = msg.getTopic();
		storage.createTopic(topic);

	}

	public void onDeleteTopic(DeleteTopicMsg msg) {

		Logger.log("onDeleteTopic:" + msg.toString());

		// TODO: COMPLETE: delete the topic from the broker storage
		// the topic is contained in the delete topic message

		String topic = msg.getTopic();

		storage.deleteTopic(topic);
	}

	public void onSubscribe(SubscribeMsg msg) {

		Logger.log("onSubscribe:" + msg.toString());

		// TODO: COMPLETE: subscribe user to the topic
		// user and topic is contained in the subscribe message


		String topic = msg.getTopic();
		String user = msg.getUser();

		storage.addSubscriber(user, topic);

	}

	public void onUnsubscribe(UnsubscribeMsg msg) {
		Logger.log("onUnsubscribe:" + msg.toString());

		// TODO: COMPLETE: unsubscribe user to the topic
		// user and topic is contained in the unsubscribe message

		String topic = msg.getTopic();
		String user = msg.getUser();

		storage.removeSubscriber(user, topic);

	}

	public void onPublish(PublishMsg msg) {

		Logger.log("onPublish:" + msg.toString());

		// TODO: COMPLETE: publish the message to clients subscribed to the topic
		// topic and message is contained in the subscribe message
		// messages must be sent used the corresponding client session objects

		String topic = msg.getTopic();

		Set<String> subscribers = storage.getSubscribers(topic);

		for (String user : subscribers){
			if(storage.isConnected(user)){
				storage.getSession(user).send(msg);
			} else {
				storage.getSession(user).send(msg);
			}
		}
	}
}
