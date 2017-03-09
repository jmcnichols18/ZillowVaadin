/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archduke.zillowvaadin;

/**
 *
 * @author mcpickem
 */
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;

import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Logger;


@Theme("mytheme")
@Title("Google Maps Servlet")
@SuppressWarnings("serial")
public class GoogleMapsUI extends UI {

//    @WebServlet(urlPatterns = "/GoogleMapsUI", name = "GoogleMapsUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(productionMode = false, ui = GoogleMapsUI.class,
//            widgetset = "com.archduke.zillowSearchResult.AppWidgetSet")
//    public static class GoogleMapsUIServlet extends VaadinServlet {
//    }

    private static Logger logger = Logger.getLogger(GoogleMapsUI.class.getName());

    VerticalLayout layout; 
            


    @Override
    protected void init(VaadinRequest request) {

        layout = new VerticalLayout();


        layout.setSizeFull();
        String apiKey = "AIzaSyCREOJBujV2gxMeoLCPnz6XxBHJSElGKX4";

        GoogleMap googleMap = new GoogleMap(apiKey, null, null);

        googleMap.setCenter(new LatLon(43.177,-77.5444077));
        googleMap.setZoom(18);
        googleMap.setSizeFull();

        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(20);



        layout.addComponent(googleMap);
        layout.setExpandRatio(googleMap, 1.0f);
        layout.addComponent(new Button("Close Window", event -> {// Java 8
            // Close the popup
            JavaScript.eval("close()");

            // Detach the UI from the session
            getUI().close();
        }));
        setContent(layout);
        
       
    }


}
