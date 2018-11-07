package com.example.maikbiskup.moviefinder.model.data_access.remote

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object TransformerApplySchedulers {

    fun <T> singleApplySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { single ->
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> observableApplySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer{ observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun completableApplySchedulers(): CompletableTransformer {
        return CompletableTransformer{ observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}