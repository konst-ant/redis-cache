package com.example.rediscache.model;

import lombok.Data;
import java.io.Serializable;
//import javax.validation.constraints.NotNull;

@Data
public class CheckResult implements Serializable {

//    @NotNull
    private Boolean result;

    private String failedCheck;

    private String message;


    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getFailedCheck() {
        return failedCheck;
    }

    public void setFailedCheck(String failedCheck) {
        this.failedCheck = failedCheck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
