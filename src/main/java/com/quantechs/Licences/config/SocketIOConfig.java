package com.quantechs.Licences.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIOConfig {
    @Value("${socket-server.host}")
    private String host;
    @Value("${socket-server.port}")
    private Integer port;
    @Value("${socket-server.bossCount}")
    private Integer bossCount;
    @Value("${socket-server.workCount}")
    private Integer workCount;
    @Value("${socket-server.upgradeTimeOut}")
    private Integer upgradeTimeOut;
    @Value("${socket-server.pingTimeout}")
    private Integer pingTimeout;
    @Value("${socket-server.pingInternal}")
    private Integer pingInternal;

    @Bean
    public SocketIOServer socketIOServer(){
     com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
     config.setHostname(host);
     config.setPort(port);
     config.setBossThreads(bossCount);
     config.setWorkerThreads(workCount);
     config.setAllowCustomRequests(true);
     config.setUpgradeTimeout(upgradeTimeOut);
     config.setPingTimeout(pingTimeout);
     config.setPingInterval(pingInternal);
     return new SocketIOServer(config);
    }

}
