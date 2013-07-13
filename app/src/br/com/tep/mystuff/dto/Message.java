package br.com.tep.mystuff.dto;

import java.io.Serializable;

/**
 *
 * @author felipe
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -79091388155948953L;
	private String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
