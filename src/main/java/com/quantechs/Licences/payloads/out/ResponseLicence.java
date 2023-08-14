package com.quantechs.Licences.payloads.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseLicence {
    private int code;
    private String message;
    private Object data;
}
