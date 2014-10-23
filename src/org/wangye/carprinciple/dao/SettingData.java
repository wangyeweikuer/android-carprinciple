package org.wangye.carprinciple.dao;

import static org.wangye.carprinciple.util.IOUtil.sArr;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 13, 2014 9:15:52 PM
 */
public class SettingData {
	
	private final String	TABLE_NAME		= "setting";
	private final String	COLUMN_NAME		= "name";
	private final String	COLUMN_VALUE	= "value";
	private final String	NAME_VERSION	= "version";
	private final DBManager	dbManager;
	
	public SettingData(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public Integer getVersion() {
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor c = db.query(TABLE_NAME, sArr(COLUMN_VALUE), COLUMN_NAME + " = ?", sArr(NAME_VERSION), null, null, null);
		ContentValues cv = new ContentValues();
		if (c.moveToNext()) {
			DatabaseUtils.cursorStringToContentValuesIfPresent(c, cv, COLUMN_VALUE);
			return cv.getAsInteger(COLUMN_VALUE);
		} else {
			return null;
		}
	}
	
	public void updateVersion(int version) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		db.beginTransaction();
		Integer lv = getVersion();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, NAME_VERSION);
		cv.put(COLUMN_VALUE, version + "");
		if (lv == null) {
			db.insert(TABLE_NAME, null, cv);
		} else if (version > lv) {
			db.update(TABLE_NAME, cv, COLUMN_NAME + " = ?", sArr(NAME_VERSION));
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
