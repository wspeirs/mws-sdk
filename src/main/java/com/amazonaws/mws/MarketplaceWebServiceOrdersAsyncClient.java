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
 *  Generated: Wed Jan 19 22:17:09 UTC 2011 
 * 
 */

package com.amazonaws.mws;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.amazonaws.mws.model.GetOrderRequest;
import com.amazonaws.mws.model.GetOrderResponse;
import com.amazonaws.mws.model.GetServiceStatusRequest;
import com.amazonaws.mws.model.GetServiceStatusResponse;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenRequest;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenResponse;
import com.amazonaws.mws.model.ListOrderItemsRequest;
import com.amazonaws.mws.model.ListOrderItemsResponse;
import com.amazonaws.mws.model.ListOrdersByNextTokenRequest;
import com.amazonaws.mws.model.ListOrdersByNextTokenResponse;
import com.amazonaws.mws.model.ListOrdersRequest;
import com.amazonaws.mws.model.ListOrdersResponse;

/**
 * This contains the Order Retrieval API section of the Marketplace Web Service.
 * 
 * 
 */
public class MarketplaceWebServiceOrdersAsyncClient extends
		MarketplaceWebServiceOrdersClient implements
		MarketplaceWebServiceOrdersAsync {

	private ExecutorService executor;

	/**
	 * Client to make asynchronous calls to the service. Please note, you should
	 * configure executor with same number of concurrent threads as number of
	 * http connections specified in MarketplaceWebServiceOrdersConfig. Default
	 * number of max http connections is 100.
	 * 
	 * @param awsAccessKeyId
	 *            AWS Access Key Id
	 * @param awsSecretAccessKey
	 *            AWS Secret Key
	 * @param config
	 *            service configuration. Pass new
	 *            MarketplaceWebServiceOrdersConfig() if you plan to use
	 *            defaults
	 * 
	 * @param executor
	 *            Executor service to manage asynchronous calls.
	 * 
	 */
	public MarketplaceWebServiceOrdersAsyncClient(String awsAccessKeyId,
			String awsSecretAccessKey, String applicationName,
			String applicationVersion,
			MarketplaceWebServiceOrdersConfig config, ExecutorService executor) {
		super(awsAccessKeyId, awsSecretAccessKey, applicationName,
				applicationVersion, config);
		this.executor = executor;
	}

	/**
	 * Non-blocking List Orders By Next Token
	 * <p/>
	 * Returns <code>future</code> pointer to ListOrdersByNextTokenResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * ListOrdersByNextTokenResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;ListOrdersByNextTokenResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;ListOrdersByNextTokenResponse&gt;&gt;();
	 * for (ListOrdersByNextTokenRequest request : requests) {
	 * 	responses.add(client.listOrdersByNextTokenAsync(request));
	 * }
	 * for (Future&lt;ListOrdersByNextTokenResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		ListOrdersByNextTokenResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            ListOrdersByNextTokenRequest request
	 * @return Future&lt;ListOrdersByNextTokenResponse&gt; future pointer to
	 *         ListOrdersByNextTokenResponse
	 * 
	 */
	public Future<ListOrdersByNextTokenResponse> listOrdersByNextTokenAsync(
			final ListOrdersByNextTokenRequest request) {
		Future<ListOrdersByNextTokenResponse> response = executor
				.submit(new Callable<ListOrdersByNextTokenResponse>() {

					public ListOrdersByNextTokenResponse call()
							throws MarketplaceWebServiceOrdersException {
						return listOrdersByNextToken(request);
					}
				});
		return response;
	}

	/**
	 * Non-blocking List Order Items By Next Token
	 * <p/>
	 * Returns <code>future</code> pointer to ListOrderItemsByNextTokenResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * ListOrderItemsByNextTokenResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;ListOrderItemsByNextTokenResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;ListOrderItemsByNextTokenResponse&gt;&gt;();
	 * for (ListOrderItemsByNextTokenRequest request : requests) {
	 * 	responses.add(client.listOrderItemsByNextTokenAsync(request));
	 * }
	 * for (Future&lt;ListOrderItemsByNextTokenResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		ListOrderItemsByNextTokenResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            ListOrderItemsByNextTokenRequest request
	 * @return Future&lt;ListOrderItemsByNextTokenResponse&gt; future pointer to
	 *         ListOrderItemsByNextTokenResponse
	 * 
	 */
	public Future<ListOrderItemsByNextTokenResponse> listOrderItemsByNextTokenAsync(
			final ListOrderItemsByNextTokenRequest request) {
		Future<ListOrderItemsByNextTokenResponse> response = executor
				.submit(new Callable<ListOrderItemsByNextTokenResponse>() {

					public ListOrderItemsByNextTokenResponse call()
							throws MarketplaceWebServiceOrdersException {
						return listOrderItemsByNextToken(request);
					}
				});
		return response;
	}

	/**
	 * Non-blocking Get Order
	 * <p/>
	 * Returns <code>future</code> pointer to GetOrderResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * GetOrderResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;GetOrderResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;GetOrderResponse&gt;&gt;();
	 * for (GetOrderRequest request : requests) {
	 * 	responses.add(client.getOrderAsync(request));
	 * }
	 * for (Future&lt;GetOrderResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		GetOrderResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            GetOrderRequest request
	 * @return Future&lt;GetOrderResponse&gt; future pointer to GetOrderResponse
	 * 
	 */
	public Future<GetOrderResponse> getOrderAsync(final GetOrderRequest request) {
		Future<GetOrderResponse> response = executor
				.submit(new Callable<GetOrderResponse>() {

					public GetOrderResponse call()
							throws MarketplaceWebServiceOrdersException {
						return getOrder(request);
					}
				});
		return response;
	}

	/**
	 * Non-blocking List Order Items
	 * <p/>
	 * Returns <code>future</code> pointer to ListOrderItemsResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * ListOrderItemsResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;ListOrderItemsResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;ListOrderItemsResponse&gt;&gt;();
	 * for (ListOrderItemsRequest request : requests) {
	 * 	responses.add(client.listOrderItemsAsync(request));
	 * }
	 * for (Future&lt;ListOrderItemsResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		ListOrderItemsResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            ListOrderItemsRequest request
	 * @return Future&lt;ListOrderItemsResponse&gt; future pointer to
	 *         ListOrderItemsResponse
	 * 
	 */
	public Future<ListOrderItemsResponse> listOrderItemsAsync(
			final ListOrderItemsRequest request) {
		Future<ListOrderItemsResponse> response = executor
				.submit(new Callable<ListOrderItemsResponse>() {

					public ListOrderItemsResponse call()
							throws MarketplaceWebServiceOrdersException {
						return listOrderItems(request);
					}
				});
		return response;
	}

	/**
	 * Non-blocking List Orders
	 * <p/>
	 * Returns <code>future</code> pointer to ListOrdersResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * ListOrdersResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;ListOrdersResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;ListOrdersResponse&gt;&gt;();
	 * for (ListOrdersRequest request : requests) {
	 * 	responses.add(client.listOrdersAsync(request));
	 * }
	 * for (Future&lt;ListOrdersResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		ListOrdersResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            ListOrdersRequest request
	 * @return Future&lt;ListOrdersResponse&gt; future pointer to
	 *         ListOrdersResponse
	 * 
	 */
	public Future<ListOrdersResponse> listOrdersAsync(
			final ListOrdersRequest request) {
		Future<ListOrdersResponse> response = executor
				.submit(new Callable<ListOrdersResponse>() {

					public ListOrdersResponse call()
							throws MarketplaceWebServiceOrdersException {
						return listOrders(request);
					}
				});
		return response;
	}

	/**
	 * Non-blocking Get Service Status
	 * <p/>
	 * Returns <code>future</code> pointer to GetServiceStatusResponse
	 * <p/>
	 * If response is ready, call to <code>future.get()</code> will return
	 * GetServiceStatusResponse.
	 * <p/>
	 * If response is not ready, call to <code>future.get()</code> will block
	 * the calling thread until response is returned.
	 * <p/>
	 * Note, <code>future.get()</code> will throw wrapped runtime exception.
	 * <p/>
	 * If service error has occured, MarketplaceWebServiceOrdersException can be
	 * extracted with <code>exception.getCause()</code>
	 * <p/>
	 * Usage example for parallel processing:
	 * 
	 * <pre>
	 * 
	 * List&lt;Future&lt;GetServiceStatusResponse&gt;&gt; responses = new ArrayList&lt;Future&lt;GetServiceStatusResponse&gt;&gt;();
	 * for (GetServiceStatusRequest request : requests) {
	 * 	responses.add(client.getServiceStatusAsync(request));
	 * }
	 * for (Future&lt;GetServiceStatusResponse&gt; future : responses) {
	 * 	while (!future.isDone()) {
	 * 		Thread.yield();
	 * 	}
	 * 	try {
	 * 		GetServiceStatusResponse response = future.get();
	 * 		// use response
	 * 	} catch (Exception e) {
	 * 		if (e instanceof MarketplaceWebServiceOrdersException) {
	 * 			MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
	 * 					.cast(e);
	 * 			// handle MarketplaceWebServiceOrdersException
	 * 		} else {
	 * 			// handle other exceptions
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            GetServiceStatusRequest request
	 * @return Future&lt;GetServiceStatusResponse&gt; future pointer to
	 *         GetServiceStatusResponse
	 * 
	 */
	public Future<GetServiceStatusResponse> getServiceStatusAsync(
			final GetServiceStatusRequest request) {
		Future<GetServiceStatusResponse> response = executor
				.submit(new Callable<GetServiceStatusResponse>() {

					public GetServiceStatusResponse call()
							throws MarketplaceWebServiceOrdersException {
						return getServiceStatus(request);
					}
				});
		return response;
	}

}
