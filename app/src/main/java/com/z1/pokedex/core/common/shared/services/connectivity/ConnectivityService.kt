package com.z1.pokedex.core.common.shared.services.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityService {
    val isConnected: Flow<Boolean>
}