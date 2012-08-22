/******************************************************************************* 
 *  Copyright 2008-2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *    __  _    _  ___ 
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 * 
 *  Marketplace Web Service Orders Java Library
 *  API Version: 2011-01-01
 *  Generated: Sun Feb 06 00:05:37 UTC 2011 
 * 
 */



package com.amazonaws.mws.mock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

import com.amazonaws.mws.MarketplaceWebServiceOrders;
import com.amazonaws.mws.MarketplaceWebServiceOrdersException;
import com.amazonaws.mws.model.orders.GetOrderRequest;
import com.amazonaws.mws.model.orders.GetOrderResponse;
import com.amazonaws.mws.model.orders.GetServiceStatusRequest;
import com.amazonaws.mws.model.orders.GetServiceStatusResponse;
import com.amazonaws.mws.model.orders.ListOrderItemsByNextTokenRequest;
import com.amazonaws.mws.model.orders.ListOrderItemsByNextTokenResponse;
import com.amazonaws.mws.model.orders.ListOrderItemsRequest;
import com.amazonaws.mws.model.orders.ListOrderItemsResponse;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenRequest;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenResponse;
import com.amazonaws.mws.model.orders.ListOrdersRequest;
import com.amazonaws.mws.model.orders.ListOrdersResponse;

/**
 *
 * MarketplaceWebServiceOrdersMock is the implementation of MarketplaceWebServiceOrders based
 * on the pre-populated set of XML files that serve local data. It simulates
 * responses from Marketplace Web Service Orders service.
 *
 * Use this to test your application without making a call to Marketplace Web Service Orders 
 *
 * Note, current Mock Service implementation does not valiadate requests
 *
 */
public  class MarketplaceWebServiceOrdersMock implements MarketplaceWebServiceOrders {

    private final Log log = LogFactory.getLog(MarketplaceWebServiceOrdersMock.class);
    private static JAXBContext  jaxbContext;
    private static ThreadLocal<Unmarshaller> unmarshaller;


    /** Initialize JAXBContext and  Unmarshaller **/
    static {
        try {
            jaxbContext = JAXBContext.newInstance("com.amazonservices.mws.model", MarketplaceWebServiceOrders.class.getClassLoader());
        } catch (JAXBException ex) {
            throw new ExceptionInInitializerError(ex);
        }
        unmarshaller = new ThreadLocal<Unmarshaller>() {
            protected synchronized Unmarshaller initialValue() {
                try {
                    return jaxbContext.createUnmarshaller();
                } catch(JAXBException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        };
    }

    // Public API ------------------------------------------------------------//

        
    /**
     * List Orders By Next Token 
     *
     * If ListOrders returns a nextToken, thus indicating that there are more orders
     * than returned that matched the given filter criteria, ListOrdersByNextToken
     * can be used to retrieve those other orders using that nextToken.
     * 
     * @param request
     *          ListOrdersByNextToken Action
     * @return
     *          ListOrdersByNextToken Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrdersByNextTokenResponse listOrdersByNextToken(ListOrdersByNextTokenRequest request)
        throws MarketplaceWebServiceOrdersException {
        ListOrdersByNextTokenResponse response;
        try {
            response = (ListOrdersByNextTokenResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("ListOrdersByNextTokenResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }

        
    /**
     * List Order Items By Next Token 
     *
     * If ListOrderItems cannot return all the order items in one go, it will
     * provide a nextToken.  That nextToken can be used with this operation to
     * retrive the next batch of items for that order.
     * 
     * @param request
     *          ListOrderItemsByNextToken Action
     * @return
     *          ListOrderItemsByNextToken Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrderItemsByNextTokenResponse listOrderItemsByNextToken(ListOrderItemsByNextTokenRequest request)
        throws MarketplaceWebServiceOrdersException {
        ListOrderItemsByNextTokenResponse response;
        try {
            response = (ListOrderItemsByNextTokenResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("ListOrderItemsByNextTokenResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }

        
    /**
     * Get Order 
     *
     * This operation takes up to 50 order ids and returns the corresponding orders.
     * 
     * @param request
     *          GetOrder Action
     * @return
     *          GetOrder Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public GetOrderResponse getOrder(GetOrderRequest request)
        throws MarketplaceWebServiceOrdersException {
        GetOrderResponse response;
        try {
            response = (GetOrderResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("GetOrderResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }

        
    /**
     * List Order Items 
     *
     * This operation can be used to list the items of the order indicated by the
     * given order id (only a single Amazon order id is allowed).
     * 
     * @param request
     *          ListOrderItems Action
     * @return
     *          ListOrderItems Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrderItemsResponse listOrderItems(ListOrderItemsRequest request)
        throws MarketplaceWebServiceOrdersException {
        ListOrderItemsResponse response;
        try {
            response = (ListOrderItemsResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("ListOrderItemsResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }

        
    /**
     * List Orders 
     *
     * ListOrders can be used to find orders that meet the specified criteria.
     * 
     * @param request
     *          ListOrders Action
     * @return
     *          ListOrders Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrdersResponse listOrders(ListOrdersRequest request)
        throws MarketplaceWebServiceOrdersException {
        ListOrdersResponse response;
        try {
            response = (ListOrdersResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("ListOrdersResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }

        
    /**
     * Get Service Status 
     *
     * Returns the service status of a particular MWS API section. The operation
     * takes no input.
     * All API sections within the API are required to implement this operation.
     * 
     * @param request
     *          GetServiceStatus Action
     * @return
     *          GetServiceStatus Response from the service
     *
     * @throws MarketplaceWebServiceOrdersException
     */
    public GetServiceStatusResponse getServiceStatus(GetServiceStatusRequest request)
        throws MarketplaceWebServiceOrdersException {
        GetServiceStatusResponse response;
        try {
            response = (GetServiceStatusResponse)getUnmarshaller().unmarshal
                    (new InputSource(this.getClass().getResourceAsStream("GetServiceStatusResponse.xml")));

            log.debug("Response from Mock Service: " + response.toXML());

        } catch (JAXBException jbe) {
            throw new MarketplaceWebServiceOrdersException("Unable to process mock response", jbe);
        }
        return response;
    }


    /**
     * Get unmarshaller for current thread
     */
    private Unmarshaller getUnmarshaller() {
        return unmarshaller.get();
    }
}