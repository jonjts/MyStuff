package br.com.tep.mystuff.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class WebClient {

	private final String url;

	public WebClient(String url) {
		this.url = url;
	}

	public String post(String json) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(json));
		 
		HttpResponse response = httpClient.execute(post);
		
		for (Header h : response.getAllHeaders()) {
			Log.i("Console", h.getValue());
		}
		
		
		return EntityUtils.toString(response.getEntity());
	}
}