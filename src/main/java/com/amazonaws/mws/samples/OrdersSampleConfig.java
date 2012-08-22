/*
 * The following is the updated info for connecting to the MWS service.
 * 
 * 
 * Seller account identifiers for NewOldSounds 
 * Merchant ID: A2O3RFX1T286C4 
 * Marketplace ID: ATVPDKIKX0DER 
 * Developer account identifier and credentials for developer account number 0055-2395-2022AWS 
 * Access Key ID: AKIAILR3PNJ7PCGMYXPA 
 * Secret Key: hOMHACs564Q4XCmEr6SC+0Xg0PUlniaToJvxMErh
 * 
 */

package com.amazonaws.mws.samples;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.mws.MarketplaceWebServiceOrdersConfig;
import com.amazonaws.mws.model.orders.MarketplaceIdList;

final public class OrdersSampleConfig {

	/************************************************************************
	 * Set Access Key ID, Secret Acess Key ID, Seller ID, etc.
	 ***********************************************************************/
	public static final String accessKeyId = "AKIAILR3PNJ7PCGMYXPA";
	public static final String secretAccessKey = "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";
	public static final String applicationName = "OfBiz Amazon Integration";
	public static final String applicationVersion = "1";

	public static final String sellerId = "A2O3RFX1T286C4";

	private static List<String> marketplaceIdArrayList = new ArrayList<String>();
	public static MarketplaceIdList marketplaceIdList = null;

	public static MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();

	static {
		/**************************************************************
		 * Add marketplaceIds as required and set to the request object.
		 **************************************************************/
		// marketplaceIdArrayList.add("<MarketplaceID1>");
		marketplaceIdArrayList.add("ATVPDKIKX0DER");
		marketplaceIdList = new MarketplaceIdList(marketplaceIdArrayList);

		/************************************************************************
		 * Uncomment to set the appropriate MWS endpoint.
		 ************************************************************************/
		// US
		config.setServiceURL("https://mws.amazonservices.com/Orders/2011-01-01");
		// UK
		// config.setServiceURL("https://mws.amazonservices.co.uk/Orders/2011-01-01");
		// Germany
		// config.setServiceURL("https://mws.amazonservices.de/Orders/2011-01-01");
		// France
		// config.setServiceURL("https://mws.amazonservices.fr/Orders/2011-01-01");
		// Italy
		// config.setServiceURL("https://mws.amazonservices.it/Orders/2011-01-01");
		// Japan
		// config.setServiceURL("https://mws.amazonservices.jp/Orders/2011-01-01");
		// China
		// config.setServiceURL("https://mws.amazonservices.com.cn/Orders/2011-01-01");
		// Canada
		// config.setServiceURL("https://mws.amazonservices.ca/Orders/2011-01-01");
	}

	/************************************************************************
	 * You can also try advanced configuration options. Available options are:
	 * 
	 * - Signature Version - Proxy Host and Proxy Port - User Agent String to be
	 * sent to Marketplace Web Service
	 *************************************************************************/

}
