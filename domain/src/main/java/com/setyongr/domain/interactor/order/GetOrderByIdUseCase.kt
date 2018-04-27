package com.setyongr.domain.interactor.order

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Order
import com.setyongr.domain.repository.OrderRepository
import io.reactivex.Single
import javax.inject.Inject

class GetOrderByIdUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCase<Single<Order>, Int> {
    override fun execute(params: Int): Single<Order> {
        return orderRepository.getOrderById(params)
                .applyDefaultSchedulers(schedulerProvider)
    }
}