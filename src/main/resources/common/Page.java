package #<modelPackage>;

/**
 * 封装分页数据.
 *
 * @author #<createUser>
 * @since #<createTime>
 *
 */
public class Page {

    /** 当前页码 */
    private long currentPage;
    /** 页面显示数据大小 */
    private int pageSize;
    /** 总记录的条数 */
    private long totalCount;
    
    public Page(int currentPage, int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : 10;
        this.setCurrentPage(currentPage);
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage > 0 ? currentPage : 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

}
