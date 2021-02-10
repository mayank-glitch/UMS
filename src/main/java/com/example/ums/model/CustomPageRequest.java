package com.example.ums.model;

import lombok.Data;

@Data
public class CustomPageRequest {

    private Integer pageSize;
    private Integer pageNo;
    private String pageState;

}
