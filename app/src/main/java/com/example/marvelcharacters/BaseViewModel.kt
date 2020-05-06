package com.example.marvelcharacters

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    protected fun launchData(
        enableLoading: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch {
            try {
                if (enableLoading) {
                    loading.postValue(true)
                }
                block()
            } catch (throwable: Throwable) {
                handleError(throwable)
            } finally {
                if (enableLoading) {
                    loading.postValue(false)
                }
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        loading.postValue(false)
        val context = get<Context>()
        val errorMsg = when (throwable) {
            is HttpException -> when (throwable.code()) {
                401 -> context.getString(R.string.error_message_unauthorized)
                409 -> throwable.message()
                else -> context.getString(R.string.error_message)
            }
            is UnknownHostException -> context.getString(R.string.error_message_unknown_host)
            else -> context.getString(R.string.error_message)
        }
        error.postValue(errorMsg)
    }
}