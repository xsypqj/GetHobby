package com.gethobby.common;

public class Page {
	
	/// Field
	private int currentPage;
	private int totalCount;
	private int pageUnit;
	private int pageSize;
	private int maxPage;
	private int beginUnitPage;
	private int endUnitPage;
	private int recommendPage;
	private int registerPage;
	private int popularityPage;
	
	/// Constructor
	public Page() {
	}
	
	public Page(int currentPage, int totalCount, int pageUnit, int pageSize) {
		this.totalCount = totalCount;
		this.pageUnit = pageUnit;
		this.pageSize = pageSize;
		
		this.maxPage = ( pageSize == 0 ) ? totalCount : (totalCount - 1) / pageSize + 1;
		this.currentPage = ( currentPage > maxPage ) ? maxPage : currentPage;
		
		this.beginUnitPage = ( ( currentPage - 1 ) / pageUnit ) * pageUnit + 1;
		
		if ( maxPage <= pageUnit ) { 
			this.endUnitPage = maxPage;
		}
		else {
			this.endUnitPage = beginUnitPage + (pageUnit - 1);
			if ( maxPage <= endUnitPage ) {
				this.endUnitPage = maxPage;
			}
		}
	}
	
	/// Getter, Setter 
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getBeginUnitPage() {
		return beginUnitPage;
	}

	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}

	public int getEndUnitPage() {
		return endUnitPage;
	}

	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}

	public int getRecommendPage() {
		return recommendPage;
	}

	public void setRecommendPage(int recommendPage) {
		this.recommendPage = recommendPage;
	}

	public int getRegisterPage() {
		return registerPage;
	}

	public void setRegisterPage(int registerPage) {
		this.registerPage = registerPage;
	}

	public int getPopularityPage() {
		return popularityPage;
	}

	public void setPopularityPage(int popularityPage) {
		this.popularityPage = popularityPage;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", totalCount=" + totalCount + ", pageUnit=" + pageUnit
				+ ", pageSize=" + pageSize + ", maxPage=" + maxPage + ", beginUnitPage=" + beginUnitPage
				+ ", endUnitPage=" + endUnitPage + ", recommendPage=" + recommendPage + ", registerPage=" + registerPage
				+ ", popularityPage=" + popularityPage + "]";
	}
	
	
	
}
