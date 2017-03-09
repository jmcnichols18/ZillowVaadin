/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.zillow;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * ZillowRealEstateService Service
 *
 * @author mcpickem
 */
public class ZillowRealEstateService {

    /**
     * Creates a new instance of ZillowRealEstateService
     */
    public ZillowRealEstateService() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param address
     * @param citystatezip
     * @return an instance of RestResponse
     */
    public static RestResponse getDeepSearchResults(String address, String citystatezip) throws IOException {
        String apiKey = ZillowRealEstateServiceAuthenticator.getApiKey();
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"zws-id", "" + apiKey + ""}, {"address", address}, {"citystatezip", citystatezip}};
        RestConnection conn = new RestConnection("http://www.zillow.com/webservice/GetDeepSearchResults.htm", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }

    /**
     *
     * @param zpid
     * @param count
     * @return an instance of RestResponse
     */
    public static RestResponse getDeepComps(String zpid, String count) throws IOException {
        String apiKey = ZillowRealEstateServiceAuthenticator.getApiKey();
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"zws-id", "" + apiKey + ""}, {"zpid", zpid}, {"count", count}};
        RestConnection conn = new RestConnection("http://www.zillow.com/webservice/GetDeepComps.htm", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
