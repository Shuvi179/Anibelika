package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseAudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long tomeNumber;
    private Long chapterNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private AudioBook book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private DataUser user;

    @ContentId
    private String contentId;
    @ContentLength
    private Long contentLength;
    @MimeType
    private String mimeType = "audio/mpeg";
}
