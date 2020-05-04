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

abstract class BaseViewModel : ViewModel(), KoinComponent{

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
        val errorMsg = context.getString(R.string.error_message)
        error.postValue(errorMsg)
    }
}