package org.wangye.carprinciple.entity;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 11, 2014 5:22:14 AM
 */
public class Principle {
	
	private final int	_id;
	private String		name;
	private String		url;
	private Integer		parent;
	
	// private Principle parent;
	// private List<Principle> children = new ArrayList<Principle>();
	@Override
	public boolean equals(Object c) {
		if (c != null && c instanceof Principle) {
			Principle c2 = (Principle) c;
			return name.equals(c2.name) && url.equals(c2.url);
		}
		return false;
	}
	
	public Principle(int _id, String name, String url, Integer parent) {
		this._id = _id;
		this.name = name;
		this.url = url;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	// public List<Principle> getChildren() {
	// return children;
	// }
	//
	// public void setChildren(List<Principle> children) {
	// this.children = children;
	// }
	// public Principle getParent() {
	// return parent;
	// }
	//
	// public void setParent(Principle parent) {
	// this.parent = parent;
	// }
	public Integer getParent() {
		return parent;
	}
	
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	
	public int get_id() {
		return _id;
	}
}
