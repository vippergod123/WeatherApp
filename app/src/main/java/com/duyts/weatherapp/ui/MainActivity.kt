package com.duyts.weatherapp.ui

import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.duyts.weatherapp.R
import com.duyts.weatherapp.adapter.WeatherAdapter
import com.duyts.weatherapp.databinding.ActivityMainBinding
import com.duyts.weatherapp.model.CurrentWeatherResponse
import com.duyts.weatherapp.model.WeatherForecastResponse
import com.duyts.weatherapp.repository.AppRepository
import com.duyts.weatherapp.util.Resource
import com.duyts.weatherapp.util.Utils
import com.duyts.weatherapp.viewmodel.MainViewModel
import com.duyts.weatherapp.viewmodel.ViewModelProviderFactory

class MainActivity : BaseActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: ActivityMainBinding

    companion object {
        private const val DEFAULT_AUTO_HIDE_SEEKBAR_DURATION: Long = 2000
        private const val DEFAULT_TEXT_VIEW_SIZE = 24;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchIcon = menu?.findItem(R.id.search)
        val searchView: SearchView = searchIcon?.actionView as SearchView
        searchView.let {
            it.queryHint = "Find something"
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getCurrentWeather(query ?: "")
                    viewModel.getWeatherForecast(query ?: "", 10)
                    searchIcon.collapseActionView()
                    searchView.clearFocus()
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

        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
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
                        currentLocationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())
                        currentDescriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())
                        currentDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())
                        currentTemperatureTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())
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

        viewModel.weatherForecast.observe(this, { it ->
            it.getContentIfNotHandled().let { response ->
                val data: WeatherForecastResponse? = response?.data
                when (response) {
                    is Resource.Success -> {
                        viewBinding.run {
                            progressBar.visibility = View.GONE
                            weatherRecyclerView.apply {
                                adapter = WeatherAdapter(
                                    applicationContext,
                                    data?.list ?: emptyList()
                                )
                            }
                        }
                    }
                    is Resource.Failed -> {
                        viewBinding.progressBar.visibility = View.GONE
                        Log.e("CHRIS", response.data.toString())
                    }

                    is Resource.Loading -> {
                        Log.w("CHRIS", response.data.toString())
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.currentWeather.observe(this, { it ->
            it.getContentIfNotHandled().let { response ->
                val data: CurrentWeatherResponse? = response?.data
                when (response) {
                    is Resource.Success -> {
                        viewBinding.let {
                            it.progressBar.visibility = View.GONE
                            data?.let { data ->
                                it.currentDateTextView.text =
                                    Utils.milisecondsToDate(data.dt.toLong(), "MMMM D, hh:mm")
                                it.currentTemperatureTextView.text = data.main?.temp.toString()
                                it.currentDescriptionTextView.text = data.weather?.get(0)?.main
                                it.currentLocationTextView.text = data.name
                                Glide.with(this@MainActivity)
                                    .load("http://openweathermap.org/img/wn/${data.weather?.get(0)?.icon}@2x.png")
                                    .into(it.currentWeatherImageView)
                            }


                        }
                    }
                    is Resource.Failed -> {
                        viewBinding.progressBar.visibility = View.GONE
                        Log.e("CHRIS", response.data.toString())
                    }

                    is Resource.Loading -> {
                        Log.w("CHRIS", response.data.toString())
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.getWeatherForecast("Ho chi minh", 10)
        viewModel.getCurrentWeather("Ho chi minh")
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
}