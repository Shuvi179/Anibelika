package com.orion.anibelika.image.fs;


import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

@Component
public class DefaultFileSystemImageProvider implements FileSystemImageProvider {

    @Override
    public void saveImage(String path, byte[] image) {
        if (StringUtils.isEmpty(path) || Objects.isNull(image) || image.length == 0) {
            return;
        }

        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            fileOutputStream.write(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSmallImage(String path, byte[] image) {
        if (StringUtils.isEmpty(path) || Objects.isNull(image) || image.length == 0) {
            return;
        }
        InputStream is = new ByteArrayInputStream(image);
        try {
            BufferedImage originalImage = ImageIO.read(is);
            BufferedImage smallImage = simpleResizeImage(originalImage, 100, 100);
            File file = new File(path);
            ImageIO.write(smallImage, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return Scalr.resize(originalImage, targetWidth, targetHeight);
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
