package com.z1.pokedex.core.network.services.googlebilling

import android.app.Activity
import android.widget.Toast
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.z1.pokedex.core.common.model.google.SubscriptionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoogleBillingClientImpl(
    private val activity: Activity
) : GoogleBillingClient {

    private val _subscriptionState = MutableStateFlow(SubscriptionState())
    override val subscriptionState get() = _subscriptionState.asStateFlow()

    private var billingClient: BillingClient? = null

    private val purchasesUpdateListener = PurchasesUpdatedListener { result, purchases ->

        when (result.responseCode) {
            BillingResponseCode.OK -> {
                purchases?.forEach { handlePurchase(it) }
            }

            BillingResponseCode.USER_CANCELED -> {
            }

            BillingResponseCode.ERROR -> {
            }

            BillingResponseCode.NETWORK_ERROR -> {
            }

            BillingResponseCode.BILLING_UNAVAILABLE -> {
            }

            else -> {
                Toast.makeText(activity, "Subscribed", Toast.LENGTH_SHORT).show()
                _subscriptionState.update {
                    it.copy(subscriptions = listOf("4JaceqoeVBPSptqnM839iFyM0k52"))
                }
            }
        }
    }

    init {
        setupBillingClient()
    }

    private fun handlePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val listener = ConsumeResponseListener { billingResult, s -> }

        billingClient?.consumeAsync(consumeParams, listener)

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (purchase.isAcknowledged.not()) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams
                    .newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                    if (billingResult.responseCode == BillingResponseCode.OK) {
                        _subscriptionState.update {
                            it.copy(subscriptions = purchase.products)
                        }
                    }
                }
            } else {
                _subscriptionState.update {
                    it.copy(subscriptions = purchase.products)
                }
            }
        }
    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchasesUpdateListener)
            .enablePendingPurchases()
            .build()
        billingStartConnection()
    }

    private fun billingStartConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                // Handle billing service disconnection
            }

            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingResponseCode.OK) {
                    hasSubscription()
                }
            }
        })
    }

    private fun hasSubscription() {
        val queryPurchaseParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        billingClient?.queryPurchasesAsync(
            queryPurchaseParams
        ) { result, purchases ->
            when (result.responseCode) {
                BillingResponseCode.OK -> {
                    purchases.forEach { purchase ->
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                            // User has an active subscription
                            _subscriptionState.update {
                                it.copy(subscriptions = purchase.products)
                            }
                            return@queryPurchasesAsync
                        }
                    }
                }

                BillingResponseCode.USER_CANCELED -> {
                    // User canceled the purchase
                }

                else -> {
                    // Handle other error cases
                }
            }

            // User does not have an active subscription
        }
    }

    private fun querySubscriptionPlans(
        subscriptionPlanId: String,
        accountId: String
    ) {
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("teste_pokedex_premium")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build(),
                    )
                )
                .build()

        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingResponseCode.OK) {
                var offerToken = ""
                val productDetails = productDetailsList.firstOrNull { productDetails ->
                    productDetails.subscriptionOfferDetails?.any {
                        if (it.basePlanId == subscriptionPlanId) {
                            offerToken = it.offerToken
                            true
                        } else false
                    } == true
                }
                productDetails?.let {
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(it)
                            .setOfferToken(offerToken)
                            .build()
                    )

                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .setObfuscatedAccountId(accountId)
                        .setObfuscatedProfileId("$accountId@$subscriptionPlanId")
                        .build()
                    billingClient?.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }

    override fun checkSubscriptionStatus(
        subscriptionPlanId: String,
        accountId: String
    ) {
        val queryPurchaseParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        billingClient?.queryPurchasesAsync(
            queryPurchaseParams
        ) { result, purchases ->
            when (result.responseCode) {
                BillingResponseCode.OK -> {
                    purchases.forEach { purchase ->
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED &&
                            purchase.products.contains(subscriptionPlanId) &&
                            purchase.accountIdentifiers?.obfuscatedAccountId == accountId
                        ) {
                            _subscriptionState.update {
                                it.copy(subscriptions = purchase.products)
                            }
                            return@queryPurchasesAsync
                        }
                    }
                }

                BillingResponseCode.USER_CANCELED -> {
                    // User canceled the purchase
                }

                else -> {
                    // Handle other error cases
                }
            }
            // User does not have an active subscription
            querySubscriptionPlans(subscriptionPlanId, accountId)
        }
    }
}