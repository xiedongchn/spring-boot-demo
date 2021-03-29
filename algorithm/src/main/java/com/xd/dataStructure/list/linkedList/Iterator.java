package com.xd.dataStructure.list.linkedList;

import java.io.Serializable;

/**
 * 自定义迭代器
 *
 * @author xd
 * Created on 2021/3/29
 */
public interface Iterator<E> extends Serializable {

    /**
     * 是否有下一个
     *
     * @return boolean
     */
    boolean hasNext();

    /**
     * 获取下一个
     *
     * @return E
     */
    E next();
}
