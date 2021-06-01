package com.xd.springbootdemo.redis.request;

import java.io.Serializable;

/**
 * @author xd
 * Created on 2021/6/1
 */
public abstract class AbstractRequest implements Serializable {
    private static final long serialVersionUID = 812452582954067303L;

    private boolean forceRefresh;

    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
