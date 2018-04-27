package com.setyongr.domain.interactor.order

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Order
import com.setyongr.domain.repository.OrderRepository
import io.reactivex.Single
import javax.inject.Inject

class GetOrdersListUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCaseOptParam<Single<List<Order>>, Void?> {
    override fun execute(params: Void?): Single<List<Order>> {
        return orderRepository.getOrders()
                .applyDefaultSchedulers(schedulerProvider)
    }
}