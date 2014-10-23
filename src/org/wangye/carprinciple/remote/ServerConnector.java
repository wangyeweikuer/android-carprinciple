package org.wangye.carprinciple.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.wangye.carprinciple.R;
import org.wangye.carprinciple.entity.Constants;

import android.content.Context;
import android.util.Log;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 13, 2014 8:47:26 PM
 */
public class ServerConnector {
	
	public static String getRemoteJson(final Context ctx, final String scene) throws Exception {
		Future<String> f = Executors.newFixedThreadPool(1).submit(new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				HttpURLConnection con = null;
				String url = getUrlByScene(ctx, scene);
				try {
					con = (HttpURLConnection) new URL(url).openConnection();
					con.setConnectTimeout(5000);
					con.setReadTimeout(5000);
					con.setRequestMethod("GET");
					con.setDoInput(true);
					con.connect();
					return convertInputstreamToJsonstr(con.getInputStream());
				} catch (Exception e) {
					Log.e(Constants.LOG_TAG, "get remote json failed by url[" + url + "]!", e);
					throw e;
				} finally {
					if (con != null) {
						con.disconnect();
					}
				}
			}
		});
		return f.get();
	}
	
	private static String convertInputstreamToJsonstr(InputStream is) throws Exception {
		StringBuilder json = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String s = null;
		while ((s = reader.readLine()) != null) {
			json.append(s);
		}
		reader.close();
		return json.toString();
	}
	
	public static String getUrlByScene(Context ctx, String scene) {
		String urlTemp = ctx.getResources().getString(R.string.server_url_temp);
		return String.format(urlTemp, scene);
	}
	
	public static String getUrlByScene(Context ctx, String scene, Map<String, String> params) {
		String urlTemp = ctx.getResources().getString(R.string.server_url_temp);
		String url = String.format(urlTemp, scene);
		if (params != null && params.size() > 0) {
			String b = "?";
			for (Entry<String, String> e : params.entrySet()) {
				url += b + e.getKey() + "=" + e.getValue();
				b = "&";
			}
		}
		return url;
	}
}
