package soutvoid.com.DsrWeatherApp.ui.screen.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.agna.ferro.mvp.component.ScreenComponent
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.activity_main.*
import soutvoid.com.DsrWeatherApp.R
import soutvoid.com.DsrWeatherApp.domain.CurrentWeather
import soutvoid.com.DsrWeatherApp.domain.DailyForecast
import soutvoid.com.DsrWeatherApp.domain.Forecast
import soutvoid.com.DsrWeatherApp.domain.ultraviolet.Ultraviolet
import soutvoid.com.DsrWeatherApp.ui.base.activity.BasePresenter
import soutvoid.com.DsrWeatherApp.ui.base.activity.TranslucentStatusActivityView
import soutvoid.com.DsrWeatherApp.ui.screen.main.data.AllWeatherData
import soutvoid.com.DsrWeatherApp.ui.screen.main.widgets.DayForecastView
import soutvoid.com.DsrWeatherApp.ui.util.UnitsUtils
import soutvoid.com.DsrWeatherApp.ui.util.WeatherIconsHelper
import soutvoid.com.DsrWeatherApp.ui.util.WindUtils
import soutvoid.com.DsrWeatherApp.ui.util.getThemeColor
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.layout_current_weather.*

class MainActivityView : TranslucentStatusActivityView() {

    @Inject
    lateinit var presenter : MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        super.onCreate(savedInstanceState, viewRecreated)
        initSwipeRefresh()
    }

    override fun getPresenter(): BasePresenter<*> = presenter

    override fun getName(): String = "MainActivity"

    override fun getContentView(): Int = R.layout.activity_main

    override fun createScreenComponent(): ScreenComponent<*> {
        return DaggerMainActivityComponent.builder()
                .activityModule(activityModule)
                .appComponent(appComponent)
                .build()
    }

    private fun initSwipeRefresh() {
        main_refresh_layout.setOnRefreshListener { presenter.refresh() }
    }

    fun fillAllData(allWeatherData: AllWeatherData) {
        fillCurrentWeatherData(allWeatherData.currentWeather)
        fillForecastData(allWeatherData.forecast)
        fillUviData(allWeatherData.ultraviolet)
    }

    fun fillCurrentWeatherData(currentWeather: CurrentWeather) {
        with(currentWeather) {
            val primaryTextColor = theme.getThemeColor(android.R.attr.textColorPrimary)
            main_city_tv.text = cityName
            main_temp_tv.text = "${Math.round(main.temperature)} ${UnitsUtils.getDegreesUnits(this@MainActivityView)}"
            main_icon_iv.setImageDrawable(IconicsDrawable(this@MainActivityView)
                    .icon(WeatherIconsHelper.getWeatherIcon(weather.first().id, timeOfData, sys.sunrise, sys.sunset))
                    .color(primaryTextColor)
                    .sizeDp(100))
            main_description_tv.text = weather.first().description
            main_wind_speed_tv.text = "${wind.speed} ${UnitsUtils.getVelocityUnits(this@MainActivityView)}"
            main_wind_direction_tv.text = WindUtils.getByDegrees(wind.degrees, this@MainActivityView)
            main_pressure_tv.text = ": ${main.pressure} ${UnitsUtils.getPressureUnits(this@MainActivityView)}"
            main_humidity_tv.text = ": ${main.humidity}%"
        }

    }

    fun fillForecastData(forecast: Forecast) {
        main_forecast.setWeather(forecast.list.filterIndexed { index, _ -> index % 2 == 0 }.take(4))
    }

    fun fillUviData(ultraviolet: Ultraviolet) {
        main_uv_tv.text = ": ${ultraviolet.value}"
    }

    fun setProgressBarEnabled(enabled: Boolean) {
        main_refresh_layout.isRefreshing = enabled
    }
}