package com.z1.pokedex.core.network.service.googlebilling

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.staticCompositionLocalOf
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val LocalGoogleBillingClient = staticCompositionLocalOf<GoogleBillingClient> {
    error("CompositionLocal GoogleBillingClient not present")
}

const val TAG = "GoogleBillingClient"

class GoogleBillingClient(private val activity: Activity) {

    private val _subscriptionState = MutableStateFlow(SubscriptionState())
    val subscriptionState get() = _subscriptionState.asStateFlow()

    private var billingClient: BillingClient? = null

    private val purchasesUpdateListener = PurchasesUpdatedListener { result, purchases ->

        when (result.responseCode) {
            BillingResponseCode.OK -> {
                Toast.makeText(activity, "Billing OK ${purchases?.size}", Toast.LENGTH_SHORT).show()
                purchases?.forEach { handlePurchase(it) }
            }
            BillingResponseCode.USER_CANCELED -> {
                Toast.makeText(activity, "User canceled purchase", Toast.LENGTH_SHORT).show()
            }
            BillingResponseCode.ERROR -> {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }
            BillingResponseCode.NETWORK_ERROR -> {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
            BillingResponseCode.BILLING_UNAVAILABLE -> {
                Toast.makeText(activity, "Billing Unavailable", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(activity, "Error: ${result.debugMessage} cod: ${result.responseCode}", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(activity,"user purchased subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "user purchased subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}")
                    }
                }
            } else {
                _subscriptionState.update {
                    it.copy(subscriptions = purchase.products)
                }
                Toast.makeText(activity,"user purchased subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "user purchased subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}")
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
                Toast.makeText(activity, "Error billing disconnected", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Error billing disconnected")
            }

            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingResponseCode.OK) {
                    Log.d(TAG, "Ok checking subscription")
                    Toast.makeText(activity, "Ok checking subscription", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(activity, "has subscription ${purchases.size}", Toast.LENGTH_SHORT).show()
                    purchases.forEach { purchase ->
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                            // User has an active subscription
                            _subscriptionState.update {
                                it.copy(subscriptions = purchase.products)
                            }
                            Toast.makeText(activity,"user has subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(activity,"accountId: ${purchase.accountIdentifiers?.obfuscatedAccountId}\nprofileId ${purchase.accountIdentifiers?.obfuscatedProfileId}", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "user has subscription: ${purchase.products}\ntoken ${purchase.purchaseToken}")
                            Log.d(TAG, "accountId: ${purchase.accountIdentifiers?.obfuscatedAccountId}\nprofileId ${purchase.accountIdentifiers?.obfuscatedProfileId}")
                            return@queryPurchasesAsync
                        }
                    }
                }

                BillingResponseCode.USER_CANCELED -> {
                    Toast.makeText(activity,"User canceled purchase", Toast.LENGTH_SHORT).show()
                    // User canceled the purchase
                }

                else -> {
                    Toast.makeText(activity,"some error ${result.debugMessage} ${result.responseCode}", Toast.LENGTH_SHORT).show()
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
                        }
                        else false
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
                    Log.d(TAG, "querySubscriptionPlans accountId: $accountId")
                    Log.d(TAG, "querySubscriptionPlans profileId: $accountId@$subscriptionPlanId")
                    billingClient?.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }

    fun checkSubscriptionStatus(
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
                            Toast.makeText(activity, "user has subscription", Toast.LENGTH_SHORT).show()
                            _subscriptionState.update {
                                it.copy(subscriptions = purchase.products)
                            }
                            return@queryPurchasesAsync
                        } else {
                            Toast.makeText(activity, "user dont have a subscription", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                BillingResponseCode.USER_CANCELED -> {
                    // User canceled the purchase
                    Toast.makeText(activity, "user canceled subscription", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    // Handle other error cases
                    Toast.makeText(activity, "error: ${result.debugMessage} ${result.responseCode}", Toast.LENGTH_SHORT).show()
                }
            }
            // User does not have an active subscription
            querySubscriptionPlans(subscriptionPlanId, accountId)
        }
    }
}