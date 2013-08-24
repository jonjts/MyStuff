package br.com.tep.mystuff.util;

import br.com.tep.mystuff.helper.ContextHelper;


public class ApplicationContext {

	private static ApplicationContext instance;
	private ContextHelper helper;

	private ApplicationContext() {
	}

	public static synchronized ApplicationContext getContext() {
		if (instance == null) {
			instance = new ApplicationContext();
		}

		return instance;
	}

	public void setHelper(ContextHelper helper) {
		this.helper = helper;
	}

	public ContextHelper getHelper() {
		return helper;
	}
}
