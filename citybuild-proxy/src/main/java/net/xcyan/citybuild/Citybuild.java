package net.xcyan.citybuild;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.xcyan.citybuild.communication.Communication;
import org.slf4j.Logger;

@Plugin(id = "citybuild",
        name = "citybuild",
        version = "1.0")
public class Citybuild {

    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public Citybuild(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;

        //new Communication(this).startServer();
    }

    public ProxyServer proxyServer() {
        return proxyServer;
    }

    public Logger logger() {
        return logger;
    }
}
