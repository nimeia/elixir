package company.project.core.base;

import java.io.Serializable;
import java.util.List;

/**
 * jdbc 分页结果
 *
 * @author huang
 * @param <T>
 */
public class ResultPage<T> implements Serializable {
    /**
     * 当前页结果集在总记录中的的位置,从0开始
     */
    private long pageNo;
    /**
     * 当前页的记录数
     */
    private long perPage;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 当前页中存放的记录,类型一般为List NOSONAR
     */
    private List<T> data;

    public ResultPage(long pageNo, long perPage) {
        this.pageNo = pageNo;
        this.perPage = perPage;
    }

    public ResultPage(long pageNo, long perPage, List<T> data) {
        this.pageNo = pageNo;
        this.perPage = perPage;
        this.data = data;
    }

    public ResultPage(long pageNo, long perPage, long totalCount, List<T> data) {
        this.pageNo = pageNo;
        this.perPage = perPage;
        this.totalCount = totalCount;
        this.data = data;
    }

    public ResultPage() {
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
