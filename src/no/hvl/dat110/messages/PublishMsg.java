package no.hvl.dat110.messages;

public class PublishMsg extends Message {
	
	// message sent from client to create publish a message on a topic 

	// TODO:
	// Implement object variables - a topic and a message is required

	// Constructor, get/set-methods, and toString method
	// as described in the project text

    private String topic;
    private String message;
    private String user;

    public PublishMsg(String user, String topic, String message){
        this.topic = message;
        this.message = message;
        this.user = user;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ("PublishMsg [user = " + user + ", topic = " + topic + ", message = " + message + "]");
    }
}
