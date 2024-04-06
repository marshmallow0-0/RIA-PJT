package com.example.ria.controller;

import com.example.ria.dto.vo.PublicMapItem;
import com.example.ria.service.PublicDataMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PublicDataMapController {

    @Autowired
    PublicDataMapService publicDataMapService;

    @GetMapping(path = "/searchPlace")
    public ResponseEntity<List<PublicMapItem>> searchLocationBasedList(@RequestParam("mapX") double mapX, @RequestParam("mapY") double mapY, @RequestParam("radius") double radius, @RequestParam("userId") String userId) {
        try {
            log.info(" /search api start. user [{}], mapX [{}], mapY [{}], radius [{}]", userId, mapX, mapY, radius);

            List<PublicMapItem> list = publicDataMapService.searchLocationBasedList1(mapX, mapY, radius, userId);

            // 받은 데이터가 비어 있는 경우
            if (list.isEmpty()) {
                log.info("리스트 비어있음 ! !");
                // 클라이언트에게 HttpStatus.NO_CONTENT와 함께 빈 응답을 보냄
                return ResponseEntity.noContent().build();
            }

            // 받은 데이터가 비어 있지 않은 경우
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("응답 처리 과정 중 에러 발생 ! ! : {}", e.getMessage());
            // 예상치 못한 예외 발생 시 HttpStatus.INTERNAL_SERVER_ERROR로 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
