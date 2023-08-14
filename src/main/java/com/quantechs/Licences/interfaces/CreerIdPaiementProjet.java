package com.quantechs.Licences.interfaces;

import com.quantechs.Licences.exceptions.CreerIdPaiementException;
import com.quantechs.Licences.payloads.in.CreerIdPaiementProjetPayload;

public interface CreerIdPaiementProjet {
    public String creerIdPaiementProjet(CreerIdPaiementProjetPayload creerIdPaiementProjetPayload) throws CreerIdPaiementException;
}
