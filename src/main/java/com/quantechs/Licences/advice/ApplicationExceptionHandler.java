package com.quantechs.Licences.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
}
