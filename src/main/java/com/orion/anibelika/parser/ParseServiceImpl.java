package com.orion.anibelika.parser;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.image.web.WebImageProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParseServiceImpl implements ParseService {

    private final WebImageProvider webImageProvider;

    private static final String PARSE_URL_IMAGE_QUERY_SELECTOR = ".c-poster";
    private static final String PARSE_DESCRIPTION_QUERY_SELECTOR = ".b-text_with_paragraphs";

    public ParseServiceImpl(WebImageProvider webImageProvider) {
        this.webImageProvider = webImageProvider;
    }

    @Override
    public DefaultAudioBookInfoDTO parseBookByUrl(String url) {
        DefaultAudioBookInfoDTO dto = new DefaultAudioBookInfoDTO();
        try {
            Document page = Jsoup.connect(url).get();
            dto.setImage(webImageProvider.getImage(parseImageURL(page)));
            dto.setName(getName(page).trim());
            dto.setDescription(getDescription(page));
            dto.setCreatedByCurrentUser(false);
            dto.setTome(0L);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }

    private String parseImageURL(Document document) {
        Elements elements = document.select(PARSE_URL_IMAGE_QUERY_SELECTOR);
        return elements.get(0).getElementsByTag(ElementTag.IMG.getElement()).attr(Attribute.SRC.getAttr());
    }

    private String getName(Document document) {
        Elements elements = document.getElementsByTag(ElementTag.H1.getElement());
        return ((TextNode) elements.first().childNode(0)).text();
    }

    private String getDescription(Document document) {
        Element descriptionToParse = document.select(PARSE_DESCRIPTION_QUERY_SELECTOR).first();
        StringBuilder result = new StringBuilder();
        parseDFS(descriptionToParse, result);
        return result.toString();
    }

    private void parseDFS(Node currentNode, StringBuilder builder) {
        if (currentNode instanceof TextNode) {
            builder.append(((TextNode) currentNode).text());
        } else {
            if (ElementTag.BR.getElement().equals(((Element) currentNode).tagName())) {
                builder.append(System.lineSeparator());
                return;
            }
            for (Node node : currentNode.childNodes()) {
                parseDFS(node, builder);
            }
        }
    }
}
