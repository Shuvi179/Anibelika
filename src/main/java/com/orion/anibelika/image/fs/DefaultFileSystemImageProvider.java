package com.orion.anibelika.image.fs;


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
    public void saveImage(String path, byte[] photo) {
        if (StringUtils.isEmpty(path) || Objects.isNull(photo) || photo.length == 0) {
            return;
        }

        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            fileOutputStream.write(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getImage(String path) {
        if (StringUtils.isEmpty(path)) {
            return EMPTY_RESULT;
        }

        try (FileInputStream in = new FileInputStream(new File(path))) {
            return in.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_RESULT;
    }
}
