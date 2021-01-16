package com.orion.anibelika.image.web;

import com.orion.anibelika.image.ImageFormat;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class DefaultWebImageProvider implements WebImageProvider {
    @Override
    public byte[] getImage(String path) {
        try {
            URL url = new URL(path);
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, ImageFormat.JPG.getType(), imageStream);
            return imageStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
