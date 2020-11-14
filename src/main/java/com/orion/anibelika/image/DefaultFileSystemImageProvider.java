package com.orion.anibelika.image;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Component
public class DefaultFileSystemImageProvider implements FileSystemImageProvider {

    @Override
    public boolean saveImage(String path, byte[] photo) {
        if (StringUtils.isEmpty(path) || Objects.isNull(photo) || photo.length == 0) {
            return false;
        }

        File file = new File(path);
        boolean result = true;
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            if (file.exists()) {
                result = file.delete();
            }
            result &= file.createNewFile();
            fileOutputStream.write(photo);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public byte[] getImage(String path) {
        if (StringUtils.isEmpty(path)) {
            return new byte[0];
        }

        try (FileInputStream in = new FileInputStream(new File(path))) {
            return in.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
