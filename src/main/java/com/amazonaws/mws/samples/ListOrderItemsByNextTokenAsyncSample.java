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
import com.amazonaws.mws.model.ListOrderItemsByNextTokenRequest;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenResponse;

/**
 * 
 * List Order Items By Next Token Samples
 * 
 * 
 */
public class ListOrderItemsByNextTokenAsyncSample {

	/**
	 * Just add few required parameters, and try the service List Order Items By
	 * Next Token functionality
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
				OrdersSampleConfig.accessKeyId,
				OrdersSampleConfig.secretAccessKey,
				OrdersSampleConfig.applicationName,
				OrdersSampleConfig.applicationVersion,
				OrdersSampleConfig.config, executor);

		/************************************************************************
		 * Setup requests parameters and invoke parallel processing. Of course
		 * in real world application, there will be much more than a couple of
		 * requests to process.
		 ***********************************************************************/
		ListOrderItemsByNextTokenRequest requestOne = new ListOrderItemsByNextTokenRequest();
		// @TODO: set request parameters here
		requestOne.setSellerId(OrdersSampleConfig.sellerId);

		ListOrderItemsByNextTokenRequest requestTwo = new ListOrderItemsByNextTokenRequest();
		// @TODO: set second request parameters here
		requestTwo.setSellerId(OrdersSampleConfig.sellerId);

		List<ListOrderItemsByNextTokenRequest> requests = new ArrayList<ListOrderItemsByNextTokenRequest>();
		requests.add(requestOne);
		requests.add(requestTwo);

		invokeListOrderItemsByNextToken(service, requests);

		executor.shutdown();

	}

	/**
	 * List Order Items By Next Token request sample If ListOrderItems cannot
	 * return all the order items in one go, it will provide a nextToken. That
	 * nextToken can be used with this operation to retrive the next batch of
	 * items for that order.
	 * 
	 * @param service
	 *            instance of MarketplaceWebServiceOrders service
	 * @param requests
	 *            list of requests to process
	 */
	public static void invokeListOrderItemsByNextToken(
			MarketplaceWebServiceOrdersAsync service,
			List<ListOrderItemsByNextTokenRequest> requests) {
		List<Future<ListOrderItemsByNextTokenResponse>> responses = new ArrayList<Future<ListOrderItemsByNextTokenResponse>>();
		for (ListOrderItemsByNextTokenRequest request : requests) {
			responses.add(service.listOrderItemsByNextTokenAsync(request));
		}
		for (Future<ListOrderItemsByNextTokenResponse> future : responses) {
			while (!future.isDone()) {
				Thread.yield();
			}
			try {
				ListOrderItemsByNextTokenResponse response = future.get();
				// Original request corresponding to this response, if needed:
				ListOrderItemsByNextTokenRequest originalRequest = requests
						.get(responses.indexOf(future));
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
