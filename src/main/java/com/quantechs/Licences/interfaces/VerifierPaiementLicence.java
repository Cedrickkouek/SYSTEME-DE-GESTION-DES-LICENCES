package com.quantechs.Licences.interfaces;

import com.quantechs.Licences.exceptions.VerificationPaiementKeyException;

public interface VerifierPaiementLicence {
    public String veriferPaiementLicence(String paiementKey) throws VerificationPaiementKeyException;

}
