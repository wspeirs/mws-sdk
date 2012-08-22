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
 *  Generated: Wed Jan 26 00:20:38 UTC 2011 
 * 
 */

package com.amazonaws.mws.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.mws.MarketplaceWebServiceOrdersAsync;
import com.amazonaws.mws.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonaws.mws.MarketplaceWebServiceOrdersException;
import com.amazonaws.mws.model.orders.ListOrdersRequest;
import com.amazonaws.mws.model.orders.ListOrdersResponse;

/**
 * 
 * List Orders Samples
 * 
 * 
 */
public class ListOrdersAsyncSample {

	/**
	 * Just add few required parameters, and try the service List Orders
	 * functionality
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String... args) {

		/*
		 * Add required parameters in OrdersSampleConfig.java before trying out
		 * this sample.
		 */

		/************************************************************************
		 * Instantiate Http Client Implementation of Marketplace Web Service
		 * Orders
		 * 
		 * Important! Number of threads in executor should match number of
		 * connections for http client.
		 * 
		 ***********************************************************************/

		OrdersSampleConfig.config.setMaxConnections(100);
		ExecutorService executor = Executors.newFixedThreadPool(100);
		MarketplaceWebServiceOrdersAsync service = new MarketplaceWebServiceOrdersAsyncClient(
				OrdersSampleConfig.accessKeyId, OrdersSampleConfig.secretAccessKey, OrdersSampleConfig.applicationName,
				OrdersSampleConfig.applicationVersion, OrdersSampleConfig.config, executor);

		/************************************************************************
		 * Setup requests parameters and invoke parallel processing. Of course
		 * in real world application, there will be much more than a couple of
		 * requests to process.
		 ***********************************************************************/
		ListOrdersRequest requestOne = new ListOrdersRequest();
		// @TODO: set request parameters here
		requestOne.setSellerId(OrdersSampleConfig.sellerId);

		ListOrdersRequest requestTwo = new ListOrdersRequest();
		// @TODO: set second request parameters here
		requestTwo.setSellerId(OrdersSampleConfig.sellerId);

		List<ListOrdersRequest> requests = new ArrayList<ListOrdersRequest>();
		requests.add(requestOne);
		requests.add(requestTwo);

		invokeListOrders(service, requests);

		executor.shutdown();

	}

	/**
	 * List Orders request sample ListOrders can be used to find orders that
	 * meet the specified criteria.
	 * 
	 * @param service
	 *            instance of MarketplaceWebServiceOrders service
	 * @param requests
	 *            list of requests to process
	 */
	public static void invokeListOrders(
			MarketplaceWebServiceOrdersAsync service,
			List<ListOrdersRequest> requests) {
		List<Future<ListOrdersResponse>> responses = new ArrayList<Future<ListOrdersResponse>>();
		for (ListOrdersRequest request : requests) {
			responses.add(service.listOrdersAsync(request));
		}
		for (Future<ListOrdersResponse> future : responses) {
			while (!future.isDone()) {
				Thread.yield();
			}
			try {
				ListOrdersResponse response = future.get();
				// Original request corresponding to this response, if needed:
				ListOrdersRequest originalRequest = requests.get(responses
						.indexOf(future));
				System.out.println("Response request id: "
						+ response.getResponseMetadata().getRequestId());
			} catch (Exception e) {
				if (e.getCause() instanceof MarketplaceWebServiceOrdersException) {
					MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
							.cast(e.getCause());
					System.out.println("Caught Exception: "
							+ exception.getMessage());
					System.out.println("Response Status Code: "
							+ exception.getStatusCode());
					System.out.println("Error Code: "
							+ exception.getErrorCode());
					System.out.println("Error Type: "
							+ exception.getErrorType());
					System.out.println("Request ID: "
							+ exception.getRequestId());
					System.out.print("XML: " + exception.getXML());
				} else {
					e.printStackTrace();
				}
			}
		}
	}

}
