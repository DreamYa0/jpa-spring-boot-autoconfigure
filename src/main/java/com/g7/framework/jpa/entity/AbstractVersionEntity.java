package com.g7.framework.jpa.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * @author dreamyao
 * @title
 * @date 2021/11/15 12:21 上午
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractVersionEntity<T extends Serializable> extends AbstractEntity<T> {

    // 版本，使用做乐观锁用
    @Version private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
