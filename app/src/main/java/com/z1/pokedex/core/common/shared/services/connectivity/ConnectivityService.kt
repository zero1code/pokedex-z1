package com.z1.pokedex.core.common.shared.services.connectivity

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

interface ConnectivityService {
    val connectivityManager: ConnectivityManager
    val isConnected: Flow<Boolean>
}