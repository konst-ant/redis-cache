package com.example.rediscache.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class CheckResult implements Serializable {

    private boolean status;

    private String failedCheck;

    private String message;

}
