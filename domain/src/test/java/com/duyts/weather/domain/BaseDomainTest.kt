package com.duyts.weather.domain

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseDomainTest {

    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
}