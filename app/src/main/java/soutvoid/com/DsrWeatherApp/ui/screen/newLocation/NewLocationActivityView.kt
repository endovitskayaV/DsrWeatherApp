package soutvoid.com.DsrWeatherApp.ui.screen.newLocation

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import com.agna.ferro.mvp.component.ScreenComponent
import kotlinx.android.synthetic.main.activity_new_location.*
import soutvoid.com.DsrWeatherApp.R
import soutvoid.com.DsrWeatherApp.ui.base.activity.BaseActivityView
import soutvoid.com.DsrWeatherApp.ui.base.activity.BasePresenter
import soutvoid.com.DsrWeatherApp.ui.base.activity.TranslucentStatusActivityView
import soutvoid.com.DsrWeatherApp.ui.screen.locations.LocationsActivityView
import soutvoid.com.DsrWeatherApp.ui.screen.newLocation.stepper.StepperAdapter
import soutvoid.com.DsrWeatherApp.ui.util.AnimationEndedListener
import soutvoid.com.DsrWeatherApp.util.SdkUtil
import javax.inject.Inject

class NewLocationActivityView : TranslucentStatusActivityView() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, NewLocationActivityView::class.java))
        }
    }

    @Inject
    lateinit var presenter: NewLocationActivityPresenter

    override fun getPresenter(): BasePresenter<*> = presenter

    override fun getName(): String = "NewLocation"

    override fun getContentView(): Int = R.layout.activity_new_location

    override fun createScreenComponent(): ScreenComponent<*> {
        return DaggerNewLocationActivityComponent.builder()
                .activityModule(activityModule)
                .appComponent(appComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        super.onCreate(savedInstanceState, viewRecreated)

        if (!SdkUtil.supportsKitkat())
            new_location_fake_statusbar.visibility = View.GONE

        initToolbar()
        initStepper()
    }

    private fun initToolbar() {
        setSupportActionBar(new_location_toolbar)
        title = getString(R.string.new_location)
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.themedBackDrawable, typedValue, true)
        new_location_toolbar.navigationIcon = ContextCompat.getDrawable(this, typedValue.resourceId)
        new_location_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initStepper() {
        new_location_stepper.adapter = StepperAdapter(supportFragmentManager, this)
    }

    fun returnToHome(fabPoint: Point = Point()) {
        if (SdkUtil.supportsKitkat()) {
            val animator = ViewAnimationUtils.createCircularReveal(
                    new_location_reveal_view,
                    fabPoint.x,
                    new_location_stepper.top + fabPoint.y,
                    0f,
                    maxOf(new_location_reveal_view.measuredHeight, new_location_reveal_view.measuredWidth).toFloat()
                    )
            animator.addListener(AnimationEndedListener {
                startLocationsActivity()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            })
            new_location_reveal_view.visibility = View.VISIBLE
            animator.start()
        } else startLocationsActivity()
    }

    private fun startLocationsActivity() {
        val intent = Intent(this, LocationsActivityView::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}