package com.sample.nevis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class CreateTask extends AsyncTask<String, Void, Void>{

	@Override
	protected Void doInBackground(String... arg0) {
		
		try {
			HttpClient client;
			client = new DefaultHttpClient();
			
			HttpPost post;
			post = new HttpPost("http://www.exchangewriters.com/taskmanagement/push_sample.php");	
			
			List<NameValuePair> params;
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("detail", arg0[0]));
			
			post.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse response;		
			response = client.execute(post);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	

}
