package com.quantechs.Licences.services;

import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonObject;
import com.quantechs.Licences.Utils.HashGenerator;
import com.quantechs.Licences.Utils.StringToLocalDateConverter;
import com.quantechs.Licences.entities.LeService;
import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
import com.quantechs.Licences.exceptions.ActivationLicencePaiementException;
import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.exceptions.LicenceNonCreerException;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.exceptions.VerificationPaiementKeyException;
import com.quantechs.Licences.payloads.in.ActiverLicenceFromPaiement;
import com.quantechs.Licences.payloads.in.CreerLicencePayload;
import com.quantechs.Licences.payloads.in.InitialiserPaiement;
import com.quantechs.Licences.payloads.out.ResponseLicence;
import com.quantechs.Licences.payloads.out.paiementInfos;
import com.quantechs.Licences.payloads.out.verificationLicenceOutput;
import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.repositories.ProjetRepository;
import com.quantechs.Licences.repositories.ServiceRepository;
import lombok.AllArgsConstructor;

import com.quantechs.Licences.enumeration.MoyenPaiement;
import com.quantechs.Licences.enumeration.QCurrency;
import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.enumeration.StatusService;

@Service
@AllArgsConstructor
public class LicenceService {
    @Autowired
    private final CommunicationUtils communicationUtils;
    private final ServiceRepository serviceRepository;
    private LicenceRepository licenceRepository;
    private final ProjetRepository projetRepository;




    final String URL =  "http://127.0.0.1:8100/Licences/projet";


    //WebClient client = WebClient.create(URL);

    //Permet d'enregistrer un objet "licence dans le repository License"
    public Licence AcheterLicence(CreerLicencePayload creerLicencePayload) throws ServiceNonTrouverException, EnumerationNotFoundException, PaiementNonEffectueException, LicenceNonCreerException, ProjetNonTrouverException, NoSuchAlgorithmException {
        
        LeService serv = serviceRepository.findById(creerLicencePayload.getIdService()).get();
        boolean verifService = serviceRepository.existsById(creerLicencePayload.getIdService());
        var checkServ = serv.getStatusService(); 

        boolean verifProjet = projetRepository.existsById(creerLicencePayload.getIdProjet());
        Projet projet = projetRepository.findById(creerLicencePayload.getIdProjet()).get();
        var checkProjet = projet.getStatusProjet();
        
        if(verifService) //&& verifProjet && checkServ == StatusService.DISPONIBLE && checkProjet == StatusProjet.ENCOURS)
        {
            if(verifProjet)
            {
                if (checkServ == StatusService.DISPONIBLE)
                {
                    if(checkProjet == StatusProjet.ENCOURS)
                    {
                        Licence licence = Licence.builder()
                        .idService(creerLicencePayload.getIdService())
                        .idProjet(creerLicencePayload.getIdProjet())
                        .idUtilisateur(creerLicencePayload.getIdUtilisateur())
                        .nomUtilisateur(creerLicencePayload.getNomUtilisateur()).build();
                        //.description(creerLicencePayload.getDescription())
                        //.idPaiement(creerLicencePayload.getIdPaiement()).build();
                        //.dateExpiration(creerLicencePayload.getDateExpiration())
                        //.cleLicence(creerLicencePayload.getCleLicence()).build();
                        var numSer = serv.getNombreLicence();
                        serv.setNombreLicence(numSer+1);
                        serviceRepository.save(serv);
                        licence.setDescription(serv.getDescription());
                        licence.setNomService(serv.getNomService());
                        licence.setAccronymeService(serv.getAccronymeService());
                        licence.setStatus(StatusLicence.NON_ACTIF);
                        licence.setIdProjet(creerLicencePayload.getIdProjet());
                        licence.setIdService(creerLicencePayload.getIdService());

                        licenceRepository.save(licence);

                        Licence LicenceActu = licenceRepository.findById(licence.getIdLicence()).get();
                        var idLicenceActu = LicenceActu.getIdLicence();
                        var idLicenceService = LicenceActu.getIdService();
                        var idLicenceProjet = LicenceActu.getIdProjet();
                        //var hash = idLicenceActu.hashCode();
                        var hash = HashGenerator.generateHash(idLicenceActu);
                        var serviceAccro = serv.getAccronymeService();

                        String etat;
                        if(LicenceActu.getStatus() == StatusLicence.ACTIF)
                        {
                            etat = "1"; 
                        }
                        else
                        {
                            etat = "0";
                        }

                        String cle = idLicenceActu+"-"+idLicenceService+"-"+idLicenceProjet+"-"+hash+"-"+serviceAccro+"-"+etat;
                        licence.setCleLicence(cle);
                        licenceRepository.save(licence);

                         InitialiserPaiement initialiserPaiement = InitialiserPaiement.builder()
                        .idService(licence.getIdService())
                        .idProjet(serv.getIdPaiementProjet())
                        .qcurrency(QCurrency.XAF)
                        .moyenPaiement(MoyenPaiement.OM)
                        .idClient(licence.getIdUtilisateur())
                        .montant(serv.getMontant())
                        .description(licence.getDescription()).build();
                        System.out.println(initialiserPaiement);
                        String responseInitPayment = communicationUtils.initialiserPaiementProvider(initialiserPaiement.validation());
                        JsonObject jsonObject = Streams.parse(new JsonReader(new StringReader(responseInitPayment))).getAsJsonObject();
                        if(jsonObject.get("code").getAsString().equals("200"))
                        {
                            JsonObject data = jsonObject.get("data").getAsJsonObject();
                            paiementInfos pInfos =  paiementInfos.builder()
                            .id(data.get("id").getAsString())
                            .idService(data.get("idService").getAsString())
                            .idCleint(data.get("idCleint").getAsString())
                            .idprojet(data.get("idprojet").getAsString())
                            .montant(data.get("montant").getAsInt())
                            .description(data.get("description").getAsString())
                            .moyenPaiement(data.get("moyenPaiement").getAsString())
                            .qCurrency(data.get("qcurrency").getAsString())
                            .paiementkey(data.get("paiementkey").getAsString())
                            .datePaiement(data.get("datePaiement").getAsString())
                            .statusPaiement(data.get("statusPaiement").getAsString())
                            .paymentUrl(data.get("paymentUrl").getAsString()).build();
                            
                            licence.setMontant(pInfos.getMontant());
                            licence.setStatusPaiement(pInfos.getStatusPaiement());
                            licence.setPaiementKey(pInfos.getPaiementkey());
                            licence.setPaiementUrl(pInfos.getPaymentUrl());
                            licence.setQCurrency(pInfos.getQCurrency());
                            //licence.setIdUtilisateur(pInfos.getIdCleint());
                            licence.setDateAchat(StringToLocalDateConverter.convertStringToLocalDate(pInfos.getDatePaiement()));
                            licenceRepository.save(licence);
                            return licence;
                        }
                        else
                        {
                            throw new PaiementNonEffectueException("Le paiement n'a pas pu etre effectueee");
                        }
                        
                    }
                    else
                    {
                        throw new ProjetNonTrouverException("Le Projet avec id "+creerLicencePayload.getIdProjet()+" n'est pas ACTIF");
                    }
                
                }
                else
                {
                    throw new ServiceNonTrouverException("Le Service avec id "+creerLicencePayload.getIdService()+" n'est pas DISPONIBLE");
                }

            }
            else
            {
                throw new ProjetNonTrouverException("Le projet avec id "+creerLicencePayload.getIdProjet()+" est n'existe pas!");
            }
        }
        else
        {
            
            throw new ServiceNonTrouverException("L'ID du Service: "+creerLicencePayload.getIdService()+" n'a pas été trouvé \u274C!");
        }
     
        
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

        if(licence.getStatus()==StatusLicence.NON_ACTIF)
        {
            
            licence.getIdLicence() ;
        }
        

        if(verification)
        {
            return licence;
        }
        else{
            throw new LicenceNonTrouverException("La licence avec pour ID: "+id+" n'a pas été trouvé \u274C!");
        }
    }

    public ResponseLicence verifierLicence(String cleLicence) throws LicenceNonTrouverException, VerificationPaiementKeyException, LicenceNonCreerException, NoSuchAlgorithmException
    {
        verificationLicenceOutput verif = new verificationLicenceOutput();
        boolean verifierExiste = licenceRepository.existsBycleLicence(cleLicence);
        Licence licence = licenceRepository.findBycleLicence(cleLicence);

        if(verifierExiste)
        {
          boolean verif1 = verifierLicenceParCle(cleLicence);
          String status = communicationUtils.veriferPaiementLicence(licence.getPaiementKey());
          System.out.println(verif1);
          System.out.println(status);
          if((status.equals("SUCCES")) && verif1 && licence.getStatus().equals(StatusLicence.ACTIF))
          {
            verif.setResult(true);
            verif.setClassSecondaire(licence.getAccronymeService());
            System.out.println("***** "+verif);
            return new ResponseLicence(200, "Verification Effectué!", verif);
          }
          else
          {
            verif.setResult(false);
            verif.setClassSecondaire(licence.getAccronymeService());
            System.out.println("***** "+verif);
            return new ResponseLicence(200, "Verification Effectué!", verif);
            //throw new VerificationPaiementKeyException("La licence avec le paiementKey "+licence.getPaiementKey()+" n'est pas valide!");
          }
        }
        else
        {
            throw new LicenceNonCreerException("La licence avec cle "+cleLicence+" n'existe pas");
        }
    }

    private boolean verifierLicenceParCle(String cleLicence) throws LicenceNonTrouverException, NoSuchAlgorithmException
    {
        String[] partieCle = cleLicence.split("-");
        //int t = partieCle.length;
        //System.out.print("LA TAILLE DE LA PARTIECLE est: "+t);
        Licence licence = licenceRepository.findBycleLicence(cleLicence);
        LeService ser = serviceRepository.findById(licence.getIdService()).get();

        if(partieCle.length == 6)
        {
            String partCle1 = partieCle[0];
            boolean verification1 = licenceRepository.existsById(partCle1);
            

            String partCle2 = partieCle[1];
            boolean verification2 = serviceRepository.existsById(partCle2);
            

            String partCle3 = partieCle[2];
            boolean verification3 = projetRepository.existsById(partCle3);
            

            String partCle4 = partieCle[3];
            System.out.println(partCle4);

            
            boolean verification4 = ser.getAccronymeService().equals(licence.getAccronymeService());
            System.out.println(verification4);


            //int hashLis = partCle1.hashCode() ;
            var hashLis = HashGenerator.generateHash(partCle1);
            System.out.println(hashLis);
            
        
            if(verification1 && verification2 && verification3 && (hashLis.equals(partCle4)) && (licence!=null) && verification4)
            {   
                //String msg = "La Licene avec pour clé: "+cleLicence+" est Valid \u2705";
                return true;
            }
            else{
            //String msg = "La Licence avec pour clé: "+cleLicence+" est Invalid \u274C!";
            return false;
            }
        } 
        
        else{
            throw new LicenceNonTrouverException("La Licence avec pour clé: "+cleLicence+" ne respecte pas le format requis (taille different de 6) \u274C!");
        }
    }

    public ResponseLicence activerLicenceParPaiementKey(ActiverLicenceFromPaiement activerLicenceFromPaiement) throws ActivationLicencePaiementException

    {
        Licence licence = licenceRepository.findByPaiementKey(activerLicenceFromPaiement.getPaiementKey());
        if(licence != null)
        {
            LocalDate maintenant = LocalDate.now();

            licence.setDateActivation(maintenant);
            //var startDate = licence.getDateAchat();
            var endDate = maintenant.plusDays(30);
            licence.setDateExpiration(endDate);

            long jourValidite = ChronoUnit.DAYS.between(licence.getDateActivation(), licence.getDateExpiration());
        
            if(jourValidite > -1)
            {
                licence.setStatus(StatusLicence.ACTIF);
                licence.setStatusPaiement(activerLicenceFromPaiement.getPaiementStatus());
                licence.setValidite(jourValidite+" jour(s)");

                var cleLicence = licence.getCleLicence();
                String[] partieCle = cleLicence.split("-");
                partieCle[5] = "1";
                String part1 = partieCle[0];
                String part2 = partieCle[1];
                String part3 = partieCle[2];
                String part4 = partieCle[3];
                String part5 = partieCle[4];
                String cle = part1+"-"+part2+"-"+part3+"-"+part4+"-"+part5+"-"+partieCle[5];

                licence.setCleLicence(cle);

                licenceRepository.save(licence);
                return new ResponseLicence(200, "Activer avec succes", licence);
            }
            else
            {
                licence.setStatus(StatusLicence.NON_ACTIF);
                licence.setValidite("Licence expiré!");
                licenceRepository.save(licence);
                return new ResponseLicence(400, "Echec d'activation", "Veuillez reessayer!");
            }
            //String msg =  "La Licence dont le paiementKey est "+paiementKey+" a été activée avec succès!";
            
        }
        else
        {
            throw new ActivationLicencePaiementException("La Licence dont le paiementKey est "+activerLicenceFromPaiement.getPaiementKey()+" n'existe pas!");
        }
    }

    public List<Licence> rechercherParNumeroEtStatusActif(String idUtilisateur) throws LicenceNonTrouverException
    {
        var licences = licenceRepository.findAll();
        List<Licence> lesLicences = new ArrayList<>();

        for (Licence licence : licences) {
            if(licence.getIdUtilisateur().equals(idUtilisateur))
            {
                lesLicences.add(licence);
            }
        }
        //boolean isEmpty = lesLicences.size() == 0;

        return lesLicences.stream()
        .filter(licence -> licence.getStatus() == StatusLicence.ACTIF)
        .collect(Collectors.toList()); 
          
    }

    public Licence activerLicence(String idLicence) 
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
        licenceRepository.save(licence);
        var startDate = licence.getDateAchat();
        var endDate = startDate.plusDays(30);
        licence.setDateExpiration(endDate);

        var serv = serviceRepository.findByidService(licence.getIdService());
        var numSer = serv.getNombreLicence();
        serv.setNombreLicence(numSer+1);
        serviceRepository.save(serv);

        var cleLicence = licence.getCleLicence();
        String[] partieCle = cleLicence.split("-");

        partieCle[5] = "1";
            String part1 = partieCle[0];
            String part2 = partieCle[1];
            String part3 = partieCle[2];
            String part4 = partieCle[3];
            String part5 = partieCle[4];
            String cle = part1+"-"+part2+"-"+part3+"-"+part4+"-"+part5+"-"+partieCle[5];

        licence.setCleLicence(cle);


        licenceRepository.save(licence);
        return licence;

   
    }

    public Licence changerEtatLicence(String idLicence, StatusLicence statusLicence)
    {
        Licence licence = licenceRepository.findByidLicence(idLicence);

        licence.setStatus(statusLicence);

        licenceRepository.save(licence);

       return licence;
    }

    public Licence desactiverLicence(String idLicence)
    { 
        Licence licence = licenceRepository.findByidLicence(idLicence);
        licence.setStatus(StatusLicence.NON_ACTIF);
    

        var cleLicence = licence.getCleLicence();
        String[] partieCle = cleLicence.split("-");


        var serv = serviceRepository.findByidService(licence.getIdService());

        var numSer = serv.getNombreLicence();
        serv.setNombreLicence(numSer-1);
        serviceRepository.save(serv);

        String part1 = partieCle[0];
        String part2 = partieCle[1];
        String part3 = partieCle[2];
        String part4 = partieCle[3];
        String part5 = partieCle[4];
        String cle = part1+"-"+part2+"-"+part3+"-"+part4+"-"+part5+"-"+partieCle[5];

        licence.setCleLicence(cle);

        licence.setStatus(StatusLicence.NON_ACTIF);
        licenceRepository.save(licence);
        return licence;

    }


}
