package org.wangye.carprinciple;

import java.util.HashMap;
import java.util.Map;

import org.wangye.carprinciple.remote.ServerConnector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 9, 2014 9:32:21 AM
 */
@SuppressLint("SetJavaScriptEnabled")
public class PrincipleDetailActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.principle_detail);
		//
		Bundle bb = getIntent().getExtras();
		WebView wv = (WebView) findViewById(R.id.principle_detail_web);
		setTitle(getTitle() + "(" + bb.getString("name") + ")");
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", bb.getInt("id") + "");
		String url = ServerConnector.getUrlByScene(getApplication(), "principle/findContentById", params);
		wv.loadUrl(url);
	}
}
