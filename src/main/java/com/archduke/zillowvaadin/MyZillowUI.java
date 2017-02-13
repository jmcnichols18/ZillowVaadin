package com.archduke.zillowvaadin;

import com.archduke.zillowSearchResult.ZillowSearchResultComponent;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyZillowUI extends UI {

    private final VerticalLayout layout = new VerticalLayout();
    private ZillowSearchResultComponent zillowDataComponent;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final TextField address = new TextField("Enter your Street Address here:",
                "536 Empire Blvd");
        final TextField cityStateZip = new TextField("Enter your City State and Zip here:", 
                "Rochester, NY 14609");

        address.setIcon(FontAwesome.HOME);
        address.setRequired(true);
        cityStateZip.setRequired(true);
        address.addValidator(new RegexpValidator("^[0-9]+\\s[A-Za-z0-9'\\.\\-\\s\\,#]+"
                , "Invalid Address entered."));
        cityStateZip.addValidator(new RegexpValidator("^[A-z\\s]+[,]?\\s[A-z]{2}[,]?\\s[0-9]{5}"
                , "Invalid City State and Zip entered."));

        Button button = new Button("Search Zillow", FontAwesome.SEARCH);
        
        button.addClickListener((Button.ClickEvent e) -> {
            address.setValidationVisible(false);
            cityStateZip.setValidationVisible(false);
            try {
                address.validate();
                cityStateZip.validate();
                zillowDataComponent = new ZillowSearchResultComponent(address.getValue(), cityStateZip.getValue());
                layout.addComponent(zillowDataComponent);
            } catch (InvalidValueException ex) {
                Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
                address.setValidationVisible(true);
                cityStateZip.setValidationVisible(true);                  
            } catch (JAXBException | IOException ex) {
                Logger.getLogger(MyZillowUI.class.getName()).log(Level.SEVERE, null, ex);
                Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
            }
        });

        layout.addComponents(address, cityStateZip, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    /**
     *
     */
    @WebServlet(urlPatterns = "/*", name = "MyZillowUIServlet", 
             displayName = "MyZillowUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyZillowUI.class, productionMode = false, 
            widgetset = "com.archduke.zillowSearchResult.AppWidgetSet")
    public static class MyZillowUIServlet extends VaadinServlet {
    }

}
