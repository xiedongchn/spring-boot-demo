package com.xd.springboot.test;

import com.xd.springboot.exception.ExpectedException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常测试
 */
public class ExceptionTest {
    private static final Logger log = LoggerFactory.getLogger(ExceptionTest.class);

    @Test
    public void test1() {
        Integer i = 1;
        Integer k = 0;

        try {
            if (k == 0) {
                throw new ExpectedException("除数不能为0");
            }
            Integer f = i / k;
            Assert.assertNotNull(f);
        } catch (ExpectedException e) {
            e.getMessage();
            e.printStackTrace();
            log.error("{}.....{}", e.getMessage(), e.getMsg());
        }
    }
}
