package org.wangye.carprinciple;

import java.util.List;

import org.wangye.carprinciple.entity.Principle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 15, 2014 12:30:48 AM
 */
@SuppressLint({"ViewHolder", "InflateParams"})
public class PrincipleListAdapter extends ArrayAdapter<Principle> {
	
	private final LayoutInflater	vi;
	
	public PrincipleListAdapter(Context context, int resource, List<Principle> items) {
		super(context, resource, items);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = vi.inflate(R.layout.principle_list_item, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.principle_list_item_icon);
		iv.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon1));
		TextView tt = (TextView) convertView.findViewById(R.id.principle_list_item_name);
		Principle p = getItem(position);
		tt.setText(p.getName());
		tt.setTag(p);
		if (position % 2 == 0) {
			convertView.setBackgroundResource(R.color.line_background1);
		} else {
			convertView.setBackgroundResource(R.color.line_background2);
		}
		return convertView;
	}
}
