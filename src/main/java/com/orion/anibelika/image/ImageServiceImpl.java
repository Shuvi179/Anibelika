package com.orion.anibelika.image;

import com.orion.anibelika.image.fs.FileSystemImageProvider;
import com.orion.anibelika.url.URLPrefix;
import com.orion.anibelika.url.URLProvider;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final FileSystemImageProvider fileSystemImageProvider;
    private final URLProvider urlProvider;

    public ImageServiceImpl(FileSystemImageProvider fileSystemImageProvider, URLProvider urlProvider) {
        this.fileSystemImageProvider = fileSystemImageProvider;
        this.urlProvider = urlProvider;
    }

    @Override
    public void saveImage(URLPrefix prefix, Long id, byte[] image) {
        String path = urlProvider.getPath(prefix, id);
        fileSystemImageProvider.saveImage(path, image);
    }

    @Override
    public byte[] getImage(URLPrefix prefix, Long id) {
        String path = urlProvider.getPath(prefix, id);
        return fileSystemImageProvider.getImage(path);
    }
}
