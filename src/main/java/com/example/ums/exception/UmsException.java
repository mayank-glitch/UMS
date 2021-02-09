package com.example.ums.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UmsException extends RuntimeException {
    private HttpStatus httpStatus;
    private              Object     data;
    private static final long       serialVersionUID = 5152631697512284659L;

    public UmsException(UmsExceptionEnum exceptionEnum) {
        super(exceptionEnum.description);
        this.httpStatus = exceptionEnum.httpStatus;
    }

    public UmsException(UmsExceptionEnum exceptionEnum, Object data){
        super(exceptionEnum.description);
        this.httpStatus = exceptionEnum.httpStatus;
        this.data = data;
    }

    public UmsException(HttpStatus httpStatus,String description){
        super(description);
        this.httpStatus=httpStatus;
    }

    public enum UmsExceptionEnum {

        USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),
        PAYLOAD_EMPTY(HttpStatus.BAD_REQUEST, "Payload is Empty"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
        USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "User Already Exist"),
        FIRST_NAME_EMPTY(HttpStatus.BAD_REQUEST, "Firstname is Empty"),
        LAST_NAME_EMPTY(HttpStatus.BAD_REQUEST, "Lastname is Empty"),
        USER_NAME_EMPTY(HttpStatus.BAD_REQUEST, "Username is Empty "),
        EMAIL_EMPTY(HttpStatus.BAD_REQUEST, "Email is Empty"),
        PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "Password is Empty"),
        PHONE_NO_EMPTY(HttpStatus.BAD_REQUEST, "Phone Number is Empty"),
        USER_ID_EMPTY(HttpStatus.BAD_REQUEST, "User Id is Empty"),
        EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Email already exist"),
        PHONE_NO_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Phone No already exist"),
        USER_NAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Username already exist"),
        NOTHING_TO_UPDATE(HttpStatus.BAD_REQUEST, "No field to update");




        private final HttpStatus httpStatus;
        private final String     description;

        UmsExceptionEnum(HttpStatus httpStatus, String description) {
            this.httpStatus = httpStatus;
            this.description = description;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getDescription() {
            return description;
        }
    }
}
