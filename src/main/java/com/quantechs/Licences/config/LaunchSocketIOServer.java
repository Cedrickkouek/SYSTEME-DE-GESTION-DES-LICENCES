package com.quantechs.Licences.config;

//import java.io.StringReader;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
/*import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;*/
import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.services.LicenceService;
/*import com.google.gson.stream.JsonReader;
import com.google.gson.JsonObject;*/

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LaunchSocketIOServer implements CommandLineRunner {

    private final SocketIOServer socketIOServer;
    private LicenceService licenceService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Server encour de demarage");
        socketIOServer.start();
        System.out.println("After server started"); 
    }

    @OnEvent(value = "licenceUser")
    public List<Licence> licenceUser(SocketIOClient client, AckRequest request, String data) throws LicenceNonTrouverException
    {
       var lesLicences = licenceService.rechercherParNumeroEtStatusActif(data);
       client.sendEvent("listeLicence", lesLicences);
       return lesLicences;
    }

    @OnEvent(value = "suspendreLicence")
    public Licence suspendreLicence(SocketIOClient client, AckRequest request, String data)
    {
        Licence licence = licenceService.desactiverLicence(data);
        client.sendEvent("suspendedLic", licence);
        return licence;
    }
    
}
