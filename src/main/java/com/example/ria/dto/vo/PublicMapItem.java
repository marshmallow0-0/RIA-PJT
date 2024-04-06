package com.example.ria.dto.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicMapItem {

    // 주소
    @JsonProperty("addr1")
    private String addr1;

    // 상세 주소
    @JsonProperty("addr2")
    private String addr2;

    // 지역 코드
    @JsonProperty("areacode")
    private int areaCode;

    // 대분류
    @JsonProperty("cat1")
    private String cat1;

    // 중분류
    @JsonProperty("cat2")
    private String cat2;

    // 소분류
    @JsonProperty("cat3")
    private String cat3;

    // 콘텐츠 ID
    @JsonProperty("contentid")
    private int contentId;

    // 콘텐츠 타입 ID
    @JsonProperty("contenttypeid")
    private int contentTypeId;

    // 생성 날짜
    @JsonProperty("createdtime")
    private String createdTime;

    // 거리
    @JsonProperty("dist")
    private String dist;

    // 대표 이미지(원본)
    @JsonProperty("firstimage")
    private String firstImage;

    // 대표 이미지(섬네일)
    @JsonProperty("firstimage2")
    private String firstImage2;

    /*
    저작권 유형
    (Type1:제1유형(출처표시-권장), Type3:제3유형(제1유형+변경금지)
     */
    @JsonProperty("cpyrhtDivCd")
    private String cpyrhtDivCd;

    // X좌표
    @JsonProperty("mapx")
    private Double mapX;

    // Y좌표
    @JsonProperty("mapy")
    private Double mapY;

    // Map level
    @JsonProperty("mlevel")
    private int mLevel;

    // Y좌표
    @JsonProperty("ny")
    private int ny;

    // 수정 날짜
    @JsonProperty("modifiedtime")
    private String modifiedTime;

    // 시군구 코드
    @JsonProperty("sigungucode")
    private int sigunguCode;

    // 전화 번호
    @JsonProperty("tel")
    private String tel;

    // 제목
    @JsonProperty("title")
    private String title;
}

