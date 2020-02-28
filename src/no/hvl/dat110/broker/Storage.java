package no.hvl.dat110.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from user to set of topics subscribed to by user
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	protected ConcurrentHashMap<String, ClientSession> clients;


	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {
		return subscriptions.keySet();
	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	public ClientSession getSession(String user) {
		ClientSession session = clients.get(user);
		return session;
	}

	public Set<String> getSubscribers(String topic) {
		return (subscriptions.get(topic));
	}

	public void addClientSession(String user, Connection connection) {
		// TODO: COMPLETE: add corresponding client session to the storage
		clients.put(user, new ClientSession(user, connection));
	}

	public void removeClientSession(String user) {
		// TODO: COMPLETE: remove client session for user from the storage
		clients.remove(user);

		
	}

	public void createTopic(String topic) {
		// TODO: COMPLETE: create topic in the storage

		if(!subscriptions.containsKey(topic)){
			Set<String> subscribers = ConcurrentHashMap.newKeySet();
			subscriptions.put(topic, new HashSet<String>());
		}
	}

	public void deleteTopic(String topic) {
		// TODO: COMPLETE: delete topic from the storage
		subscriptions.remove(topic);

	}

	public void addSubscriber(String user, String topic) {
		// TODO: COMPLETE: add the user as subscriber to the topic
		Set<String> set = subscriptions.get(topic);
		set.add(user);
		subscriptions.put(topic, set);

	}

	public void removeSubscriber(String user, String topic) {
		// TODO: COMPLETE remove the user as subscriber to the topic
		Set<String> set = subscriptions.get(topic);
		set.remove(user);
		subscriptions.put(topic, set);

	}
}
