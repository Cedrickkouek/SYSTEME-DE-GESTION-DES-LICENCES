package com.quantechs.Licences.payloads.in;

import com.quantechs.Licences.enumeration.MoyenPaiement;
import com.quantechs.Licences.enumeration.QCurrency;
import com.quantechs.Licences.exceptions.EnumerationNotFoundException;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitialiserPaiement {
    private QCurrency qcurrency;
    private MoyenPaiement moyenPaiement;
    @NotBlank(message = "id du projet requis")
    private String idProjet;
    @NotBlank(message = "id du service requis")
    private String idService;
    @NotBlank(message = "id du client requis")
    private String idClient;
    @Min(25)
    private int montant;
    @NotBlank(message = "La description est requise")
    private String description;

    public InitialiserPaiement validation() throws EnumerationNotFoundException{
        try {
            QCurrency.valueOf(qcurrency.name());
            try {
                MoyenPaiement.valueOf(moyenPaiement.name());
                if(montant%10==0){
                    return this;
                }else{
                    throw new EnumerationNotFoundException("le montant doit etre un multuple de 10");
                }
            } catch (Exception e) {
                throw new EnumerationNotFoundException("entrer des valeur requisse pour le MoyenPaiement "+MoyenPaiement.values());
            }
        } catch (Exception e) {
            throw new EnumerationNotFoundException("entrer des valeur requisse pour la currency "+QCurrency.values());
        }
    }
}
