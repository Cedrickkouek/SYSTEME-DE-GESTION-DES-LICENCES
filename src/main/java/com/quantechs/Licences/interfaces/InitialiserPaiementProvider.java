package com.quantechs.Licences.interfaces;

import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.payloads.in.InitialiserPaiement;

public interface InitialiserPaiementProvider {
    public String initialiserPaiementProvider(InitialiserPaiement initialiserPaiement) throws PaiementNonEffectueException;
}
