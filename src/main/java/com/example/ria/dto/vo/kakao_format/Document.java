package com.example.ria.dto.vo.kakao_format;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Document {

    private String id;

    @JsonProperty("collection")
    private String collection;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("display_sitename")
    private String displaySitename;

    @JsonProperty("doc_url")
    private String docUrl;

    @JsonProperty("height")
    private int height;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("width")
    private int width;

    @JsonProperty("redirect_url")
    @JsonIgnoreProperties(ignoreUnknown=true)
    private String redirectUrl;

}
