///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.archduke.zillowvaadin;
//
//import com.archduke.zillowSearchResult.NoMatchException;
//import com.archduke.zillowSearchResult.ZillowSearchResultComponent;
//import javax.servlet.annotation.WebServlet;
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.data.Validator.InvalidValueException;
//import com.vaadin.data.validator.RegexpValidator;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.Notification.Type;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.bind.JAXBException;
//
//
///**
// * This UI is the application entry point. A UI may either represent a browser
// * window (or tab) or some part of a html page where a Vaadin application is
// * embedded.
// * <p>
// * The UI is initialized using {@link #init(VaadinRequest)}. This method is
// * intended to be overridden to add component to the user interface and
// * initialize non-component functionality.
// */
//@Theme("mytheme")
//public class UpdatedPropertyDetails extends UI {
//
//    private VerticalLayout layout = new VerticalLayout();
//    private ZillowSearchResultComponent zillowDataComponent;
//    int count  = 0;
//
//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//    	String defaultAddress = "536 empire blvd";
//    	String defaultCityStateZip = "rochester, ny 14609";
//    	if(vaadinRequest.getParameterMap().containsKey("address") 
//                && vaadinRequest.getParameterMap().containsKey("cityStateZip") ){
//    		defaultAddress = (String)vaadinRequest.getParameter("address");
//    		defaultCityStateZip = (String)vaadinRequest.getParameter("cityStateZip");
//    	} 
//    	 
//        final TextField address = new TextField("Enter your Street Address here:",
//                defaultAddress);
//        final TextField cityStateZip = new TextField("Enter your City State and Zip here:",
//                defaultCityStateZip);
//
//        address.setIcon(FontAwesome.HOME);
//        address.setRequired(true);
//        cityStateZip.setRequired(true);
//        address.addValidator(new RegexpValidator("^[0-9]+\\s[A-Za-z0-9'\\.\\-\\s\\,#]+",
//                 "Invalid Address entered."));
//        cityStateZip.addValidator(new RegexpValidator("^[A-z\\s]+[,]?\\s[A-z]{2}[,]?\\s[0-9]{5}",
//                 "Invalid City State and Zip entered."));
//
//        Button searchButton = new Button("Search Zillow", FontAwesome.SEARCH);
//        searchButton.addClickListener((Button.ClickEvent e) -> {
//
//            if (layout.getComponentIndex(zillowDataComponent) > 0)
//                layout.removeComponent(zillowDataComponent);
//
//            address.setValidationVisible(false);
//            cityStateZip.setValidationVisible(false);
//            try {
//                address.validate();
//                cityStateZip.validate();
//                zillowDataComponent = new ZillowSearchResultComponent(address.getValue(), 
//                        cityStateZip.getValue());
//                layout.addComponent(zillowDataComponent);
//            } catch (InvalidValueException ex) {
//                Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
//                address.setValidationVisible(true);
//                cityStateZip.setValidationVisible(true);
//            } catch (JAXBException | IOException | NoMatchException ex) {
//                Logger.getLogger(UpdatedPropertyDetails.class.getName()).log(Level.SEVERE, null, ex);
//                Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
//            } 
//        });
//
//        layout.addComponents(address, cityStateZip, searchButton);
//        layout.setMargin(true);
//        layout.setSpacing(true);
//
//        setContent(layout);
//        
//        searchButton.click();
//    }
//
//    /**
//     *
//     */
//    @WebServlet(urlPatterns = "/UpdatedPropertyDetails", name = "UpdatedPropertyDetailsServlet",
//            displayName = "UpdatedPropertyDetailsServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = UpdatedPropertyDetails.class, productionMode = false,
//            widgetset = "com.archduke.zillowSearchResult.AppWidgetSet")
//    public static class UpdatedPropertyDetailsServlet extends VaadinServlet {
//    }
//
//}
