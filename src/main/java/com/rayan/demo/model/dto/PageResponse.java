package com.rayan.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T>{
    List<T> content;
    Integer page;
    Integer size;
    Long totalElements;


    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.totalElements = page.getTotalElements();
    }

}
