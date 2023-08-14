package com.quantechs.Licences.payloads.out;

//import java.time.LocalDate;

//import com.quantechs.Licences.enumeration.StatusPaiement;

import lombok.Builder;
import lombok.Data;

//import com.quantechs.Licences.enumeration.MoyenPaiement;
//import com.quantechs.Licences.enumeration.QCurrency;

@Builder
@Data
public class paiementInfos {
    private String id;
    private String idService;
    private String idprojet;
    private String idCleint;
    private String paiementkey;
    private String qCurrency;
    private String moyenPaiement;
    private int montant ;
    private String datePaiement;
    private String description;
    private String statusPaiement;
    private String paymentUrl;
    private String numero_payeur;
    private String indicatifPay;

}
