package com.kotensky.testscenario.presenter

abstract class BasePresenter<T> {

    var view: T? = null

    abstract fun cancel()

    abstract fun destroy()

}