package com.orion.anibelika.image;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DefaultFileSystemImageProvider implements FileSystemImageProvider {

    @Override
    public boolean saveImage(String path, byte[] photo) {
        File file = new File(path);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(photo);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public byte[] getImage(String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            return in.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
