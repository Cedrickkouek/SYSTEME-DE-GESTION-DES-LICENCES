package com.quantechs.Licences.interfaces;

import com.quantechs.Licences.exceptions.ActivationProjetPaiementException;
import com.quantechs.Licences.payloads.in.ActivateDeactivatePayload;

public interface ActiverProjetPaiement {
    public String activerPaimentProjet(ActivateDeactivatePayload activateDeactivatePayload) throws ActivationProjetPaiementException;
}
