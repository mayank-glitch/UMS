package com.example.ums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

/**
 * PageResponse
 */

@Data
@NoArgsConstructor
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-04T08:36:35.244Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)
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

  public PageResponse(Integer totalPages, Long totalCount, Integer pageSize, Integer pageNo, List<T> list) {
    this.totalPages = totalPages;
    this.totalCount = totalCount;
    this.pageSize = pageSize;
    this.pageNo = pageNo;
    this.list = list;
  }


}
