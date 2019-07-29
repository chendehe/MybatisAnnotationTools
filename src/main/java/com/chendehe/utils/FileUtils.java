package com.chendehe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * @author CDH
 * @since 2019/7/29 11:01
 */
public final class FileUtils {

    private FileUtils() {}

    /**
     * 创建文件
     * 
     * @param fileName
     *            全路径文件
     * @return File
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        return file.getParentFile().mkdirs() ? file : file;
    }

    /**
     * 写入文件
     * 
     * @param file
     *            File
     * @param content
     *            内容
     */
    public static void writeToFile(File file, String content) {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), Charset.defaultCharset())) {
            osw.write(content.trim());
            osw.flush();
        } catch (IOException e) {
            throw new RuntimeException("write file failed!");
        }
    }

    /**
     * 获取模板
     * 
     * @param fileName
     *            模板文件名
     * @return 模板字符串
     */
    public static String getTemplate(String fileName) {

        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL url = Optional.ofNullable(classLoader.getResource(fileName))
            .orElseThrow(() -> new RuntimeException("template not find!"));

        try (InputStream is = url.openStream()) {
            StringBuilder builder = new StringBuilder();
            byte[] b = new byte[4096];
            while (is.read(b) != -1) {
                builder.append(new String(b));
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException("get template failed!");
        }
    }

}
