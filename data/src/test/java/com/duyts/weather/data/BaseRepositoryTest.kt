package com.duyts.weather.data

import android.content.Context
import androidx.annotation.CallSuper
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


abstract class BaseRepositoryTest {


    @Mock
    protected lateinit var fakeContext: Context

    @CallSuper
    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        resetMock()
    }

    protected fun resetMock() {

    }


    fun <T> anyType(type: Class<T>): T {
        Mockito.any(type)
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}