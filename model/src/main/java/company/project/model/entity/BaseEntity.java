package company.project.model.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoqian on 2015/5/21.
 *
 * jpa 公用Entity
 * @author huang
 */
@MappedSuperclass
abstract public class BaseEntity implements Serializable {

    /**
     * 创建日期
     */
    @Column(name = "date_created")
    private Date dateCreated;

    /**
     * 最后更新日期
     */
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "is_delete")
    private Boolean deleteFlag = false;

    private Date deleteDate;

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        if(deleteFlag)
            this.deleteDate = new Date();
        this.deleteFlag = deleteFlag;
    }

    @PrePersist
    protected void prePersist(){
        dateCreated = new Date();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    private void preUpdate(){
        lastUpdated = new Date();
    }
}