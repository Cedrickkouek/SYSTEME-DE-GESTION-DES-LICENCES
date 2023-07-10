package com.quantechs.Licences.services;

import java.time.LocalDate;
//import java.time.LocalDate;
//import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
//import java.util.UUID;
//import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantechs.Licences.entities.LeService;
//import com.fasterxml.uuid.Generators;
import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.CreerLicencePayload;
import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.repositories.ProjetRepository;
import com.quantechs.Licences.repositories.ServiceRepository;

//import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;

import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.enumeration.StatusService;

//import jakarta.annotation.Generated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;

@Service
@AllArgsConstructor
public class LicenceService {
    @Autowired

    private final ServiceRepository serviceRepository;
    private LicenceRepository licenceRepository;
    private final ProjetRepository projetRepository;


    //Permet d'enregistrer un objet "licence dans le repository License"
    public Licence creerLicence(CreerLicencePayload creerLicencePayload) throws ServiceNonTrouverException {
        Licence licence = Licence.builder()
        .idService(creerLicencePayload.getIdService())
        .idProjet(creerLicencePayload.getIdProjet())
        .idUtilisateur(creerLicencePayload.getIdUtilisateur())
        .nomUtilisateur(creerLicencePayload.getNomUtilisateur())
        .idPaiement(creerLicencePayload.getIdPaiement()).build();
        //.dateExpiration(creerLicencePayload.getDateExpiration())
        //.cleLicence(creerLicencePayload.getCleLicence()).build();

        var serv = serviceRepository.findByidService(creerLicencePayload.getIdService());

        var numSer = serv.getNombreLicence();
        serv.setNombreLicence(numSer+1);
        serviceRepository.save(serv);

        //var getidService = licence.getIdService();
        boolean verfication = serviceRepository.existsById(creerLicencePayload.getIdService());
        var getidService = licence.getIdService();
        if(verfication)
        {
            //System.out.println("plein");
            licence.setIdService(getidService);
            licenceRepository.save(licence);
        }
        else{
            
            throw new ServiceNonTrouverException("L'ID du Service: "+getidService+" n'a pas été trouvé \u274C!");
        }

        LeService servPrix =  serviceRepository.findByidService(creerLicencePayload.getIdService());

        var prixserv = servPrix.getPrix();
        
        licence.setPrix(prixserv);
        

        boolean verifProjet = projetRepository.existsById(creerLicencePayload.getIdProjet());
        var getidProjet = licence.getIdProjet();
        if(verifProjet)
        {
            licence.setIdProjet(getidProjet);
            licenceRepository.save(licence);
        } else{
            throw new ServiceNonTrouverException("L'ID du Projet: "+getidProjet+" n'a pas été trouvé \u274C!");
        }

        var nomduServ = serviceRepository.findByidService(creerLicencePayload.getIdService());
        var nomService = nomduServ.getNomService();
        licence.setNomService(nomService);

        
        licence.setStatus(StatusLicence.ACTIF);

        LocalDate now = LocalDate.now();

        licence.setDateAchat(now);

        /* Setting ending date of licence */
        licenceRepository.save(licence);

        var startDate = licence.getDateAchat();

        var endDate = startDate.plusDays(30);

        licence.setDateExpiration(endDate);


        //UUID uuid = Generators.timeBasedGenerator().generate();
        //licence.setCleLicence(uuid);

        long jourValidite = ChronoUnit.DAYS.between(licence.getDateAchat(), licence.getDateExpiration());

        
        if(jourValidite > -1)
        {
            licence.setStatus(StatusLicence.ACTIF);
            licence.setValidite(jourValidite+" jour(s)");
        }
        else{
            licence.setStatus(StatusLicence.NONACTIF);
            licence.setValidite("Licence expiré!");
        }


        var LicenceActu = licenceRepository.findByidLicence(licence.getIdLicence());

        var idLicenceActu = LicenceActu.getIdLicence();
        var idLicenceService = LicenceActu.getIdService();
        var idLicenceProjet = LicenceActu.getIdProjet();
        var hash = idLicenceActu.hashCode();

        String etat;
        if(LicenceActu.getStatus()==StatusLicence.ACTIF)
        {
             etat = "1"; 
        }
        else{
             etat = "0";
        }

        String cle = idLicenceActu+"-"+idLicenceService+"-"+idLicenceProjet+"-"+hash+"-"+etat;
        licence.setCleLicence(cle);
        licenceRepository.save(licence);
        
            return licence;
    }

    public List<Licence> listerToutesLicences() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return licenceRepository.findAll();
    }

    public void supprimerToutesLicences() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        licenceRepository.deleteAll();
    }

    public Licence rechercheUneLicenceParId(String id) throws LicenceNonTrouverException
    {
        boolean verification = licenceRepository.existsById(id);

        Licence licence =  licenceRepository.findByidLicence(id);

        if(licence.getStatus()==StatusLicence.NONACTIF)
        {
            
            licence.getIdLicence() ;
        }
        

        if(verification)
        {
            //Licence licence = licenceRepository.findByidLicence(id);
            return licence;
        }
        else{
            throw new LicenceNonTrouverException("La licence avec pour ID: "+id+" n'a pas été trouvé \u274C!");
        }
    } 

    public String verifierLicenceParCle(String cleLicence) throws LicenceNonTrouverException
    {
        String[] partieCle = cleLicence.split("-");
        int t = partieCle.length;
        System.out.print("LA TAILLE DE LA PARTIECLE est: "+t);

        if(partieCle.length == 5)
        {
            String partCle1 = partieCle[0];
            boolean verification1 = licenceRepository.existsById(partCle1);

            String partCle2 = partieCle[1];
            boolean verification2 = serviceRepository.existsById(partCle2);

            String partCle3 = partieCle[2];
            boolean verification3 = projetRepository.existsById(partCle3);

            String partCle4 = partieCle[3];
            int partCle4ToInt = Integer.parseInt(partCle4);

            int hashLis = partCle1.hashCode() ;

        //String partcle5 = partieCle[4];

            Licence licence = licenceRepository.findBycleLicence(cleLicence);
             
        
            if(verification1 && verification2 && verification3 && (hashLis == partCle4ToInt) && (licence!=null))
            {   
                String msg = "La Licene avec pour clé: "+cleLicence+" est Valid \u2705";
                return msg;
            }
            else{
            String msg = "La Licence avec pour clé: "+cleLicence+" est Invalid \u274C!";
            return msg;
            }
        } 
        
        else{
            throw new LicenceNonTrouverException("La Licence avec pour clé: "+cleLicence+" ne respecte pas le format requis (taille different de 5) \u274C!");
        }
    }

    /*public void supprimerLicenceParId(String id)
    {
         licenceRepository.deleteById(id);
    }*/

    public Licence activerLicence(String idLicence) /*throws LicenceNonTrouverException*/
    {
        Licence licence = licenceRepository.findByidLicence(idLicence);
        licence.setStatus(StatusLicence.ACTIF);

        LeService service = serviceRepository.findByidService(licence.getIdService());
        service.setStatusService(StatusService.DISPONIBLE);
        serviceRepository.save(service);

        Projet projet = projetRepository.findByidProjet(licence.getIdProjet());
        projet.setStatusProjet(StatusProjet.ENCOURS);
        projetRepository.save(projet);

        LocalDate now = LocalDate.now();
        licence.setDateAchat(now);
        var startDate = licence.getDateAchat();
        var endDate = startDate.plusDays(30);
        licence.setDateExpiration(endDate);

        var serv = serviceRepository.findByidService(licence.getIdService());
        var numSer = serv.getNombreLicence();
        serv.setNombreLicence(numSer-1);
        serviceRepository.save(serv);

        var cleLicence = licence.getCleLicence();
        String[] partieCle = cleLicence.split("-");
        //int t = partieCle.length;
        partieCle[4] = "1";
        String part1 = partieCle[0];
        String part2 = partieCle[1];
        String part3 = partieCle[2];
        String part4 = partieCle[3];
        String cle = part1+"-"+part2+"-"+part3+"-"+part4+"-"+partieCle[4];

        licence.setCleLicence(cle);


        licenceRepository.save(licence);
        return licence;

        /*StatusLicence status = StatusLicence.ACTIF;
        if(rechercheUneLicenceParId(idLicence)!=null)
        {
            rechercheUneLicenceParId(idLicence).setStatus(status);
            
            return rechercheUneLicenceParId(idLicence); 
        }
        else 
        {
            throw new LicenceNonTrouverException("La licence avec pour ID: "+idLicence+" n'a pas été trouvé!");
        }*/  
    }

    public Licence changerEtatLicence(String idLicence, StatusLicence statusLicence)
    {
        Licence licence = licenceRepository.findByidLicence(idLicence);

        licence.setStatus(statusLicence);

        licenceRepository.save(licence);

       return licence;
    }

    public Licence desactiverLicence(String idLicence) /*throws LicenceNonTrouverException*/
    { 
        Licence licence = licenceRepository.findByidLicence(idLicence);
        licence.setStatus(StatusLicence.NONACTIF);
        
        /*var licenceService = serviceRepository.findByidService(licence.getIdService());
        licenceService.setStatusService(StatusService.NONDISPONIBLE);
        serviceRepository.save(licenceService); */

        /*var projetService = projetRepository.findByidProjet(licence.getIdProjet());
        projetService.setStatusProjet(StatusProjet.TERMINER);
        projetRepository.save(projetService);*/

        var cleLicence = licence.getCleLicence();
        String[] partieCle = cleLicence.split("-");
        //int t = partieCle.length;

        var serv = serviceRepository.findByidService(licence.getIdService());

        var numSer = serv.getNombreLicence();
        serv.setNombreLicence(numSer-1);
        serviceRepository.save(serv);

        String part1 = partieCle[0];
        String part2 = partieCle[1];
        String part3 = partieCle[2];
        String part4 = partieCle[3];
        partieCle[4] = "0";
        String cle = part1+"-"+part2+"-"+part3+"-"+part4+"-"+partieCle[4];

        licence.setCleLicence(cle);

        licence.setStatus(StatusLicence.NONACTIF);
        licenceRepository.save(licence);
        return licence;


        //StatusLicence status = StatusLicence.NONACTIF;
        /*if(rechercheUneLicenceParId(idLicence)!=null)
        {
            rechercheUneLicenceParId(idLicence).setStatus(StatusLicence.ACTIF);
            
            return rechercheUneLicenceParId(idLicence); 
        }
        else 
        {
            throw new LicenceNonTrouverException("La licence avec pour ID: "+idLicence+" n'a pas été trouvé!");
        }*/  
    }

    /*public UUID genererLicence()
    {   
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID key;
    }*/
    /*public List<Licence> rechercheUneLicenceParNom(String nom)
    {
        return licenceRepository.findBynomService(nom);
    }*/

    /*public List<Licence> rechercheParStatus(StatusLicence status)
    {
        return licenceRepository.findByStatus(status);
    }*/

    /*public Object activerLicence() {
        return null;
    }*/
}
