package common.base;

/**
 * @author ntarget
 * Create date 		: 2017-01-09
 */

public abstract class BaseModel {

	/* paging */
	protected String page		= null;
	protected String idx		= "0";
	protected int startIndex	= 0;
	protected int firstIndex 	= 1;
	protected int lastIndex 	= 1;


	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

}
