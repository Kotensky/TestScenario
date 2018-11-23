package com.kotensky.testscenario.presenter

import com.kotensky.testscenario.interfaces.view.ScenarioView
import com.kotensky.testscenario.model.entities.ScenarioEntity
import com.kotensky.testscenario.model.room.dao.ScenarioDao
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ScenarioPresenter @Inject constructor(
        private val scenarioDao: ScenarioDao) : BasePresenter<ScenarioView>() {

    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()


    fun addScenario(scenarioEntity: ScenarioEntity) {
        Completable.fromAction { scenarioDao.insertScenario(scenarioEntity) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable?.add(d)
                    }

                    override fun onComplete() {
                        view?.onScenarioAdded()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }

    fun getAllScenarios() {
        scenarioDao.getAllScenarios()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<ScenarioEntity?>?> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable?.add(d)
                    }

                    override fun onSuccess(scenarios: List<ScenarioEntity?>) {
                        view?.showScenarios(scenarios)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }

    fun getScenario(id: Long) {
        scenarioDao.getScenario(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ScenarioEntity?> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable?.add(d)
                    }

                    override fun onSuccess(scenarioEntity: ScenarioEntity) {
                        view?.showScenario(scenarioEntity)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }



    override fun cancel() {
        if (!(compositeDisposable?.isDisposed as Boolean)) {
            compositeDisposable.dispose()
        }
    }

    override fun destroy() {
        cancel()
        view = null
    }
}