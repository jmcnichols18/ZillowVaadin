/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archduke.zillowSearchResult;

import com.vaadin.server.Sizeable;
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

    public ZillowSearchResultComponent(String address, String cityStateZip) 
            throws JAXBException, IOException {
        
        ZillowDeepSearch searchResults;
        searchResults = new ZillowDeepSearch(address, cityStateZip);
        searchResults.setWidth(100, Sizeable.Unit.PERCENTAGE);
        Panel panel = new Panel("My Zillow Results");
        VerticalLayout panelContent = new VerticalLayout();
        HorizontalLayout exportPanel = new HorizontalLayout();
        
        ExcelExporter excelExporter = new ExcelExporter();
        excelExporter.setDateFormat("yyyy-MM-dd");
        excelExporter.setTableToBeExported(searchResults);
        excelExporter.setCaption("Export to Excel");
        exportPanel.addComponent(excelExporter);
            
        PdfExporter pdfExporter = new PdfExporter(searchResults);
        pdfExporter.setCaption("Export to PDF");
        pdfExporter.setWithBorder(false);
        exportPanel.addComponent(pdfExporter);
        exportPanel.setSpacing(true);
        panelContent.addComponent(exportPanel);
        
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
