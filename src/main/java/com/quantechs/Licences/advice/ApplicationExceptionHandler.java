package com.quantechs.Licences.advice;

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

import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;

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

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> valeurNonConforme(HttpMessageNotReadableException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("\u274C Format d'entrée non valid, verifier les champs et reessayer! \u274C ", excep.getMessage());
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
}
