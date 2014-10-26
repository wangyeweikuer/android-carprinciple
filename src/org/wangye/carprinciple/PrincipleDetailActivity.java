package org.wangye.carprinciple;

import org.wangye.carprinciple.entity.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        String url = getString(R.string.server_host_and_port) + bb.getString("url");
        Log.d(Constants.LOG_TAG, url);
        wv.loadUrl(url);
    }
}
