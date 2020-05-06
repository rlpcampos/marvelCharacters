package com.example.marvelcharacters.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ConnectHandler : ConnectivityManager.NetworkCallback() {

    private val _connectivityLiveData = MutableLiveData<Boolean>()
    val connectivityLiveData: LiveData<Boolean>
        get() = _connectivityLiveData

    fun registerCallback(context: Context) {
        val cm = getConnectivityManager(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(this)
        } else {
            cm.registerNetworkCallback(
                NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()
                , this
            )
        }
    }

    fun unRegisterCallback(context: Context) {
        getConnectivityManager(context).unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        _connectivityLiveData.postValue(true)
    }

    override fun onUnavailable() {
        _connectivityLiveData.postValue(false)
    }

    override fun onLost(network: Network) {
        _connectivityLiveData.postValue(false)
    }


    private fun getConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}