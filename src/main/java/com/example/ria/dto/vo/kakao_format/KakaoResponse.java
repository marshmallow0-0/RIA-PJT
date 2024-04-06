package com.example.ria.dto.vo.kakao_format;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
public class KakaoResponse {
    private Meta meta;
    private List<Document> documents;


}