package com.amazonaws.mws.samples;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import com.amazonaws.mws.MarketplaceWebServiceOrders;
import com.amazonaws.mws.MarketplaceWebServiceOrdersClient;
import com.amazonaws.mws.MarketplaceWebServiceOrdersException;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenRequest;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenResponse;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenResult;
import com.amazonaws.mws.model.orders.ListOrdersRequest;
import com.amazonaws.mws.model.orders.ListOrdersResponse;
import com.amazonaws.mws.model.orders.ListOrdersResult;
import com.amazonaws.mws.model.orders.OrderList;

/**
 * Sample file that fetches orders created during a given time period.
 */

public class OrderFetcherSample {

	/*
	 * Add required parameters in OrdersSampleConfig.java before trying out this
	 * sample.
	 */
	public static final Log log = LogFactory.getLog(OrderFetcherSample.class);

	/*****************************************
	 * Throttling Limits in Milliseconds
	 *****************************************/
	static final long LIST_ORDERS_THROTTLE_LIMIT = 600000L; // 1 call/10 mins

	protected MarketplaceWebServiceOrders service;

	public OrderFetcherSample() {
		/*********************************************************************
		 * Instantiate Http Client Implementation of Marketplace Web Service *
		 * Orders
		 *********************************************************************/
		this.service = new MarketplaceWebServiceOrdersClient(
				OrdersSampleConfig.accessKeyId,
				OrdersSampleConfig.secretAccessKey,
				OrdersSampleConfig.applicationName,
				OrdersSampleConfig.applicationVersion,
				OrdersSampleConfig.config);
	}

	/**
	 * Fetches all orders created in the given time period and processes them
	 * locally. If end is null, it will be ignored (the MWS service will pick an
	 * appropriate time, which is now - 2 minutes).
	 */
	public void fetchOrders(XMLGregorianCalendar start, XMLGregorianCalendar end) throws MarketplaceWebServiceOrdersException {
		ListOrdersRequest listOrdersRequest = new ListOrdersRequest();
		listOrdersRequest.setSellerId(OrdersSampleConfig.sellerId);
		if (OrdersSampleConfig.marketplaceIdList != null) {
			listOrdersRequest
					.setMarketplaceId(OrdersSampleConfig.marketplaceIdList);
		}
		listOrdersRequest.setCreatedAfter(start);
		if (start == null) {
			throw new IllegalArgumentException("Start date cannot be null.");
		}
		if (end != null) {
			listOrdersRequest.setCreatedBefore(end);
		}

		try {
			ListOrdersResult listOrdersResult = listOrders(listOrdersRequest);

			if (listOrdersResult != null && listOrdersResult.isSetNextToken()) {
				ListOrdersByNextTokenRequest listOrdersByNextTokenRequest = new ListOrdersByNextTokenRequest();
				listOrdersByNextTokenRequest
						.setSellerId(OrdersSampleConfig.sellerId);
				String nextToken = listOrdersResult.getNextToken();
				ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;
				while (nextToken != null) {
					listOrdersByNextTokenRequest.setNextToken(nextToken);
					listOrdersByNextTokenResult = listOrdersByNextToken(listOrdersByNextTokenRequest);
					nextToken = listOrdersByNextTokenResult.getNextToken();
				}
			}
		} catch (MarketplaceWebServiceOrdersException ex) {
			System.out.println("Caught Exception: " + ex.getMessage());
			System.out.println("Response Status Code: " + ex.getStatusCode());
			System.out.println("Error Code: " + ex.getErrorCode());
			System.out.println("Error Type: " + ex.getErrorType());
			System.out.println("Request ID: " + ex.getRequestId());
			System.out.print("XML: " + ex.getXML());
			throw ex;
		}
	}

	private ListOrdersResult listOrders(ListOrdersRequest request)
			throws MarketplaceWebServiceOrdersException {
		boolean retry;
		ListOrdersResponse listOrdersResponse = null;
		ListOrdersResult listOrdersResult = null;
		do {
			retry = false;
			try {
				listOrdersResponse = service.listOrders(request);

			} catch (MarketplaceWebServiceOrdersException ex) {
				if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
						&& "RequestThrottled".equals(ex.getErrorCode())) {
					retry = true;
					requestThrottledExceptionHandler(LIST_ORDERS_THROTTLE_LIMIT);
				} else {
					throw ex;
				}
			}
			if (listOrdersResponse != null
					&& listOrdersResponse.isSetListOrdersResult()) {
				listOrdersResult = listOrdersResponse.getListOrdersResult();
				if (listOrdersResult.isSetOrders()) {
					processOrders(listOrdersResult.getOrders());
				}
			}

		} while (retry);
		return listOrdersResult;
	}

	private ListOrdersByNextTokenResult listOrdersByNextToken(
			ListOrdersByNextTokenRequest listOrdersByNextTokenRequest)
			throws MarketplaceWebServiceOrdersException {
		boolean retry;
		ListOrdersByNextTokenResponse listOrdersByNextTokenResponse = null;
		ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;
		do {
			retry = false;
			try {

				listOrdersByNextTokenResponse = service
						.listOrdersByNextToken(listOrdersByNextTokenRequest);
			} catch (MarketplaceWebServiceOrdersException ex) {
				if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
						&& "RequestThrottled".equals(ex.getErrorCode())) {
					retry = true;
					requestThrottledExceptionHandler(LIST_ORDERS_THROTTLE_LIMIT);
				} else {
					throw ex;
				}
			}
			if (listOrdersByNextTokenResponse != null
					&& listOrdersByNextTokenResponse
							.isSetListOrdersByNextTokenResult()) {
				listOrdersByNextTokenResult = listOrdersByNextTokenResponse
						.getListOrdersByNextTokenResult();
				if (listOrdersByNextTokenResult.isSetOrders()) {
					processOrders(listOrdersByNextTokenResult.getOrders());
				}
			}

		} while (retry);
		return listOrdersByNextTokenResult;
	}

	private void requestThrottledExceptionHandler(long throttlingLimit) {
		try {
			log.info("Request throttled. Sleeping for " + throttlingLimit
					+ " milliseconds.");
			Thread.sleep(throttlingLimit);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return;
		}
	}

	/*
	 * TODO: Insert your order processing logic here.
	 */
	protected void processOrders(OrderList orders) {
		System.out.println(orders.toString());
	}

	public static void main(String... args) {

		OrderFetcherSample orderFetcher = new OrderFetcherSample();
		DatatypeFactory df = null;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			log.error(e.getMessage(), e);
		}

		/******************************************
		 * Uncomment the desired fetchOrders call *
		 ******************************************/

		/* Fetch orders for the last 24 hours GMT */
		XMLGregorianCalendar start1 = df
				.newXMLGregorianCalendar(new GregorianCalendar());
		Duration negativeOneDay = df.newDurationDayTime(false, 0, 24, 0, 0);
		start1.add(negativeOneDay);
//		try {
//			 orderFetcher.fetchOrders(start1, null);
//		} catch (MarketplaceWebServiceOrdersException e) {
//			log.error(e.getMessage(), e);
//		}

		/*
		 * Fetch orders for the last quarter (Oct 1st, 2010 to Dec 31st, 2010
		 * PST)
		 */
		XMLGregorianCalendar start2 = df.newXMLGregorianCalendarDate(2010, 10,
				1, -480);
		XMLGregorianCalendar end2 = df.newXMLGregorianCalendarDate(2011, 01,
				01, -480);
//		try {
//			orderFetcher.fetchOrders(start2, end2);
//		} catch (MarketplaceWebServiceOrdersException e) {			
//			e.printStackTrace();
//		}
	}

}
