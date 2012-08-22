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

import com.amazonaws.mws.MarketplaceWebServiceOrders;
import com.amazonaws.mws.MarketplaceWebServiceOrdersClient;
import com.amazonaws.mws.MarketplaceWebServiceOrdersException;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenRequest;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenResponse;
import com.amazonaws.mws.model.ListOrderItemsByNextTokenResult;
import com.amazonaws.mws.model.Money;
import com.amazonaws.mws.model.OrderItem;
import com.amazonaws.mws.model.OrderItemList;
import com.amazonaws.mws.model.PromotionIdList;
import com.amazonaws.mws.model.ResponseMetadata;

/**
 * 
 * List Order Items By Next Token Samples
 * 
 * 
 */
public class ListOrderItemsByNextTokenSample {

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
		 ***********************************************************************/
		MarketplaceWebServiceOrders service = new MarketplaceWebServiceOrdersClient(
				OrdersSampleConfig.accessKeyId,
				OrdersSampleConfig.secretAccessKey,
				OrdersSampleConfig.applicationName,
				OrdersSampleConfig.applicationVersion,
				OrdersSampleConfig.config);

		/************************************************************************
		 * Uncomment to try out Mock Service that simulates Marketplace Web
		 * Service Orders responses without calling Marketplace Web Service
		 * Orders service.
		 * 
		 * Responses are loaded from local XML files. You can tweak XML files to
		 * experiment with various outputs during development
		 * 
		 * XML files available under com/amazonservices/mws/mock tree
		 * 
		 ***********************************************************************/
		// MarketplaceWebServiceOrders service = new
		// MarketplaceWebServiceOrdersMock();

		/************************************************************************
		 * Setup request parameters and uncomment invoke to try out sample for
		 * List Order Items By Next Token
		 ***********************************************************************/
		ListOrderItemsByNextTokenRequest request = new ListOrderItemsByNextTokenRequest();

		// @TODO: set request parameters here
		request.setSellerId(OrdersSampleConfig.sellerId);

		// invokeListOrderItemsByNextToken(service, request);

	}

	/**
	 * List Order Items By Next Token request sample If ListOrderItems cannot
	 * return all the order items in one go, it will provide a nextToken. That
	 * nextToken can be used with this operation to retrive the next batch of
	 * items for that order.
	 * 
	 * @param service
	 *            instance of MarketplaceWebServiceOrders service
	 * @param request
	 *            Action to invoke
	 */
	public static void invokeListOrderItemsByNextToken(
			MarketplaceWebServiceOrders service,
			ListOrderItemsByNextTokenRequest request) {
		try {

			ListOrderItemsByNextTokenResponse response = service
					.listOrderItemsByNextToken(request);

			System.out.println("ListOrderItemsByNextToken Action Response");
			System.out
					.println("=============================================================================");
			System.out.println();

			System.out.println("    ListOrderItemsByNextTokenResponse");
			System.out.println();
			if (response.isSetListOrderItemsByNextTokenResult()) {
				System.out.println("        ListOrderItemsByNextTokenResult");
				System.out.println();
				ListOrderItemsByNextTokenResult listOrderItemsByNextTokenResult = response
						.getListOrderItemsByNextTokenResult();
				if (listOrderItemsByNextTokenResult.isSetNextToken()) {
					System.out.println("            NextToken");
					System.out.println();
					System.out.println("                "
							+ listOrderItemsByNextTokenResult.getNextToken());
					System.out.println();
				}
				if (listOrderItemsByNextTokenResult.isSetAmazonOrderId()) {
					System.out.println("            AmazonOrderId");
					System.out.println();
					System.out.println("                "
							+ listOrderItemsByNextTokenResult
									.getAmazonOrderId());
					System.out.println();
				}
				if (listOrderItemsByNextTokenResult.isSetOrderItems()) {
					System.out.println("            OrderItems");
					System.out.println();
					OrderItemList orderItems = listOrderItemsByNextTokenResult
							.getOrderItems();
					java.util.List<OrderItem> orderItemList = orderItems
							.getOrderItem();
					for (OrderItem orderItem : orderItemList) {
						System.out.println("                OrderItem");
						System.out.println();
						if (orderItem.isSetASIN()) {
							System.out.println("                    ASIN");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getASIN());
							System.out.println();
						}
						if (orderItem.isSetSellerSKU()) {
							System.out.println("                    SellerSKU");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getSellerSKU());
							System.out.println();
						}
						if (orderItem.isSetTitle()) {
							System.out.println("                    Title");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getTitle());
							System.out.println();
						}
						if (orderItem.isSetQuantityOrdered()) {
							System.out
									.println("                    QuantityOrdered");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getQuantityOrdered());
							System.out.println();
						}
						if (orderItem.isSetQuantityShipped()) {
							System.out
									.println("                    QuantityShipped");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getQuantityShipped());
							System.out.println();
						}
						if (orderItem.isSetGiftMessageText()) {
							System.out
									.println("                    GiftMessageText");
							System.out.println();
							System.out.println("                        "
									+ orderItem.getGiftMessageText());
							System.out.println();
						}
						if (orderItem.isSetItemPrice()) {
							System.out.println("                    ItemPrice");
							System.out.println();
							Money itemPrice = orderItem.getItemPrice();
							if (itemPrice.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ itemPrice.getCurrencyCode());
								System.out.println();
							}
							if (itemPrice.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ itemPrice.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetShippingPrice()) {
							System.out
									.println("                    ShippingPrice");
							System.out.println();
							Money shippingPrice = orderItem.getShippingPrice();
							if (shippingPrice.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ shippingPrice
														.getCurrencyCode());
								System.out.println();
							}
							if (shippingPrice.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ shippingPrice.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetGiftWrapPrice()) {
							System.out
									.println("                    GiftWrapPrice");
							System.out.println();
							Money giftWrapPrice = orderItem.getGiftWrapPrice();
							if (giftWrapPrice.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ giftWrapPrice
														.getCurrencyCode());
								System.out.println();
							}
							if (giftWrapPrice.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ giftWrapPrice.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetItemTax()) {
							System.out.println("                    ItemTax");
							System.out.println();
							Money itemTax = orderItem.getItemTax();
							if (itemTax.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ itemTax.getCurrencyCode());
								System.out.println();
							}
							if (itemTax.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ itemTax.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetShippingTax()) {
							System.out
									.println("                    ShippingTax");
							System.out.println();
							Money shippingTax = orderItem.getShippingTax();
							if (shippingTax.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ shippingTax.getCurrencyCode());
								System.out.println();
							}
							if (shippingTax.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ shippingTax.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetGiftWrapTax()) {
							System.out
									.println("                    GiftWrapTax");
							System.out.println();
							Money giftWrapTax = orderItem.getGiftWrapTax();
							if (giftWrapTax.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ giftWrapTax.getCurrencyCode());
								System.out.println();
							}
							if (giftWrapTax.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ giftWrapTax.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetShippingDiscount()) {
							System.out
									.println("                    ShippingDiscount");
							System.out.println();
							Money shippingDiscount = orderItem
									.getShippingDiscount();
							if (shippingDiscount.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ shippingDiscount
														.getCurrencyCode());
								System.out.println();
							}
							if (shippingDiscount.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ shippingDiscount.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetPromotionDiscount()) {
							System.out
									.println("                    PromotionDiscount");
							System.out.println();
							Money promotionDiscount = orderItem
									.getPromotionDiscount();
							if (promotionDiscount.isSetCurrencyCode()) {
								System.out
										.println("                        CurrencyCode");
								System.out.println();
								System.out
										.println("                            "
												+ promotionDiscount
														.getCurrencyCode());
								System.out.println();
							}
							if (promotionDiscount.isSetAmount()) {
								System.out
										.println("                        Amount");
								System.out.println();
								System.out
										.println("                            "
												+ promotionDiscount.getAmount());
								System.out.println();
							}
						}
						if (orderItem.isSetPromotionIds()) {
							System.out
									.println("                    PromotionIds");
							System.out.println();
							PromotionIdList promotionIds = orderItem
									.getPromotionIds();
							java.util.List<String> promotionIdList = promotionIds
									.getPromotionId();
							for (String promotionId : promotionIdList) {
								System.out
										.println("                        PromotionId");
								System.out.println();
								System.out
										.println("                            "
												+ promotionId);
							}
						}
					}
				}
			}
			if (response.isSetResponseMetadata()) {
				System.out.println("        ResponseMetadata");
				System.out.println();
				ResponseMetadata responseMetadata = response
						.getResponseMetadata();
				if (responseMetadata.isSetRequestId()) {
					System.out.println("            RequestId");
					System.out.println();
					System.out.println("                "
							+ responseMetadata.getRequestId());
					System.out.println();
				}
			}
			System.out.println();

		} catch (MarketplaceWebServiceOrdersException ex) {

			System.out.println("Caught Exception: " + ex.getMessage());
			System.out.println("Response Status Code: " + ex.getStatusCode());
			System.out.println("Error Code: " + ex.getErrorCode());
			System.out.println("Error Type: " + ex.getErrorType());
			System.out.println("Request ID: " + ex.getRequestId());
			System.out.print("XML: " + ex.getXML());
		}
	}

}
