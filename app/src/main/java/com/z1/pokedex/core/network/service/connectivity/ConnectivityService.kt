package com.z1.pokedex.core.network.service.connectivity

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

interface ConnectivityService {
    val connectivityManager: ConnectivityManager
    val isConnected: Flow<Boolean>
}