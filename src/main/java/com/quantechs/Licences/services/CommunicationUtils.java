package com.quantechs.Licences.services;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.quantechs.Licences.config.GlobalUrl;
//import com.quantechs.Licences.Utils.GlobalUrl;
import com.quantechs.Licences.exceptions.ActivationProjetPaiementException;
//import com.quantechs.Licences.exceptions.ActivationLicencePaiementException;
import com.quantechs.Licences.exceptions.CreerIdPaiementException;
import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.exceptions.VerificationPaiementKeyException;
import com.quantechs.Licences.interfaces.ActiverProjetPaiement;
import com.quantechs.Licences.interfaces.CreerIdPaiementProjet;
import com.quantechs.Licences.interfaces.InitialiserPaiementProvider;
import com.quantechs.Licences.interfaces.VerifierPaiementLicence;
import com.quantechs.Licences.payloads.in.ActivateDeactivatePayload;
import com.quantechs.Licences.payloads.in.CreerIdPaiementProjetPayload;
//import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.payloads.in.InitialiserPaiement;
//import com.quantechs.Licences.payloads.out.ResponseInitPayment;
//import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Service
public class CommunicationUtils implements InitialiserPaiementProvider, CreerIdPaiementProjet, VerifierPaiementLicence, ActiverProjetPaiement
{
    
    @Override
    public String initialiserPaiementProvider(InitialiserPaiement initialiserPaiement) throws PaiementNonEffectueException {
        try {
        final String URL = GlobalUrl.PAIEMENT_URL;
        //final String URL =  "http://192.168.100.39:8085/q(paiement";
        WebClient client = WebClient.create(URL);
        Mono<String> responseValue =
        client.post().uri("/initialize").accept(MediaType.APPLICATION_JSON)
        .bodyValue(initialiserPaiement)
        .retrieve()
        .bodyToMono(String.class);
        return responseValue.block();
    } catch (Exception e) {
        throw new PaiementNonEffectueException("Le paiement n'a pas pu etre effectué");
    }
        
    }

    @Override
    public String creerIdPaiementProjet(CreerIdPaiementProjetPayload creerIdPaiementProjetPayload) throws CreerIdPaiementException {
        
        try {
        final String URL = GlobalUrl.PAIEMENT_URL;
        WebClient client = WebClient.create(URL);
        Mono<String> responseValue =
        client.post().uri("/create_project").accept(MediaType.APPLICATION_JSON)
        .bodyValue(creerIdPaiementProjetPayload)
        .retrieve()
        .bodyToMono(String.class);
        return responseValue.block();
    } catch (Exception e) {
        throw new CreerIdPaiementException("Erreur lors de la Creation du projet de Paiement");
    }
        
    }

    @Override
    public String veriferPaiementLicence(String paiementKey) throws VerificationPaiementKeyException {
        System.out.println();
        try {
            System.out.println("avant lien");
            final String URL = GlobalUrl.PAIEMENT_URL;
            System.out.println("apres lien");
            WebClient client = WebClient.create(URL);
            Mono<String> responseValue =
            client.get().uri("/verifierPaiementStatus/"+paiementKey).accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
            return responseValue.block();
        } catch (Exception e) {
           throw new VerificationPaiementKeyException("Echec lors de la verification du Paiement de la Licence");
        }
    }

    @Override
    public String activerPaimentProjet(ActivateDeactivatePayload activateDeactivatePayload) throws ActivationProjetPaiementException {
        try {
            final String URL = GlobalUrl.PAIEMENT_URL;
            WebClient client = WebClient.create(URL);
            Mono<String> responseValue =
            client.put().uri("/change_status").accept(MediaType.APPLICATION_JSON)
            .bodyValue(activateDeactivatePayload)
            .retrieve()
            .bodyToMono(String.class);
            return responseValue.block();
        } catch (Exception e) {
           throw new ActivationProjetPaiementException("Echec lors de l'activation de projet Paiement "+activateDeactivatePayload.getId());
        }
    }

}



//import jakarta.validation.Valid;


/*@FeignClient(name="Licences", url="http://127.0.0.1:8085/qpaiement")
public interface CommunicationUtils {
     
    //@PostMapping(value="/initialize")
    @RequestMapping(value = "/initialize",method = RequestMethod.POST)
    public ResponseInitPayment initialize(@Valid @RequestBody InitialiserPaiement initialiserPaiement);
        

}*/