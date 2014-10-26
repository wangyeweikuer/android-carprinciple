package org.wangye.carprinciple.entity;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 11, 2014 5:22:14 AM
 */
public class Principle {

    private final int _id;
    private final String name;
    private final String url;
    private final String category;

    @Override
    public boolean equals(Object c) {
        if (c != null && c instanceof Principle) {
            Principle c2 = (Principle) c;
            return name.equals(c2.name) && url.equals(c2.url);
        }
        return false;
    }

    public Principle(int _id, String name, String url, String category) {
        this._id = _id;
        this.name = name;
        this.url = url;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int get_id() {
        return _id;
    }

    public String getCategory() {
        return category;
    }
}
