package com.xd.springbootdemo.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestComponent//Mark a bean as the component should be excluded from Spring Boot's component scanning.
@SpringBootTest
public class ThreadTest {

    @Test
    public void listSplitTest() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {//测试线程数大于集合数据数量的情况
            splitList(getList(random.nextInt(20)), random.nextInt(100) + 20);
            System.out.println("***************************************");
        }

        System.out.println("\n\n\n");

        for (int i = 0; i < 10; i++) {//测试线程数小于集合数据数量的情况
            splitList(getList(random.nextInt(1000) + 100), random.nextInt(100));
            System.out.println("***************************************");
        }
    }

    /**
     * 按照线程数均匀分配List中的元素，并打印出来
     * @param data 数据集合
     * @param threadNum 线程数量
     */
    public void splitList(List<String> data, int threadNum) {
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = data.size() < threadNum ? data.size() : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : threadNum;

        System.out.println("length:" + data.size() + ",perSize:" + perSize + ",threadNum:" + threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (end > data.size()) {//计算出来的下标值超出了列表大小
                end = data.size();
                threadNum = i;//重置循环条件，使下一次循环不再执行
            }

            List<String> subList = data.subList(i * perSize, end);

            StringBuilder builder = new StringBuilder("Round:").append(i).append(",start:").append(i * perSize).append(",end:").append(end).append("----subList:");
            for (String s : subList) {
                builder.append(s).append(",");
            }
            System.out.println(builder.toString());
        }
    }

    /**
     * 模拟生成数据
     * @param size 数据量
     * @return 数据集
     */
    public List<String> getList(int size) {
        List<String> list = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            list.add("" + i);
        }
        return list;
    }
}
