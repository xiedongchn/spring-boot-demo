package com.xd.springboot.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class PageUtil {
    /**
     * 分页
     *
     * @param list     切割数据集合
     * @param pageSize 每页记录数
     * @param <T>      泛型集合
     * @return 泛型子集
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;
        List<List<T>> listArray = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            List<T> subList = new ArrayList<>();
            for (int j = 0; j < listSize; j++) {
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                if (pageIndex == (i + 1)) {
                    subList.add(list.get(j));
                }
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }
}
