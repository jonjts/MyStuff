package br.com.tep.mystuff.dto;



public enum SimNaoEnum {
	SIM(1), NAO(0);

	private long id;

	private SimNaoEnum(long id) {
		this.id = id;
	}

	public static SimNaoEnum fromValue(long id) {
		for (SimNaoEnum item : values()) {
			if (item.id == id) {
				return item;
			}
		}

		return null;
	}

	public String getId() {
		return String.valueOf(id);
	}
}
