package com.example.ria.service;

import com.example.ria.common.Constants;
import com.example.ria.dto.vo.kakao_format.Document;
import com.example.ria.dto.vo.kakao_format.KakaoResponse;
import com.example.ria.dto.vo.PlaceList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class KakaoMapService {

    private static final String QUERY = "query";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String AUTHORIZATION = "Authorization";
    private static final String KAKAO_APP_KEY_PREFIX = "KakaoAK ";

    @Autowired
    private RestClient restClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${kakao.app.key}")
    private String APP_KEY;


    public List<Document> searchImageByKeyword(String keyword, int page, int size, String userId) throws IOException {

        log.info(" KAKAO APP KEY [{}]", APP_KEY);

        // header setting
        // HttpHeaders 설정을 위한 Consumer
        Consumer<HttpHeaders> headersConsumer = headers -> headers.add(AUTHORIZATION, KAKAO_APP_KEY_PREFIX + APP_KEY);
        log.info("http header: {}", headersConsumer);
        // uri 카카오 검색 api의 기본 url을 사용하여 url 빌더를 생성
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(Constants.KAKAO_IMAGE_API)
                .path(Constants.KAKAO_IMAGE_PATH)
                .queryParam(QUERY, keyword)
                .queryParam(PAGE, page)
                .queryParam(SIZE, size)
                .build();

        log.info("uriComponent : {}", uriComponents);

        // request 요청 설정
        // api 호출, 생성된 url로 get 요청을 수행.
        HttpEntity<String> response = restClient.get().
                uri(uriComponents.toUriString()).
                headers(headersConsumer).
                retrieve()
                .toEntity(String.class);

        log.info("Request headers: {}", response.getHeaders());

        // JSON 응답 파싱
        KakaoResponse kakaoResponse = objectMapper.readValue(response.getBody(), KakaoResponse.class);

        log.debug("placeList : {}, placeListCounts ={}", kakaoResponse.getDocuments(),kakaoResponse.getDocuments().size());
        // output setting

        // 이미지 검색 결과인 Document 목록 반환
        List<Document> images = kakaoResponse.getDocuments();

        // 이미지 검색 결과의 각 Document 객체에 image search 결과 값을 저장
        for (Document image : images) {
            image.setImageUrl(image.getImageUrl());
        }

        return images;


    }


}