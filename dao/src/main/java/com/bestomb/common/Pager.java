package com.bestomb.common;

public class Pager {
	
	private int pageNo = 1; // 当前页
    private int pageSize = 10; // 每页多少行
    private int totalRow;  // 共多少行
    private int start; // 当前页起始行
    private int end; // 结束行
    private int totalPage;  // 共多少页
    
    public Pager(){};
    public Pager(int pageNo, int pageSize){
    	this.pageNo = pageNo;
    	this.pageSize = pageSize;
    };
    
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        if ( pageNo < 1 ) {
        	pageNo = 1;
        }
        start = pageSize * (pageNo - 1);
        end = start + pageSize > totalRow ? totalRow : start + pageSize;
        this.pageNo = pageNo;
    }
    public void setStart(int start) {
		this.start = start;
	}
	public int getStart() {
        return start;
    }
	public void setEnd(int end) {
		this.end = end;
	}
    public int getEnd() {
        return end;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalRow() {
        return totalRow;
    }
    public void setTotalRow(int totalRow) {
        totalPage = (totalRow + pageSize - 1) / pageSize;
        this.totalRow = totalRow;
        if (totalPage < pageNo) {
            pageNo = totalPage;
            start = pageSize * (pageNo - 1);
            end = totalRow;
        }
        end = start + pageSize > totalRow ? totalRow : start + pageSize;
    }
    public int getTotalPage() {
        return this.totalPage;
    }
}
