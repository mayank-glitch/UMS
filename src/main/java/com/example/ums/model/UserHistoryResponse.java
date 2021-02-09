package com.example.ums.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * UserHistoryResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-04T08:36:35.244Z[GMT]")


public class UserHistoryResponse {

  @JsonProperty("userResponse")
  private UserResponse userResponse;

  @JsonProperty("version")
  private Integer version = null;

  public UserHistoryResponse version(Integer version) {
    this.version = version;
    return this;
  }

  public UserResponse getUserBaseResponse() {
    return userResponse;
  }

  public void setUserBaseResponse(UserResponse userResponse) {
    this.userResponse = userResponse;
  }

  /**
   * Get version
   * @return version
   **/
  @Schema(description = "")
  
    public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserHistoryResponse userHistoryResponse = (UserHistoryResponse) o;
    return Objects.equals(this.version, userHistoryResponse.version) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserHistoryResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
