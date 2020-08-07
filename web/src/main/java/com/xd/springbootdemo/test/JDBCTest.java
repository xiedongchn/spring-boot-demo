package com.xd.springbootdemo.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

public class JDBCTest {
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";

    /**
     * 与Statement相比:
     *
     * ①PreparedStatement接口代表预编译的语句，它主要的优势在于可以减少SQL的编译错误并增加SQL的安全性（减少SQL注射攻击的可能性）；
     *
     * ②PreparedStatement中的SQL语句是可以带参数的，避免了用字符串连接拼接SQL语句的麻烦和不安全；
     *
     * ③当批量处理SQL或频繁执行相同的查询时，PreparedStatement有明显的性能上的优势，由于数据库可以将编译优化后的SQL语句缓存起来，下次执行相同结构的语句时就会很快（不用再次编译和生成执行计划）
     * ————————————————
     * 版权声明：本文为CSDN博主「也非野人」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/u014136713/article/details/51427730
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void statementTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.addBatch("INSERT INTO user(name, idno) VALUES"); // 这是个错误的SQL
            stmt.addBatch("INSERT INTO user(name, idno) VALUES('letian', '1')");
            stmt.addBatch("INSERT INTO user(name, idno) VALUES('xiaosi', '2')");

            int[] affectRowsArray = stmt.executeBatch();
            System.out.println("影响的行数：" + Arrays.toString(affectRowsArray));
        } catch (BatchUpdateException ex) {
            ex.printStackTrace();
            System.out.println("批处理出现异常，影响的行数：" + Arrays.toString(ex.getLargeUpdateCounts()));
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        }
    }

    /**
     * 若值为0或者大于0，代表影响的行数。
     * 若为 SUCCESS_NO_INFO（-2），代表执行成功，但无法获取影响的行数。
     * 若其中一个SQL执行失败，会抛出 BatchUpdateException 异常。遇到一个SQL执行失败，那么剩下的SQL要不要继续执行？这个看JDBC的实现。如果剩下的SQL继续执行，那么影响的行数数组放在 BatchUpdateException.getUpdateCounts 中。EXECUTE_FAILED(-3) 代表执行失败。
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void preparedStatementTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        PreparedStatement stmt = null;
        int[] result = new int[0];
        try {
            System.out.println(conn.getAutoCommit());
            stmt = conn.prepareStatement("INSERT INTO user(id, idno, name) VALUES (?,?,?)");
            stmt.setObject(1, 1);
            stmt.setObject(2, "12");
            stmt.setObject(3, "张三");
            stmt.addBatch();
            stmt.setObject(1, 2);
            stmt.setObject(2, "34");
            stmt.setObject(3, "李四");
            stmt.addBatch();
            stmt.setObject(1, 3);
            stmt.setObject(2, "56");
            stmt.setObject(3, "李四");
            stmt.addBatch();
            result = concatArray(result, stmt.executeBatch());
            stmt.setObject(1, 1);
            stmt.setObject(2, "56");
            stmt.setObject(3, "李四");
            stmt.addBatch();
            stmt.setObject(1, 2);
            stmt.setObject(2, "56");
            stmt.setObject(3, "李四");
            stmt.addBatch();
            stmt.setObject(1, 4);
            stmt.setObject(2, "56");
            stmt.setObject(3, "李四");
            stmt.addBatch();

            result = concatArray(result, stmt.executeBatch());
        } catch (BatchUpdateException ex) {
            ex.printStackTrace();
            result = concatArray(result, ex.getUpdateCounts());
            System.out.println("批处理出现异常，影响的行数：" + Arrays.toString(ex.getUpdateCounts()));
            System.out.println("批处理出现异常，影响的行数L：" + Arrays.toString(ex.getLargeUpdateCounts()));
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        }
        System.out.println("影响的行数：" + Arrays.toString(result));
    }

    @Test
    public void testMod() {
        System.out.println(500%1000);
        System.out.println(500%500);
        System.out.println(501%500);
    }

    @Test
    public void testConcatArray() {
        System.out.println(Arrays.toString(concatArray(new int[]{1,2}, new int[]{3,4})));
    }

    /**
     * 合并数组
     *
     * @param first 数据在前的数组
     * @param second 数据在后的数组
     * @return 扩容后包含所有数据的数组
     */
    public static int[] concatArray(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public void insertUser() {
        try {
            DruidDataSource dataSource = getDataSource();
            DruidPooledConnection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("insert into test.user(name, idNo, age) values ('李四', '2131238477', 3)");
            statement.execute();
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DruidDataSource getDataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("87654321");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true");
        return ds;
    }
}
