package com.duyts.weatherapp.ui

import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.usecase.GetWeathersForecast
import com.duyts.weatherapp.R
import com.duyts.weatherapp.adapter.WeatherAdapter
import com.duyts.weatherapp.databinding.ActivityMainBinding
import com.duyts.weatherapp.util.Utils
import com.duyts.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {


    private val viewModel: MainViewModel by viewModel()
    private lateinit var viewBinding: ActivityMainBinding

    companion object {
        private const val DEFAULT_AUTO_HIDE_SEEKBAR_DURATION: Long = 2000
        private const val DEFAULT_TEXT_VIEW_SIZE = 24
        private const val DEFAULT_LOCATION_ID = 524901
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchIcon = menu?.findItem(R.id.search)
        val searchView: SearchView = searchIcon?.actionView as SearchView
        searchView.let {
            it.queryHint = "Find city"
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchIcon.collapseActionView()
                    searchView.clearFocus()
                    viewModel.getCurrentWeather(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.textZoom -> {
                viewBinding.seekBar.let {
                    it.visibility = View.VISIBLE
                    it.postDelayed(autoHideSeekBarDelay(it), DEFAULT_AUTO_HIDE_SEEKBAR_DURATION)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initFirst() {
        super.initFirst()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun initView() {
        super.initView()
        viewBinding.run {
            weatherRecyclerView.run {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }


            seekBar.progress = DEFAULT_TEXT_VIEW_SIZE
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    (weatherRecyclerView.adapter as WeatherAdapter).setTextSize(progress.toFloat())
                    viewBinding.let {
                        currentLocationTextView.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP,
                            progress.toFloat()
                        )
                        currentDescriptionTextView.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP,
                            progress.toFloat()
                        )
                        currentDateTextView.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP,
                            progress.toFloat()
                        )
                        currentTemperatureTextView.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP,
                            progress.toFloat()
                        )
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    viewBinding.seekBar.let { it.removeCallbacks(autoHideSeekBarDelay(it)) }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    viewBinding.seekBar.let {
                        it.postDelayed(autoHideSeekBarDelay(it), DEFAULT_AUTO_HIDE_SEEKBAR_DURATION)
                    }
                }
            })
        }

    }

    override fun initLogic() {
        super.initLogic()
        viewModel.weatherForecast.observe(this, { event ->
            event.getContentIfNotHandled()?.run {
                when (this) {
                    is ResponseHandler.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                    is ResponseHandler.Success -> {
                        viewBinding.let {
                            it.progressBar.visibility = View.GONE
                            it.weatherRecyclerView.adapter = WeatherAdapter(
                                applicationContext,
                                data.list
                            )
                        }
                    }
                    is ResponseHandler.Failure -> viewBinding.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.currentWeather.observe(this, { event ->
            event.getContentIfNotHandled()?.run {
                when (this) {
                    is ResponseHandler.Success -> {
                        val param = GetWeathersForecast.GetWeathersForecastParam(this.data.id,10)
                        viewModel.getWeatherForecast(param)
                        updateCurrentWeatherView(this.data)
                    }

                    is ResponseHandler.Failure -> {
                        viewBinding.progressBar.visibility = View.GONE
                    }

                    is ResponseHandler.Loading -> {
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.getCurrentWeather("Ho chi minh")
        val getWeathersForecastParam = GetWeathersForecast.GetWeathersForecastParam(DEFAULT_LOCATION_ID, 10)
        viewModel.getWeatherForecast(getWeathersForecastParam)
    }

    private lateinit var autoHideSeekBarRunnable: Runnable
    fun autoHideSeekBarDelay(view: SeekBar): Runnable {
        if (!this::autoHideSeekBarRunnable.isInitialized) {
            autoHideSeekBarRunnable = Runnable {
                view.visibility = View.GONE
            }
        }
        return autoHideSeekBarRunnable
    }

    private fun updateCurrentWeatherView(item: CurrentWeather) {
        viewBinding.let {
            it.progressBar.visibility = View.GONE
            it.currentDateTextView.text =
                Utils.milisecondsToDate(item.dt.toLong(), "MMMM D, hh:mm")
            it.currentTemperatureTextView.text = item.main.temp.toString()
            it.currentDescriptionTextView.text = "Unknown!" //item.weather.get(0).main
            it.currentLocationTextView.text = item.name
//            Glide.with(this@MainActivity)
//                .load("http://openweathermap.org/img/wn/${item.weather.get(0).icon}@2x.png")
//                .into(it.currentWeatherImageView)
        }
    }
}