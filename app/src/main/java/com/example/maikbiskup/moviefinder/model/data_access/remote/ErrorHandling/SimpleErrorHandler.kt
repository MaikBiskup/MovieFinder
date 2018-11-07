package de.vectron_systems_ag.gethappy.consumer.rest.retrofit.ErrorHandling

import android.util.Log
import com.example.maikbiskup.moviefinder.model.data_access.remote.BaseController
import com.example.maikbiskup.moviefinder.model.data_access.remote.ErrorHandling.ExtendedErrorHandling

open class SimpleErrorHandler : io.reactivex.functions.Consumer<Throwable> {

    private var stackTraceElement: StackTraceElement = Exception().stackTrace[1]

    private var extendedErrorHandling: ExtendedErrorHandling? = null

    override fun accept(throwable: Throwable) {
        Log.d(BaseController.LOG_KEY, prepStackTraceOutput() + ": " + throwable.message)

        extendedErrorHandling?.onError()
    }

    private fun prepStackTraceOutput() : String{

        return stackTraceElement.className + "/" + stackTraceElement.methodName + " " + stackTraceElement.lineNumber
    }

    fun setExtendedErrorHandling(extendedErrorHandling: ExtendedErrorHandling){
        this.extendedErrorHandling = extendedErrorHandling
    }

}
