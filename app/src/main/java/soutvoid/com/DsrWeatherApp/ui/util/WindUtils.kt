package soutvoid.com.DsrWeatherApp.ui.util

import android.content.Context
import soutvoid.com.DsrWeatherApp.R

object WindUtils {

    /**
     * позволяет получить строковое представление "исток" ветра по градусам (N, S, etc)
     * @see R.array.directions
     */
    fun getFromByDegrees(degrees: Double, context: Context) : String {
        return context.resources.getStringArray(R.array.directions)[ Math.round((degrees + 180) % 360 / 45).toInt() % 8 ]
    }

}