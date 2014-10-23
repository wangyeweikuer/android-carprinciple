package org.wangye.carprinciple.dao;

import static org.wangye.carprinciple.util.IOUtil.sArr;

import java.util.ArrayList;
import java.util.List;

import org.wangye.carprinciple.entity.Principle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 11, 2014 5:01:19 AM
 */
public class PrincipleData {
	
	public static final String	TABLE_NAME		= "principle";
	public static final String	COLUMN_ID		= "_id";
	public static final String	COLUMN_NAME		= "name";
	public static final String	COLUMN_URL		= "url";
	public static final String	COLUMN_PARENT	= "parent";
	private final DBManager		dbManager;
	
	public PrincipleData(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public Cursor loadPrinciplesCursor(Integer parent) {
		SQLiteDatabase db = dbManager.getReadableDatabase();
		String s = parent == null ? COLUMN_PARENT + " is null" : COLUMN_PARENT + " = ?";
		String[] sa = parent == null ? null : sArr(parent + "");
		return db.query(TABLE_NAME, sArr(COLUMN_ID, COLUMN_NAME, COLUMN_URL), s, sa, null, null, null, null);
	}
	
	public List<Principle> loadPrinciples(Integer parent) {
		SQLiteDatabase db = dbManager.getReadableDatabase();
		String s = parent == null ? COLUMN_PARENT + " is null" : COLUMN_PARENT + " = ?";
		String[] sa = parent == null ? null : sArr(parent + "");
		Cursor c = db.query(TABLE_NAME, sArr(COLUMN_ID, COLUMN_NAME, COLUMN_URL, COLUMN_PARENT), s, sa, null, null,
				null, null);
		List<Principle> rlist = new ArrayList<Principle>();
		while (c.moveToNext()) {
			ContentValues v = new ContentValues();
			DatabaseUtils.cursorRowToContentValues(c, v);
			Principle p = new Principle(v.getAsInteger(COLUMN_ID), v.getAsString(COLUMN_NAME),
					v.getAsString(COLUMN_URL), v.getAsInteger(COLUMN_PARENT));
			rlist.add(p);
		}
		return rlist;
	}
	
	public void updatePrinciple(Principle c) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_URL, c.getUrl());
		db.update(TABLE_NAME, values, COLUMN_NAME + " = ?", sArr(c.getName()));
	}
	
	public void addPrinciple(Principle c) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, c.getName());
		values.put(COLUMN_URL, c.getUrl());
		values.put(COLUMN_ID, c.get_id());
		values.put(COLUMN_PARENT, c.getParent());
		db.insert(TABLE_NAME, null, values);
	}
	
	public void deleteCategory(String name) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		db.delete(TABLE_NAME, COLUMN_NAME + " = ?", sArr(name));
	}
	
	public void reloadPrinciples(List<Principle> remoteList) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		db.beginTransaction();
		//
		db.delete(TABLE_NAME, null, null);
		//
		for (Principle p : remoteList) {
			addPrinciple(p);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	public boolean hasChildren(int id) {
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor c = db
				.rawQuery("select count(*) from " + TABLE_NAME + " where " + COLUMN_PARENT + " = ?", sArr(id + ""));
		if (c.moveToNext()) {
			ContentValues values = new ContentValues();
			DatabaseUtils.cursorIntToContentValuesIfPresent(c, values, "count(*)");
			return values.getAsInteger("count(*)") > 0;
		} else {
			return false;
		}
	}
	
	public Principle findById(int id) {
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor c = db.query(TABLE_NAME, sArr(COLUMN_ID, COLUMN_NAME, COLUMN_PARENT, COLUMN_URL), COLUMN_ID + " = ?",
				sArr(id + ""), null, null, null);
		if (c.moveToNext()) {
			ContentValues v = new ContentValues();
			DatabaseUtils.cursorRowToContentValues(c, v);
			return new Principle(v.getAsInteger(COLUMN_ID), v.getAsString(COLUMN_NAME), v.getAsString(COLUMN_URL),
					v.getAsInteger(COLUMN_PARENT));
		} else {
			return null;
		}
	}
}
