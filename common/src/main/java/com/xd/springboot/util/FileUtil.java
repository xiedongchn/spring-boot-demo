package com.xd.springboot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author xd
 * Created on 2021/3/2
 */
public class FileUtil {

    /**
     * 获取文件的字节数组
     *
     * @param file 文件
     * @return
     */
    public static byte[] getFileByteArray(File file) {
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = null;
        try (FileInputStream fi = new FileInputStream(file)) {
            buffer = new byte[(int) fileSize];
            int numRead;
            int offset = 0;
            while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
