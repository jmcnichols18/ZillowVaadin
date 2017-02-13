/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archduke.zillowSearchResult;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.ui.Table;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.zillow.ZillowRealEstateService;
import zillow.realestateservice.searchresults.SearchResultsProperty;
import zillow.realestateservice.searchresults.Address;

/**
 *
 * @author mcpickem
 */
public class ZillowDeepSearch extends Table {

    private static final Logger logger = LoggerFactory.getLogger(ZillowDeepSearch.class);

    public ZillowDeepSearch(String address, String cityStateZip) throws JAXBException, IOException {
        super("Zillow Results Table");

        final BeanItemContainer<SearchResultsProperty> searchResults
                = new BeanItemContainer<>(SearchResultsProperty.class);

        String address1 = address.replace(" ", "+");
        String citystatezip1 = cityStateZip.replace(" ", "+").replace(",", "+");

        RestResponse result = ZillowRealEstateService.getDeepSearchResults(address1, citystatezip1);
        if (result.getDataAsObject(zillow.realestateservice.searchresults.Searchresults.class) instanceof zillow.realestateservice.searchresults.Searchresults) {
            zillow.realestateservice.searchresults.Searchresults resultObj = result.getDataAsObject(zillow.realestateservice.searchresults.Searchresults.class);

            List<SearchResultsProperty> myResults = resultObj.getResponse().getResults().getResult();

            searchResults.addAll(myResults);

            this.setContainerDataSource(searchResults);
            this.addStyleName("v-scrollable");

        }

    }

    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
        String val = "";
        logger.debug("THIS IS A TEST LOG! JDM");
        logger.info("formatPropertyValue: rowId: " + rowId);
        logger.info("formatPropertyValue: colId: " + (String) colId);
        logger.info("formatPropertyValue: property: " + property);
        logger.info("getItemIds: " + this.getItemIds());

        SearchResultsProperty row = (SearchResultsProperty) rowId;

        if (((String) colId).equalsIgnoreCase("address")) {
            Address addr = row.getAddress();
            val = addr.getStreet() + ", " + addr.getCity() + ", " + addr.getState() + " " + addr.getZipcode();
        } else if (((String) colId).equalsIgnoreCase("links")) {
            val = row.getLinks().getHomedetails() + "\n"
                    + row.getLinks().getMapthishome() + "\n"
                    + row.getLinks().getGraphsanddata();
        } else if (((String) colId).equalsIgnoreCase("lastSoldPrice")) {
            val = (row.getLastSoldPrice() == null) ? ""
                    : NumberFormat.getCurrencyInstance().format(row.getLastSoldPrice().getValue());
        } else if (((String) colId).equalsIgnoreCase("zestimate")) {
            val = (row.getZestimate() == null) ? ""
                    : NumberFormat.getCurrencyInstance().format(row.getZestimate().getAmount().getValue());
        } else if (((String) colId).equalsIgnoreCase("zpid")) {
            NumberFormat formatter = NumberFormat.getIntegerInstance();
            formatter.setGroupingUsed(false);
            formatter.format(row.getZpid());
        } else {
            val = super.formatPropertyValue(rowId, colId, property);
        }

        return val;
    }

}
