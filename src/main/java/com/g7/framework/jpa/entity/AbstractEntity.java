package com.g7.framework.jpa.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dreamyao
 * @title JPA 表公共基础字段
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> {

    // 主键ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private @Nullable T id;
    // 创建时间
    @Column(name = "gmt_create") @Temporal(TemporalType.TIMESTAMP) private @Nullable Date gmtCreate;
    // 更新时间
    @Column(name = "gmt_modified") @Temporal(TemporalType.TIMESTAMP) private @Nullable Date gmtModified;

    @Nullable
    public T getId() {
        return id;
    }

    public void setId(@Nullable T id) {
        this.id = id;
    }

    @Nullable
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(@Nullable Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Nullable
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(@Nullable Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        AbstractPersistable<?> that = (AbstractPersistable<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
