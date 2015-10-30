package demo.util;

import java.util.List;

/*
 * 分页的model类
 */
public class PageModel<T> {

	private Integer count;//总记录数
	private List<T> pageData;//待分页的记录

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
}
