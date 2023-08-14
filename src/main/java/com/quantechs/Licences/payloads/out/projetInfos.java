package com.quantechs.Licences.payloads.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class projetInfos {
    private String id;
    private String projectName;
    private String siteId;
    private String apiKey;
    private String stateProjet;
    private String notify_url;
    private String return_url;
    private String channelPayment;
}

