package com.archduke.zillowvaadin;

import com.archduke.zillowSearchResult.ZillowSearchResultComponent;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyZillowUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField address = new TextField();
        address.setCaption("Type your address here:");
        final TextField cityStateZip = new TextField();
        cityStateZip.setCaption("Type your City State and Zip here:");
        
        Button button = new Button("Search Zillow");
        button.addClickListener( e -> {
            layout.addComponent(new ZillowSearchResultComponent(address.getValue(), cityStateZip.getValue()) );
        });
        
        
        
        
        layout.addComponents(address, cityStateZip, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyZillowUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyZillowUI.class, productionMode = false, widgetset = "com.archduke.zillowSearchResult.AppWidgetSet")
    public static class MyZillowUIServlet extends VaadinServlet {
    }
}
