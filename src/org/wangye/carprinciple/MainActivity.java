package org.wangye.carprinciple;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wangye.carprinciple.dao.DBManager;
import org.wangye.carprinciple.dao.PrincipleData;
import org.wangye.carprinciple.dao.SettingData;
import org.wangye.carprinciple.entity.Constants;
import org.wangye.carprinciple.entity.Principle;
import org.wangye.carprinciple.remote.ServerConnector;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	private DBManager		dBManager;
	private PrincipleData	principleData;
	private SettingData		settingData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dBManager = new DBManager(getApplicationContext());
		principleData = new PrincipleData(dBManager);
		settingData = new SettingData(dBManager);
		//
		init();
	}
	
	private void init() {
		if (needReloadPrinciples()) {
			reloadPrinciples();
		}
	}
	
	private boolean needReloadPrinciples() {
		try {
			String json = ServerConnector.getRemoteJson(this, "setting/getLatest");
			JSONObject jo = new JSONObject(json);
			int version = Integer.parseInt(jo.getString("version"));
			Integer lv = settingData.getVersion();
			if (lv == null || lv < version) {
				settingData.updateVersion(version);
				return true;
			}
		} catch (Exception e) {
			Log.e(Constants.LOG_TAG, "getSettings failed!", e);
		}
		return false;
	}
	
	private void reloadPrinciples() {
		List<Principle> remoteList = loadRemotePrinciples();
		if (remoteList == null) {
			new AlertDialog.Builder(this).setTitle("Network Error!").setIcon(R.drawable.warning)
					.setMessage("The network is broken!").create().show();
			return;
		}
		principleData.reloadPrinciples(remoteList);
	}
	
	private List<Principle> loadRemotePrinciples() {
		try {
			String json = ServerConnector.getRemoteJson(this, "principle/findAll");
			JSONArray principles = new JSONArray(json);
			return parsePrinciples(principles);
		} catch (Exception e) {
			Log.e(Constants.LOG_TAG, "get remote categories failed !", e);
			return null;
		}
	}
	
	private List<Principle> parsePrinciples(JSONArray ja) throws Exception {
		List<Principle> clist = new ArrayList<Principle>();
		for (int i = 0; i < ja.length(); i++) {
			JSONObject principle = ja.getJSONObject(i);
			int id = principle.getInt("id");
			String name = principle.getString("name");
			String url = principle.optString("url", null);
			int parent = principle.optInt("parentId");
			Principle p = new Principle(id, name, url, parent == 0 ? null : parent);
			clist.add(p);
		}
		return clist;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 在layout中定义的回调函数
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.goto_principle_list :
				Intent i = new Intent(getBaseContext(), PrincipleListActivity.class);
				i.putExtras(new Bundle());
				startActivity(i);
				break;
			default :
				break;
		}
	}
}
