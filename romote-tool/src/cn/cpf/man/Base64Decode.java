package cn.cpf.man;

import cn.cpf.util.CommandLineArgs;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Base64;

/**
 * <b>Description : </b> 解析64编码为文件
 *
 * @author CPF
 * @date 2020/11/13
 **/
public class Base64Decode {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws IOException {
        CommandLineArgs.requireNonEmpty(args, "参数不能为空");
        final CommandLineArgs lineArgs = CommandLineArgs.analyze(args);
        final String filePath = lineArgs.getKeyVal("fp");
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在 ==> " + filePath);
        }
        if (!file.isFile()) {
            throw new FileSystemException("不是文件 ==> " + filePath);
        }
        final String savePath = lineArgs.getDefaultKeyVal("sp", file.getParentFile().getPath() + File.separator + "base64.zip");
        try (FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            final int read = reader.read(chars);
            System.out.println(read);
            String base64 = new String(chars);
            byte[] bytes = Base64.getDecoder().decode(base64);
            writeFile(savePath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String savePath, byte[] content) {
        final File file = new File(savePath);
        try (final FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
