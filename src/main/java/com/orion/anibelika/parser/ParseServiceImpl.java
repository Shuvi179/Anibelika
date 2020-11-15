package com.orion.anibelika.parser;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.image.WebImageProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParseServiceImpl implements ParseService {

    private final WebImageProvider webImageProvider;

    private static final String PARSE_URL_IMAGE_QUERY_SELECTOR = ".c-poster";

    public ParseServiceImpl(WebImageProvider webImageProvider) {
        this.webImageProvider = webImageProvider;
    }

    @Override
    public DefaultAudioBookInfoDTO parseBookByUrl(String url) {
        DefaultAudioBookInfoDTO dto = new DefaultAudioBookInfoDTO();
        try {
            Document page = Jsoup.connect(url).get();
            dto.setImage(webImageProvider.getImage(parseImageURL(page)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }

    private String parseImageURL(Document document) {
        Elements elements = document.select(PARSE_URL_IMAGE_QUERY_SELECTOR);
        return elements.get(0).getElementsByTag(ElementTag.IMG.getElement()).attr(Attribute.SRC.getAttr());
    }


}
