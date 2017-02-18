package com.archduke.zillowSearchResult;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import com.vaadin.ui.Table;
import java.io.IOException;
import java.text.NumberFormat;

import java.util.List;
import javax.xml.bind.JAXBException;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.zillow.ZillowRealEstateService;
import zillow.realestateservice.searchresults.SearchResultsProperty;
import zillow.realestateservice.searchresults.Searchresults;
import zillow.realestateservice.searchresults.Address;

/**
 * Zillow DeepSearchResults Table
 *
 * @author mcpickem
 */
public class ZillowDeepSearch extends Table {

    private final Logger logger = LoggerFactory.getLogger(ZillowDeepSearch.class);

    private final BeanItemContainer<SearchResultsProperty> searchResults
            = new BeanItemContainer<>(SearchResultsProperty.class);

    /**
     *
     * @param address
     * @param cityStateZip
     * @throws JAXBException
     * @throws IOException
     */
    public ZillowDeepSearch(String address, String cityStateZip) throws JAXBException, IOException, NoMatchException {
        super("Zillow DeepSearchResults Table");
        address = address.replace(" ", "+");
        cityStateZip = cityStateZip.replace(" ", "+").replace(",", "+");
        RestResponse result = ZillowRealEstateService.getDeepSearchResults(address, cityStateZip);

        if (result.getDataAsObject(Searchresults.class) instanceof Searchresults) {
            Searchresults resultObj = result.getDataAsObject(Searchresults.class);
            if (resultObj.getMessage().getCode().intValue() != 0) {
                logger.error(resultObj.getMessage().getText());
                throw new NoMatchException(resultObj.getMessage().getText());
            }
            List<SearchResultsProperty> myResults = resultObj.getResponse().getResults().getResult();
            searchResults.addAll(myResults);

            this.setContainerDataSource(searchResults);
            this.setPageLength(this.size());
            this.addStyleName("h-scrollable");

            this.setVisibleColumns(
                    "zestimate",
                    "taxAssessmentYear",
                    "taxAssessment",
                    "lastSoldDate",
                    "lastSoldPrice",
                    /* */
                    "bedrooms",
                    "bathrooms",
                    "finishedSqFt",
                    "useCode",
                    "lotSizeSqFt",
                    "yearBuilt" /*,
                    
                    "links",

                    "zpid" ,
                   // "fipSCounty",
                    "address",
                    "localRealEstate" */
            );
        }
    }

    @Override
    public boolean isColumnReorderingAllowed() {
        return true;
    }

    @Override
    public boolean isColumnCollapsingAllowed() {
        return true;
    }

    @Override
    public Resource getColumnIcon(Object propertyId) {
        return FontAwesome.LINK;
    }
   
    /**
     * @param rowId
     * @param colId
     * @param property
     * @return formated string
     */
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
        String val = "";
       // logger.info("formatPropertyValue JDM " + property.getClass() + " "
       //         + property.getType() + ":" + property.getValue());

        if (property.getValue() != null) {
            SearchResultsProperty row = (SearchResultsProperty) rowId;
            String col = (String) colId;
            if (col.equalsIgnoreCase("address")) {
                Address addr = row.getAddress();
                val = addr.getStreet() + ", " + addr.getCity() + ", "
                        + addr.getState() + " " + addr.getZipcode();
            } else if (col.equalsIgnoreCase("links")) {
                val = row.getLinks().getHomedetails()
                        + row.getLinks().getMapthishome()
                        + row.getLinks().getGraphsanddata();
            } else if (col.equalsIgnoreCase("lastSoldPrice")) {
                val = (row.getLastSoldPrice() == null) ? ""
                        : NumberFormat.getCurrencyInstance().format(row.getLastSoldPrice().getValue());
            } else if (col.equalsIgnoreCase("zestimate")) {
                val = (row.getZestimate() == null) ? ""
                        : NumberFormat.getCurrencyInstance().format(row.getZestimate().getAmount().getValue());
            } else if (col.equalsIgnoreCase("taxAssessment")) {
                val = (row.getTaxAssessment() == null) ? ""
                        : NumberFormat.getCurrencyInstance().format(new Double(row.getTaxAssessment()));
            } else if (col.equalsIgnoreCase("zpid")) {
                val = Long.toString(row.getZpid());
            } else {
                val = super.formatPropertyValue(rowId, colId, property);
            }
        }
        return val;
    }

}


