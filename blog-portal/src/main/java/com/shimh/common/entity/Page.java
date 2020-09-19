/**
 */
package com.shimh.common.entity;

import java.io.Serializable;

/**
 * Description: 分页对象<br/>
 *
 * @author qingquanzhong
 * @date: 2017年09月02日 00:17:05
 * @version 1.0
 * @since JDK 1.87
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1229970440722497822L;

    /** 总记录数 */
    private Integer totalCount;

    /** 每页数目 */
    private Integer pageSize=10;

    /** 页码 */
    private Integer page;

    /** 总页数 */
    private Integer totalPages;

    private Integer offset;

    public void setPage(Integer pageSize,Integer pageNum){
        this.pageSize = pageSize;
        this.page = pageNum;
        this.offset=(pageNum - 1) * pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
