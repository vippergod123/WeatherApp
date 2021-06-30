package com.duyts.weather.domain.usecase

abstract class BaseUsecase<Param, Result> {

    abstract suspend operator fun invoke(param: Param): Result
}

abstract class BaseUsecaseNoParam<Result> {
    abstract suspend operator fun invoke(): Result
}