package expensetracker.aggregateservice.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

public interface GatewayDiscoveryService {

    default String getGatewayUrl(EurekaClient discoveryClient, String gatewayLocator) {

        InstanceInfo instance = discoveryClient.getNextServerFromEureka(gatewayLocator, false);
        return instance.getHomePageUrl();
    }
}
