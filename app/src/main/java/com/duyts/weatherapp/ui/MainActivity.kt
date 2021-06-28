package com.duyts.weatherapp.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.duyts.weatherapp.adapter.WeatherAdapter
import com.duyts.weatherapp.databinding.ActivityMainBinding
import com.duyts.weatherapp.model.WeatherResponse
import com.duyts.weatherapp.repository.AppRepository
import com.duyts.weatherapp.util.Resource
import com.duyts.weatherapp.viewmodel.MainViewModel
import com.duyts.weatherapp.viewmodel.ViewModelProviderFactory

class MainActivity : BaseActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: ActivityMainBinding


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

            submitButton.setOnClickListener {
                viewModel.getWeather(locationEditText.text.toString(), 10)
            }
        }
    }

    override fun initLogic() {
        super.initLogic()

        viewModel.weather.observe(this, { it ->
            it.getContentIfNotHandled().let { response ->
                val data: WeatherResponse? = response?.data
                when (response) {
                    is Resource.Success -> {
                        viewBinding.progressBar.visibility = View.GONE
                        Log.d("CHRIS", data.toString())
                        viewBinding.run {
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

        viewModel.getWeather("Ho chi minh", 10)
    }
}