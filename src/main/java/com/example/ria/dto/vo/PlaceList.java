package com.example.ria.dto.vo;

import com.example.ria.dto.vo.kakao_format.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
@AllArgsConstructor
public class PlaceList {
    private int currentPage;
    private int totalPage;
    private int size;
    private int pageableCount;
    private List<Document> documents;
}
