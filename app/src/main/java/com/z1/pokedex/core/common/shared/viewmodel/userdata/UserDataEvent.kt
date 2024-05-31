package com.z1.pokedex.core.common.shared.viewmodel.userdata

sealed class UserDataEvent {
    data object GetSignedInUser : UserDataEvent()
    data object SignOut : UserDataEvent()
}