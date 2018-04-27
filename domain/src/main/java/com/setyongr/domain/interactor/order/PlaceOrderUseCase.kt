package com.setyongr.domain.interactor.order

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Order
import com.setyongr.domain.repository.OrderRepository
import io.reactivex.Single
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCase<Single<Order>, PlaceOrderUseCase.OrderParam> {
    override fun execute(params: OrderParam): Single<Order> {
        return orderRepository.placeOrder(params.id, params.message)
                .applyDefaultSchedulers(schedulerProvider)
    }

    class OrderParam(val id: Int, val message: String)

}