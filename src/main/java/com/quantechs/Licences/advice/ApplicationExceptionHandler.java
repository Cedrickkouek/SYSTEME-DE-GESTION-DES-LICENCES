package com.quantechs.Licences.advice;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.quantechs.Licences.exceptions.ActivationLicencePaiementException;
import com.quantechs.Licences.exceptions.ActivationProjetPaiementException;
import com.quantechs.Licences.exceptions.CreerIdPaiementException;
import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.exceptions.LicenceNonCreerException;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.exceptions.PaiementNonValideException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.exceptions.VerificationPaiementKeyException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> gererLesArgumentsInvalid(MethodArgumentNotValidException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        excep.getBindingResult().getFieldErrors().forEach(erreur -> {
            errorMap.put(erreur.getField(), erreur.getDefaultMessage());
        });

        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LicenceNonTrouverException.class)
    public Map<String, String> gererExceptionsDesLicences(LicenceNonTrouverException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ActivationProjetPaiementException.class)
    public Map<String, String> gererExceptionsDesLicences(ActivationProjetPaiementException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PaiementNonValideException.class)
    public Map<String, String> gererValiditePaiement(PaiementNonValideException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NumberFormatException.class)
    public Map<String, String> gererExceptionsDeType(NumberFormatException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Format d'entrée non valid, verifier les champs et reessayer! \u274C ", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProjetNonTrouverException.class)
    public Map<String, String> gererExceptionsDesProjets(ProjetNonTrouverException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceNonTrouverException.class)
    public Map<String, String> gererExceptionsDesProjets(ServiceNonTrouverException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PaiementNonEffectueException.class)
    public Map<String, String> gererLesPaiementsDansLicence(PaiementNonEffectueException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LicenceNonCreerException.class)
    public Map<String, String> gererLicenceNonCreer(LicenceNonCreerException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ActivationLicencePaiementException.class)
    public Map<String, String> gererActivationProjetPaiement(ActivationLicencePaiementException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(VerificationPaiementKeyException.class)
    public Map<String, String> verificationPaiementKeyException(VerificationPaiementKeyException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CreerIdPaiementException.class)
    public Map<String, String> creerIdPaiementException(CreerIdPaiementException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> valeurNonConforme(HttpMessageNotReadableException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Format d'entrée non valid, verifier les champs et reessayer! \u274C ", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public Map<String, String> algorithmeException(NoSuchAlgorithmException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public Map<String, String> typeNonAccepter(HttpMediaTypeNotAcceptableException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Format d'entrée non valid, verifier les champs et reessayer! \u274C ", excep.getLocalizedMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(ConversionFailedException.class)
    public Map<String, String> conversionImpossible(ConversionFailedException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Conversion des champs vers d'autres type impossible, verifier les champs et reessayer! \u274C ", excep.getLocalizedMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(EnumerationNotFoundException.class)
    public Map<String, String> enumerationException(EnumerationNotFoundException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Message D'erreur \u274C ", excep.getLocalizedMessage());
        return errorMap;
    }
    
}
