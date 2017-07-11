package soutvoid.com.DsrWeatherApp.app.dagger

import android.content.Context
import com.agna.ferro.mvp.component.scope.PerApplication
import dagger.Component
import soutvoid.com.DsrWeatherApp.interactor.common.network.NetworkModule
import soutvoid.com.DsrWeatherApp.interactor.common.network.OkHttpModule
import soutvoid.com.DsrWeatherApp.interactor.common.network.cache.CacheModule
import soutvoid.com.DsrWeatherApp.interactor.currentWeather.CurrentWeatherModule
import soutvoid.com.DsrWeatherApp.interactor.currentWeather.CurrentWeatherRepository
import soutvoid.com.DsrWeatherApp.interactor.network.NetworkConnectionChecker
import soutvoid.com.DsrWeatherApp.ui.base.activity.ActivityModule

@PerApplication
@Component(modules = arrayOf(
        AppModule::class,
        OkHttpModule::class,
        NetworkModule::class,
        CacheModule::class,
        ActivityModule::class,
        CurrentWeatherModule::class
))
interface AppComponent {
    fun context() : Context
    fun networkConnectionChecker() : NetworkConnectionChecker
    fun currentWeatherRepository() : CurrentWeatherRepository
}