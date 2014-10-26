package org.wangye.carprinciple.dao;

import static org.wangye.carprinciple.util.IOUtil.sArr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.wangye.carprinciple.entity.Principle;
import org.wangye.carprinciple.entity.PrincipleOrCategoryItem;

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

    public static final String TABLE_NAME = "principle";
    public static final String C_ID = "_id";
    public static final String C_NAME = "name";
    public static final String C_URL = "url";
    public static final String C_CATEGORY = "category";
    private final DBManager dbManager;

    public PrincipleData(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    // public Cursor loadPrinciplesCursor(Integer parent) {
    // SQLiteDatabase db = dbManager.getReadableDatabase();
    // String s = parent == null ? COLUMN_PARENT + " is null" : COLUMN_PARENT + " = ?";
    // String[] sa = parent == null ? null : sArr(parent + "");
    // return db.query(TABLE_NAME, sArr(C_ID, C_NAME, C_URL), s, sa, null, null, null, null);
    // }
    //
    // public List<Principle> loadPrinciples(Integer parent) {
    // SQLiteDatabase db = dbManager.getReadableDatabase();
    // String s = parent == null ? COLUMN_PARENT + " is null" : COLUMN_PARENT + " = ?";
    // String[] sa = parent == null ? null : sArr(parent + "");
    // Cursor c = db.query(TABLE_NAME, sArr(C_ID, C_NAME, C_URL, COLUMN_PARENT), s, sa, null, null, null, null);
    // List<Principle> rlist = new ArrayList<Principle>();
    // while (c.moveToNext()) {
    // ContentValues v = new ContentValues();
    // DatabaseUtils.cursorRowToContentValues(c, v);
    // Principle p =
    // new Principle(v.getAsInteger(C_ID), v.getAsString(C_NAME), v.getAsString(C_URL),
    // v.getAsInteger(COLUMN_PARENT));
    // rlist.add(p);
    // }
    // return rlist;
    // }

    //
    // a > b > d1
    // a > d2
    // b > c > d3
    public List<PrincipleOrCategoryItem> loadPrinciplesByCategory(String category) {
        List<PrincipleOrCategoryItem> list = loadCategories(category);
        if (list.size() == 0) {
            list = loadPrinciples(category);
        }
        return list;
    }

    private List<PrincipleOrCategoryItem> loadCategories(String category) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        String selection = "category like \"" + category + "/%\"";
        Cursor c = db.query(TABLE_NAME, sArr(C_ID, C_NAME, C_URL, C_CATEGORY), selection, null, null, null, null, null);
        List<PrincipleOrCategoryItem> plist = new ArrayList<PrincipleOrCategoryItem>();
        Set<String> categories = new HashSet<String>();
        while (c.moveToNext()) {
            ContentValues v = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(c, v);
            Principle p =
                    new Principle(v.getAsInteger(C_ID), v.getAsString(C_NAME), v.getAsString(C_URL),
                            v.getAsString(C_CATEGORY));
            //
            String topCategory = category + "/";
            int idx = p.getCategory().indexOf(topCategory);
            String pc = p.getCategory().substring(idx + topCategory.length());
            if (pc.length() > 0) {
                String[] cs = pc.split("/");
                if (!categories.contains(cs[0])) {
                    plist.add(PrincipleOrCategoryItem.newCategory(category + "/" + cs[0], cs[0]));
                    categories.add(cs[0]);
                }
            } else {
                plist.add(PrincipleOrCategoryItem.newPrinciple(p, p.getName()));
            }
        }
        return plist;
    }

    private List<PrincipleOrCategoryItem> loadPrinciples(String category) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        String selection = "category = ?";
        Cursor c =
                db.query(TABLE_NAME, sArr(C_ID, C_NAME, C_URL, C_CATEGORY), selection, sArr(category), null, null,
                        null, null);
        List<PrincipleOrCategoryItem> list = new ArrayList<PrincipleOrCategoryItem>();
        while (c.moveToNext()) {
            ContentValues v = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(c, v);
            Principle p =
                    new Principle(v.getAsInteger(C_ID), v.getAsString(C_NAME), v.getAsString(C_URL),
                            v.getAsString(C_CATEGORY));
            list.add(PrincipleOrCategoryItem.newPrinciple(p, p.getName()));
        }
        return list;
    }

    // public void updatePrinciple(Principle c) {
    // SQLiteDatabase db = dbManager.getWritableDatabase();
    // ContentValues values = new ContentValues();
    // values.put(C_URL, c.getUrl());
    // db.update(TABLE_NAME, values, C_NAME + " = ?", sArr(c.getName()));
    // }

    public void addPrinciple(Principle c) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_NAME, c.getName());
        values.put(C_URL, c.getUrl());
        values.put(C_ID, c.get_id());
        values.put(C_CATEGORY, c.getCategory());
        db.insert(TABLE_NAME, null, values);
    }

    // public void deleteCategory(String name) {
    // SQLiteDatabase db = dbManager.getWritableDatabase();
    // db.delete(TABLE_NAME, C_NAME + " = ?", sArr(name));
    // }

    public void reloadPrinciples(String createTableSql, String dropTableSql, List<Principle> remoteList) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.beginTransaction();
        //
        db.execSQL(dropTableSql);
        db.execSQL(createTableSql);
        // db.delete(TABLE_NAME, null, null);
        //
        for (Principle p : remoteList) {
            addPrinciple(p);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    // public boolean hasChildren(int id) {
    // SQLiteDatabase db = dbManager.getReadableDatabase();
    // Cursor c =
    // db.rawQuery("select count(*) from " + TABLE_NAME + " where " + COLUMN_PARENT + " = ?", sArr(id + ""));
    // if (c.moveToNext()) {
    // ContentValues values = new ContentValues();
    // DatabaseUtils.cursorIntToContentValuesIfPresent(c, values, "count(*)");
    // return values.getAsInteger("count(*)") > 0;
    // } else {
    // return false;
    // }
    // }

    // public Principle findById(int id) {
    // SQLiteDatabase db = dbManager.getReadableDatabase();
    // Cursor c =
    // db.query(TABLE_NAME, sArr(C_ID, C_NAME, C_CATEGORY, C_URL), C_ID + " = ?", sArr(id + ""), null, null,
    // null);
    // if (c.moveToNext()) {
    // ContentValues v = new ContentValues();
    // DatabaseUtils.cursorRowToContentValues(c, v);
    // return new Principle(v.getAsInteger(C_ID), v.getAsString(C_NAME), v.getAsString(C_URL),
    // v.getAsString(C_CATEGORY));
    // } else {
    // return null;
    // }
    // }
}
