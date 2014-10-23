package org.wangye.carprinciple.dao;

import org.wangye.carprinciple.R;
import org.wangye.carprinciple.entity.Constants;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 11, 2014 5:01:19 AM
 */
public class DBManager extends SQLiteOpenHelper {
	
	private final String	CREATE_PRINCIPLE_TABLE;
	private final String	DELETE_PRINCIPLE_TABLE;
	private final String	CREATE_SETTING_TABLE;
	private final String	DELETE_SETTING_TABLE;
	
	public DBManager(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
		Resources r = context.getResources();
		CREATE_PRINCIPLE_TABLE = r.getString(R.string.createPrincipleTable);
		DELETE_PRINCIPLE_TABLE = r.getString(R.string.deletePrincipleTable);
		CREATE_SETTING_TABLE = r.getString(R.string.createSettingTable);
		DELETE_SETTING_TABLE = r.getString(R.string.deleteSettingTable);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PRINCIPLE_TABLE);
		db.execSQL(CREATE_SETTING_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DELETE_PRINCIPLE_TABLE);
		db.execSQL(DELETE_SETTING_TABLE);
		onCreate(db);
	}
}
