package com.example.ums.model;

import java.util.Objects;

import com.example.ums.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Response
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-04T08:36:35.244Z[GMT]")


public class Response {
    @JsonProperty("status")
    private String status = null;

    @JsonProperty("reason")
    private String reason = null;

    @JsonProperty("success")
    private boolean success;

    public Response() {
        this.success = true;
        this.reason = Constants.OK;
    }

    public Response(boolean responseFlag, String status, String reason) {
        this.success = responseFlag;
        this.status = status;
        this.reason = reason;
    }

    public Response(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public Response(Boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public Response(String status, Boolean success) {
        this.success = success;
        this.status = status;
    }

    public Response status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @Schema(description = "")

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response reason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * Get reason
     *
     * @return reason
     **/
    @Schema(description = "")

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response response = (Response) o;
        return Objects.equals(this.status, response.status) &&
                Objects.equals(this.reason, response.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, reason);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Response {\n");

        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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
