/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archduke.zillowSearchResult;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author mcpickem
 */
public class ZillowSearchResultComponent extends CustomComponent {

    public ZillowSearchResultComponent(String address, String cityStateZip) {

        // A layout structure used for composition
        Panel panel = new Panel("My Zillow Results");
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setMargin(true); // Very useful
        panelContent.setSpacing(true);
        panel.setContent(panelContent);

        ZillowDeepSearch searchResults = new ZillowDeepSearch(address, cityStateZip);
        searchResults.setWidth(100, Sizeable.Unit.PERCENTAGE);
      
        panelContent.addComponent(searchResults);
        panelContent.setExpandRatio(searchResults, 1.0f);
        
        
        panelContent.setSizeFull();
        panel.setSizeFull();
        setSizeFull();
                
        // The composition root MUST be set
        setCompositionRoot(panel);
    }

}
