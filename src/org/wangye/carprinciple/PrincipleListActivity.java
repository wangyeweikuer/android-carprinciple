package org.wangye.carprinciple;

import java.util.List;

import org.wangye.carprinciple.dao.DBManager;
import org.wangye.carprinciple.dao.PrincipleData;
import org.wangye.carprinciple.entity.PrincipleOrCategoryItem;

import android.annotation.SuppressLint;
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
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrincipleListActivity extends ListActivity {

    private DBManager dBManager;
    private PrincipleData principleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principle_list);
        dBManager = new DBManager(getApplicationContext());
        principleData = new PrincipleData(dBManager);
        //
        Bundle b = getIntent().getExtras();
        String category = b.getString("category", "");
        initList(category);
    }

    private void initList(String category) {
        if (category.length() > 0) {
            String[] cs = category.split("/");
            setTitle(getTitle() + "(" + cs[cs.length - 1] + ")");
        }
        List<PrincipleOrCategoryItem> items = principleData.loadPrinciplesByCategory(category);
        PrincipleListAdapter pla = new PrincipleListAdapter(this, R.layout.principle_list_item, items);
        setListAdapter(pla);
    }

    /**
     * 在layout中定义的回调函数
     */
    public void onClick(View v) {
        TextView tv = (TextView) v.findViewById(R.id.principle_list_item_name);
        PrincipleOrCategoryItem p = (PrincipleOrCategoryItem) tv.getTag();
        if (p.isPrincipleNotCategory()) {
            Intent i = new Intent(getBaseContext(), PrincipleDetailActivity.class);
            i.putExtra("name", p.getPrinciple().getName());
            i.putExtra("url", p.getPrinciple().getUrl());
            startActivity(i);
        } else {
            Intent i = new Intent(getBaseContext(), PrincipleListActivity.class);
            i.putExtra("category", p.getCategory());
            startActivity(i);
        }
    }
}
