package com.le.cs.ws.service.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-05-13 15:40
 **/
@Data
public class AuthMessage implements Serializable {
    private static final long serialVersionUID = 8554862149832250645L;

    private String token;
}
