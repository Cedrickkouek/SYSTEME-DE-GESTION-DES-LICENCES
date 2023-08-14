package com.quantechs.Licences.payloads.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class verificationLicenceOutput {
    private boolean result;
    private String classSecondaire;
}
