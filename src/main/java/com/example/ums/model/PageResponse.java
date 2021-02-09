package com.example.ums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

/**
 * PageResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-04T08:36:35.244Z[GMT]")


public class PageResponse<T> extends Response implements Serializable {

  @JsonProperty("totalPages")
  private Integer totalPages = null;

  @JsonProperty("totalCount")
  private Long totalCount = null;

  @JsonProperty("pageSize")
  private Integer pageSize;

  @JsonProperty("pageNo")
  private Integer pageNo;

  private List<T> list             = new ArrayList<>();

  public PageResponse() {
    super();
  }

  public PageResponse(List<T> list) {
    super();
    this.list = list;
  }

  public static <T> PageResponse<T> of(Page<T> page) {
    return new PageResponse<>(page.getContent())
            .pageNo(page.getNumber())
            .totalCount(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .pageSize(page.getSize());

  }
  public int getPageNo() {
    return pageNo;
  }

  public PageResponse<T> pageNo(int pageNo) {
    this.pageNo = pageNo;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public PageResponse<T> pageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public PageResponse<T> totalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public PageResponse<T>  totalCount(long totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  public List<T> getList() {
    return list;
  }

  public PageResponse<T> list(List<T> list) {
    this.list = list;
    return this;
  }
}
