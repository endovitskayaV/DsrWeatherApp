package soutvoid.com.DsrWeatherApp.interactor.triggers.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import soutvoid.com.DsrWeatherApp.app.dagger.AppComponent
import soutvoid.com.DsrWeatherApp.interactor.triggers.TriggersRepository
import javax.inject.Inject

abstract class BaseTriggerJob(params: Params): Job(params) {

    @Inject
    lateinit var triggerRep: TriggersRepository

    open fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

}