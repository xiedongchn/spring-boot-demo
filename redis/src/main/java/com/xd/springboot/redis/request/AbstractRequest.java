package com.xd.springboot.redis.request;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xd
 * Created on 2021/6/1
 */
@EqualsAndHashCode
public abstract class AbstractRequest implements Serializable {
    private static final long serialVersionUID = 812452582954067303L;

    public static final String PQCU = "PQCU";
    public static final String PQDU = "PQDU";

    private boolean forceRefresh;

    public boolean isForceRefresh() {
        return forceRefresh;
    }

    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }
}
