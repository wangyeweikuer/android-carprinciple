package org.wangye.carprinciple;

import java.util.List;

import org.wangye.carprinciple.dao.DBManager;
import org.wangye.carprinciple.dao.PrincipleData;
import org.wangye.carprinciple.entity.Principle;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 14, 2014 9:01:45 AM
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrincipleListActivity extends ListActivity {
	
	private DBManager		dBManager;
	private PrincipleData	principleData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principle_list);
		dBManager = new DBManager(getApplicationContext());
		principleData = new PrincipleData(dBManager);
		//
		Bundle b = getIntent().getExtras();
		int parent = b.getInt("parent", -1);
		initList(parent == -1 ? null : parent);
	}
	
	private void initList(Integer parent) {
		// Cursor c = principleData.loadPrinciplesCursor(parent);
		// SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.principle_list_item, c, sArr(
		// PrincipleData.COLUMN_ID, PrincipleData.COLUMN_NAME), iArr(R.id.principle_list_item_tag,
		// R.id.principle_list_item_name), FLAG_REGISTER_CONTENT_OBSERVER);
		//
		if (parent != null) {
			Principle p = principleData.findById(parent);
			setTitle(getTitle() + "(" + p.getName() + ")");
		}
		List<Principle> items = principleData.loadPrinciples(parent);
		PrincipleListAdapter pla = new PrincipleListAdapter(this, R.layout.principle_list_item, items);
		setListAdapter(pla);
	}
	
	/**
	 * 在layout中定义的回调函数
	 */
	public void onClick(View v) {
		TextView tv = (TextView) v.findViewById(R.id.principle_list_item_name);
		Principle p = (Principle) tv.getTag();
		int id = p.get_id();
		boolean hasChildren = principleData.hasChildren(id);
		if (hasChildren) {
			Intent i = new Intent(getBaseContext(), PrincipleListActivity.class);
			i.putExtra("parent", id);
			startActivity(i);
		} else {
			Intent i = new Intent(getBaseContext(), PrincipleDetailActivity.class);
			i.putExtra("name", p.getName());
			i.putExtra("id", p.get_id());
			startActivity(i);
		}
	}
}
