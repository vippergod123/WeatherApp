package com.duyts.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirst()
        initView()
    }

    override fun onResume() {
        super.onResume()
        initLogic()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearUp()
    }

    open fun clearUp() {

    }

    open fun initLogic() {

    }

    open fun initView() {

    }

    open fun initFirst() {

    }
}


