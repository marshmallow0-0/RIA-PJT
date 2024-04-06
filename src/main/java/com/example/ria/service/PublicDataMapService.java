package com.example.ria.service;
import com.example.ria.common.Constants;
import com.example.ria.dto.vo.PublicMapItem;
import com.example.ria.dto.vo.PublicMapItems;
import com.example.ria.dto.vo.kakao_format.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PublicDataMapService {
    @Autowired
    private RestClient restClient;
    @Autowired
    private KakaoMapService kakaoMapService;

    private static final String MOBILEOS = "MobileOS";
    private static final String MOBILEAPP = "MobileApp";
    private static final String TYPE = "_type";
    private static final String MAP_X = "mapX";
    private static final String MAP_Y = "mapY";
    private static final String RADIUS = "radius";
    private static final String CONTENT_TYPE_ID = "contentTypeId";
    private static final String SERVICE_KEY = "serviceKey";

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String moblicOs = "win";
    private static final String mobileApp = "ria";
    private static final String contentTypeId = "12";

    public List<PublicMapItem> searchLocationBasedList1(double mapX, double mapY, double radius, String userId) {
        try {
            log.info("PUBLIC SERVICE KEY [{}]", serviceKey);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(Constants.PUBLIC_DATA_API)
                    .path(Constants.LOCATION_BASED_PATH)
                    .queryParam(MOBILEOS, moblicOs)
                    .queryParam(MOBILEAPP, mobileApp)
                    .queryParam(TYPE, Constants.PUBLIC_API_TYPE)
                    .queryParam(MAP_X, mapX)
                    .queryParam(MAP_Y, mapY)
                    .queryParam(RADIUS, radius)
                    .queryParam(CONTENT_TYPE_ID, contentTypeId)
                    .queryParam(SERVICE_KEY, serviceKey)
                    .build(true);

            log.info("URI COMPONENTS: {}", uriComponents);

            HttpEntity<String> response = restClient.get()
                    .uri(uriComponents.toUri())
                    .retrieve()
                    .toEntity(String.class);

            log.debug("Request headers: {}", response.getHeaders());

            // JSON 응답 파싱
            PublicMapItems publicMapItems = objectMapper.readValue(response.getBody(), PublicMapItems.class);

            if (publicMapItems == null || publicMapItems.getPublicMapItems() == null) {
                log.error("No data found in the response");
                return new ArrayList<>();
            }

            publicMapItems.getPublicMapItems().forEach(place -> {
                log.info("title: {}", place.getTitle());
                try {
                    List<Document> placeList = kakaoMapService.searchImageByKeyword(place.getTitle(), 1, 1, userId);

                    for (Document image : placeList) {
                        place.setFirstImage(image.getImageUrl());
                    }
                } catch (IOException e) {
                    log.error("Error occurred while searching images for place {}: {}", place.getTitle(), e.getMessage());
                }
            });

            log.info("placeList : {}", publicMapItems.getPublicMapItems());

            return publicMapItems.getPublicMapItems();
        } catch (IOException e) {
            log.error("응답 처리 과정 중 에러 발생 ! !: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
