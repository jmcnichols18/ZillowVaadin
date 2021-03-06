/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archduke.zillowSearchResult;

import com.archduke.zillowvaadin.GoogleMapsUI;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.vaadin.haijian.PdfExporter;
import org.vaadin.haijian.ExcelExporter;


/**
 *
 * @author mcpickem
 */
public class ZillowSearchResultComponent extends CustomComponent {

    private final Logger logger =  LoggerFactory.getLogger(this.getClass());
     
    
    private String address;
    private String cityStateZip;
    /**
     *
     * @param address
     * @param cityStateZip
     * @throws JAXBException
     * @throws IOException
     */
    public ZillowSearchResultComponent(String address, String cityStateZip) 
            throws JAXBException, IOException, NoMatchException {
        this.address = address;
        this.cityStateZip = cityStateZip;
        initComponent();

    }
    
    private void initComponent()throws JAXBException, IOException, NoMatchException{
                ZillowDeepSearch searchResults;
        searchResults = new ZillowDeepSearch(address, cityStateZip);
        searchResults.setWidth(98, Sizeable.Unit.PERCENTAGE);
        
        searchResults.setColumnReorderingAllowed(true);
        searchResults.setColumnCollapsingAllowed(true);
                        
        Panel panel = new Panel("My Zillow Results");
        VerticalLayout panelContent = new VerticalLayout();
        HorizontalLayout exportPanel = new HorizontalLayout();
        
        ExcelExporter excelExporter = new ExcelExporter();
        excelExporter.setDateFormat("yyyy-MM-dd");
        excelExporter.setTableToBeExported(searchResults);
        excelExporter.setCaption("Export to Excel");
        excelExporter.setIcon(FontAwesome.DOWNLOAD);
        exportPanel.addComponent(excelExporter);
            
        PdfExporter pdfExporter = new PdfExporter(searchResults);
        pdfExporter.setWithBorder(true);
        pdfExporter.setCaption("Export to PDF");
        pdfExporter.setWithBorder(false);
        pdfExporter.setIcon(FontAwesome.DOWNLOAD);
        exportPanel.addComponent(pdfExporter);
        exportPanel.setSpacing(true);
        panelContent.addComponent(exportPanel);
        
        // Create an opener extension
        BrowserWindowOpener opener =
            new BrowserWindowOpener(GoogleMapsUI.class);
        opener.setFeatures("height=400,width=600,resizable");

        // Attach it to a button
        Button mapsButton = new Button("Google Map!", FontAwesome.MAP);
        opener.extend(mapsButton);
        exportPanel.addComponent(mapsButton);
        
        panelContent.setMargin(true); // Very useful
        panelContent.setSpacing(true);
        panel.setContent(panelContent);

        panelContent.addComponent(searchResults);
        panelContent.setExpandRatio(searchResults, 1.0f);
        panelContent.setSizeFull();
        panel.setSizeFull();
        setSizeFull();
                
        // The composition root MUST be set
        setCompositionRoot(panel);
    }

}
