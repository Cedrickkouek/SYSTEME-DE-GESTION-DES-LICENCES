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

import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.interfaces.InitialiserPaiementProvider;
//import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.payloads.in.InitialiserPaiement;
//import com.quantechs.Licences.payloads.out.ResponseInitPayment;
//import jakarta.validation.Valid;
import reactor.core.publisher.Mono;


//import jakarta.validation.Valid;


/*@FeignClient(name="Licences", url="http://127.0.0.1:8085/qpaiement")
public interface CommunicationUtils {
     
    //@PostMapping(value="/initialize")
    @RequestMapping(value = "/initialize",method = RequestMethod.POST)
    public ResponseInitPayment initialize(@Valid @RequestBody InitialiserPaiement initialiserPaiement);
        

}*/
@Service
public class CommunicationUtils implements InitialiserPaiementProvider
{
    @Override
    public String initialiserPaiementProvider(InitialiserPaiement initialiserPaiement) throws PaiementNonEffectueException {
        try {
        final String URL =  "http://127.0.0.1:8085/qpaiement";
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
    
}

    


 