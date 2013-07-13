package br.com.tep.mystuff.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
	private static final long serialVersionUID = 6948328793823932767L;

	private ResponseStatus status;
	private List<Message> messages;
	private Object data;

	public Response(ResponseStatus status) {
		messages = new ArrayList<Message>();
		this.status = status;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public Object getData() {
		return data;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
