package com.amazonaws.mws.samples;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import com.amazonaws.mws.MarketplaceWebServiceOrders;
import com.amazonaws.mws.MarketplaceWebServiceOrdersClient;
import com.amazonaws.mws.MarketplaceWebServiceOrdersException;
import com.amazonaws.mws.model.orders.ListOrderItemsByNextTokenRequest;
import com.amazonaws.mws.model.orders.ListOrderItemsByNextTokenResponse;
import com.amazonaws.mws.model.orders.ListOrderItemsByNextTokenResult;
import com.amazonaws.mws.model.orders.ListOrderItemsRequest;
import com.amazonaws.mws.model.orders.ListOrderItemsResponse;
import com.amazonaws.mws.model.orders.ListOrderItemsResult;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenRequest;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenResponse;
import com.amazonaws.mws.model.orders.ListOrdersByNextTokenResult;
import com.amazonaws.mws.model.orders.ListOrdersRequest;
import com.amazonaws.mws.model.orders.ListOrdersResponse;
import com.amazonaws.mws.model.orders.ListOrdersResult;
import com.amazonaws.mws.model.orders.MarketplaceIdList;
import com.amazonaws.mws.model.orders.Order;
import com.amazonaws.mws.model.orders.OrderItem;

/**
 * This sample illustrates continuous order updates retrieval. It contains a
 * order updates fetcher job that fetches updates every 15 minutes.
 */

public class FetchOrderUpdatesJob {

	/*
	 * Add required parameters in OrdersSampleConfig.java before trying out this
	 * sample.
	 */

	public static final Log log = LogFactory.getLog(OrderFetcherSample.class);
	private volatile boolean keepRunning = true;

	/*************************************
	 * Throttling Limits in Milliseconds *
	 *************************************/
	// 1 call per 10 mins
	static final long LIST_ORDERS_THROTTLE_LIMIT = 600000L;
	// 1 call per 12 secs
	static final long LIST_ORDER_ITEMS_THROTTLE_LIMIT = 12000L;

	static final int INTERVAL_IN_MINS = 15; // 15-minute update interval

	protected MarketplaceWebServiceOrders service;
	protected String sellerId = null;
	protected MarketplaceIdList marketplaceIdList = null;

	public FetchOrderUpdatesJob(
			MarketplaceWebServiceOrders marketplaceWebServiceOrdersClient,
			String sellerId, MarketplaceIdList marketplaceIdList) {
		if (marketplaceWebServiceOrdersClient == null) {
			throw new IllegalArgumentException(
					"MarketplaceWebServiceOrders object cannot be null.");
		}
		this.service = marketplaceWebServiceOrdersClient;
		this.sellerId = sellerId;
		this.marketplaceIdList = marketplaceIdList;
	}

	public static void main(String... args) {
		MarketplaceWebServiceOrders client = new MarketplaceWebServiceOrdersClient(
				OrdersSampleConfig.accessKeyId,
				OrdersSampleConfig.secretAccessKey,
				OrdersSampleConfig.applicationName,
				OrdersSampleConfig.applicationVersion,
				OrdersSampleConfig.config);
		final FetchOrderUpdatesJob orderFetcher = new FetchOrderUpdatesJob(
				client, OrdersSampleConfig.sellerId,
				OrdersSampleConfig.marketplaceIdList);

		Thread fetchOrderUpdatesJobThread = new Thread(new Runnable() {
			public void run() {
				orderFetcher.startPeriodicSynch();
			}
		});

		// Start the FetchOrderUpdatesJob in the second thread.
		fetchOrderUpdatesJobThread.start();

		/*
		 * The following code puts this thread to sleep for an hour and then the
		 * stopSynch() method is called which stops the job in the second
		 * thread.
		 */

		/*
		 * TODO: Make sure to change this if you want it to run indefinitely or
		 * for some other length of time .
		 */
		try {
			log.info("Main thread sleeping.");
			Thread.sleep(3600000L); // = 1 hour
			log.info("Main thread awake.");
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return;
		}

		log.info("Main thread setting keepRunning to false.");
		orderFetcher.stopSynch(); // Stops the FetchOrderUpdatesJob thread.

		// Join the two threads and wait for the fetchOrderUpdatesJobThread to
		// finish.
		try {
			fetchOrderUpdatesJobThread.join();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return;
		}
	}

	/**
	 * Call this method to start this job. Note that the job will run until the
	 * keepRunning flag is set to false.
	 */
	public void startPeriodicSynch() {

		DatatypeFactory df = null;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			log.error(e.getMessage(), e);
		}
		XMLGregorianCalendar startTime = df
				.newXMLGregorianCalendar(new GregorianCalendar());
		// endTime = now -2. So, startTime = now - 2 - INTERVAL_IN_MINS.
		startTime.add(df.newDurationDayTime(false, 0, 0, INTERVAL_IN_MINS + 2,
				0));
		/*
		 * This loop keeps running until the stopSynch() method is called.
		 * stopSynch() sets keepRunning to false.
		 */
		log.info("Sync job starting.");
		while (keepRunning) {
			// Subtract 2 minutes because endTime returned from server = now - 2
			// mins.
			long timeWaited = System.currentTimeMillis()
					- startTime.toGregorianCalendar().getTimeInMillis()
					- 120000;
			/*
			 * If the timeWaited is greater than 30 times the interval, we exit
			 * from the loop as the job is lagging far behind in getting new
			 * orders.
			 */
			if (timeWaited > 30 * INTERVAL_IN_MINS * 60L * 1000L) {
				keepRunning = false;
				log
						.info("timeWaited is greater than 30 times the interval. This a too large a time interval for which to fetch orders.");
			}
			/*
			 * If the time lapsed is greater than or equal to 15 mins, we will
			 * try to fetchOrderUpdates giving it a startTime.
			 */
			else if (timeWaited >= INTERVAL_IN_MINS * 60L * 1000L) {
				log.info("Time to fetch new order updates.");
				XMLGregorianCalendar endTime = fetchOrderUpdatesSince(startTime);
				if (endTime != null) {
					startTime = endTime;
				} else {
					log.info("endTime returned is null. Sleeping for "
							+ INTERVAL_IN_MINS + " minutes.");
					try {
						Thread.sleep(INTERVAL_IN_MINS * 60L * 1000L);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
						stopSynch();
						break;
					}
				}
			} else {
				try {
					long timeToSleepInMilliseconds = INTERVAL_IN_MINS * 60000L
							- timeWaited;
					log.info("Thread sleeping for "
							+ Math.round(timeToSleepInMilliseconds / 60000)
							+ " minutes.");
					// Sleep in 1 minute intervals for timeToSleepInMilliseconds
					// or until keepRunning is set to false.
					for (long i = timeToSleepInMilliseconds; i > 0
							&& keepRunning; i = i - 60000L) {
						if (i < 60000L) {
							Thread.sleep(i);
						} else {
							Thread.sleep(60000L);
						}
					}

				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
					stopSynch();
					break;
				}
			}
		}
		log.info("Sync job ending.");
	}

	/**
	 * Call this method to stop execution of this job. The job will stop after
	 * it has finished processing the current call (if it is in the middle of
	 * processing any). For example if a listOrders call has been invoked, then
	 * it will terminate the job only after the call returns with valid results.
	 * If this method is called while the thread is sleeping, because a request
	 * was throttled, the job will terminate after it wakes up.
	 */
	public void stopSynch() {
		keepRunning = false;
	}

	/**
	 * Gets all orders updated between the given startTime and the calculated
	 * endTime. The endTime is calculated by the service as server's system
	 * clock time minus 2 mins. The method returns this endTime.
	 * 
	 */
	protected XMLGregorianCalendar fetchOrderUpdatesSince(
			XMLGregorianCalendar startTime) {
		log.info("Fetching order updates since " + startTime.toString());
		ListOrdersRequest listOrdersRequest = new ListOrdersRequest();

		listOrdersRequest.setSellerId(sellerId);
		if (OrdersSampleConfig.marketplaceIdList != null) {
			listOrdersRequest
					.setMarketplaceId(OrdersSampleConfig.marketplaceIdList);
		}
		listOrdersRequest.setLastUpdatedAfter(startTime);
		XMLGregorianCalendar lastUpdatedDate = null;

		try {
			ListOrdersResult listOrdersResult = listOrders(listOrdersRequest);
			if (listOrdersResult.isSetLastUpdatedBefore()) {
				lastUpdatedDate = listOrdersResult.getLastUpdatedBefore();
			}

			if (listOrdersResult != null && listOrdersResult.isSetNextToken()) {
				ListOrdersByNextTokenRequest listOrdersByNextTokenRequest = new ListOrdersByNextTokenRequest();
				listOrdersByNextTokenRequest.setSellerId(sellerId);
				String nextToken = listOrdersResult.getNextToken();
				ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;

				while (nextToken != null && keepRunning) {
					listOrdersByNextTokenRequest.setNextToken(nextToken);
					listOrdersByNextTokenResult = listOrdersByNextToken(listOrdersByNextTokenRequest);
					nextToken = listOrdersByNextTokenResult.getNextToken();
				}
				if (listOrdersByNextTokenResult.isSetLastUpdatedBefore()) {
					lastUpdatedDate = listOrdersByNextTokenResult
							.getLastUpdatedBefore();
				}
			}
			return lastUpdatedDate;
		} catch (MarketplaceWebServiceOrdersException ex) {
			log.error(ex.getMessage(), ex);
			System.out.println("Caught Exception: " + ex.getMessage());
			System.out.println("Response Status Code: " + ex.getStatusCode());
			System.out.println("Error Code: " + ex.getErrorCode());
			System.out.println("Error Type: " + ex.getErrorType());
			System.out.println("Request ID: " + ex.getRequestId());
			System.out.print("XML: " + ex.getXML());
			return null;
		}
	}

	/*
	 * listOrders call. If a request is throttled, this method calls the
	 * throttling handler and sets the retry flag to true.
	 */
	protected ListOrdersResult listOrders(ListOrdersRequest request)
			throws MarketplaceWebServiceOrdersException {
		boolean retry;
		ListOrdersResponse listOrdersResponse = null;
		ListOrdersResult listOrdersResult = null;
		do {
			retry = false;
			try {
				log.info("Calling ListOrders.");
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
				if (listOrdersResult.isSetOrders()
						&& listOrdersResult.getOrders().isSetOrder()) {
					processOrderUpdates(listOrdersResult.getOrders().getOrder());
				}
			}
		} while (retry && keepRunning);
		return listOrdersResult;
	}

	/*
	 * listOrdersByNextToken call. If a request is throttled, this method calls
	 * the throttling handler and sets the retry flag to true.
	 */
	protected ListOrdersByNextTokenResult listOrdersByNextToken(
			ListOrdersByNextTokenRequest listOrdersByNextTokenRequest)
			throws MarketplaceWebServiceOrdersException {
		boolean retry;
		ListOrdersByNextTokenResponse listOrdersByNextTokenResponse = null;
		ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;
		do {
			retry = false;
			try {
				log.info("Calling ListOrdersByNextToken.");
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
				if (listOrdersByNextTokenResult.isSetOrders()
						&& listOrdersByNextTokenResult.getOrders().isSetOrder()) {
					processOrderUpdates(listOrdersByNextTokenResult.getOrders()
							.getOrder());
				}
			}
		} while (retry && keepRunning);
		return listOrdersByNextTokenResult;
	}

	/**
	 * This method is called to process updates on each of the orders that are
	 * returned by the ListOrders and ListOrdersByNextToken calls.
	 */
	protected void processOrderUpdates(List<Order> orders)
			throws MarketplaceWebServiceOrdersException {
		ListOrderItemsResult listOrderItemsResult;
		log.info("Processing order updates.");
		for (Order order : orders) {
			if (shouldFetchOrderItems(order)) {
				log.info("Order being processed: " + order.getAmazonOrderId());
				listOrderItemsResult = listOrderItems(order);
				if (listOrderItemsResult != null
						&& listOrderItemsResult.isSetNextToken()) {
					ListOrderItemsByNextTokenRequest listOrderItemsByNextTokenRequest = new ListOrderItemsByNextTokenRequest();
					listOrderItemsByNextTokenRequest
							.setSellerId(OrdersSampleConfig.sellerId);
					String nextToken = listOrderItemsResult.getNextToken();

					ListOrderItemsByNextTokenResult listOrderItemsByNextTokenResult = null;

					while (nextToken != null && keepRunning) {
						listOrderItemsByNextTokenRequest
								.setNextToken(nextToken);
						listOrderItemsByNextTokenResult = listOrderItemsByNextToken(
								listOrderItemsByNextTokenRequest, order);
						nextToken = listOrderItemsByNextTokenResult
								.getNextToken();
					}
				}
			}
		}
	}

	/*
	 * listOrderItems call. If a request is throttled, this method calls the
	 * throttling handler and sets the retry flag to true.
	 */
	protected ListOrderItemsResult listOrderItems(Order order)
			throws MarketplaceWebServiceOrdersException {
		log.info("Calling ListOrderItems.");
		boolean retry;
		List<OrderItem> items;
		ListOrderItemsRequest listOrderItemsRequest = new ListOrderItemsRequest();
		listOrderItemsRequest.setSellerId(sellerId);
		listOrderItemsRequest.setAmazonOrderId(order.getAmazonOrderId());
		ListOrderItemsResponse listOrderItemsResponse = null;
		ListOrderItemsResult listOrderItemsResult = null;

		do {
			retry = false;
			try {
				listOrderItemsResponse = service
						.listOrderItems(listOrderItemsRequest);
			} catch (MarketplaceWebServiceOrdersException ex) {
				if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
						&& "RequestThrottled".equals(ex.getErrorCode())) {
					retry = true;
					requestThrottledExceptionHandler(LIST_ORDER_ITEMS_THROTTLE_LIMIT);
				} else {
					throw ex;
				}
			}
			if (listOrderItemsResponse != null
					&& listOrderItemsResponse.isSetListOrderItemsResult()) {
				listOrderItemsResult = listOrderItemsResponse
						.getListOrderItemsResult();
				if (listOrderItemsResult.isSetOrderItems()
						&& listOrderItemsResult.getOrderItems()
								.isSetOrderItem()) {
					items = listOrderItemsResponse.getListOrderItemsResult()
							.getOrderItems().getOrderItem();
					processOrderItems(order, items);
				}
			}

		} while (retry && keepRunning);

		return listOrderItemsResult;
	}

	/*
	 * listOrderItemsByNextToken call. If a request is throttled, this method
	 * calls the throttling handler and sets the retry flag to true.
	 */
	protected ListOrderItemsByNextTokenResult listOrderItemsByNextToken(
			ListOrderItemsByNextTokenRequest listOrderItemsByNextTokenRequest,
			Order order) throws MarketplaceWebServiceOrdersException {
		log.info("Calling ListOrderItemsByNextToken.");
		boolean retry;
		List<OrderItem> items;
		ListOrderItemsByNextTokenResponse listOrderItemsByNextTokenResponse = null;
		ListOrderItemsByNextTokenResult listOrderItemsByNextTokenResult = null;

		do {
			retry = false;
			try {
				listOrderItemsByNextTokenResponse = service
						.listOrderItemsByNextToken(listOrderItemsByNextTokenRequest);
			} catch (MarketplaceWebServiceOrdersException ex) {
				if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
						&& "RequestThrottled".equals(ex.getErrorCode())) {
					retry = true;
					requestThrottledExceptionHandler(LIST_ORDER_ITEMS_THROTTLE_LIMIT);
				} else {
					throw ex;
				}
			}
			if (listOrderItemsByNextTokenResponse != null
					&& listOrderItemsByNextTokenResponse
							.isSetListOrderItemsByNextTokenResult()) {
				listOrderItemsByNextTokenResult = listOrderItemsByNextTokenResponse
						.getListOrderItemsByNextTokenResult();
				if (listOrderItemsByNextTokenResult.isSetOrderItems()
						&& listOrderItemsByNextTokenResult.getOrderItems()
								.isSetOrderItem()) {
					items = listOrderItemsByNextTokenResponse
							.getListOrderItemsByNextTokenResult()
							.getOrderItems().getOrderItem();
					processOrderItems(order, items);
				}
			}
		} while (retry && keepRunning);

		return listOrderItemsByNextTokenResult;
	}

	/**
	 * TODO: Change the logic to decide when order items need to be fetched.
	 * Probably only if this is the first time you are seeing the order.
	 */
	protected boolean shouldFetchOrderItems(Order order) {
		return true;
	}

	/**
	 * Simply prints out the data to standard out. TODO: Insert logic to process
	 * order items here (perhaps store it in your local datastore).
	 */
	protected void processOrderItems(Order order, List<OrderItem> orderItems) {
		log.info("****************** Order info********************");
		log.info(order.toString());
		if (orderItems != null && orderItems.size() > 0) {
			log
					.info("================== Order Items info ====================");
			for (OrderItem item : orderItems) {
				log.info(item.toString());
			}
		}
	}

	/*
	 * When a request is throttled, this method is called to make the thread
	 * sleep for a period of time specified by the throttling limit.
	 */
	private void requestThrottledExceptionHandler(
			long throttlingLimitInMilliseconds) {
		try {
			log.info("Request throttled. Sleeping for "
					+ throttlingLimitInMilliseconds / 1000 + " seconds.");

			// Sleep in 1 minute intervals for throttlingLimitInMillis or until
			// keepRunning is set to false.
			for (long i = throttlingLimitInMilliseconds; i > 0 && keepRunning; i = i - 60000L) {
				if (i < 60000L) {
					Thread.sleep(i);
				} else {
					Thread.sleep(60000L);
				}
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return;
		}
	}
}
