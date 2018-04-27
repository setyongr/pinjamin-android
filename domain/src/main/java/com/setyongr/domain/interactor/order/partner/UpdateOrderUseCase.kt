package com.setyongr.domain.interactor.order.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Order
import com.setyongr.domain.model.OrderUpdate
import com.setyongr.domain.repository.OrderRepository
import com.setyongr.domain.repository.PartnerOrderRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val partnerOrderRepository: PartnerOrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCase<Single<Order>, OrderUpdate> {
    override fun execute(params: OrderUpdate): Single<Order> {
        return partnerOrderRepository.updateOrder(params.id, params)
                .applyDefaultSchedulers(schedulerProvider)
    }
}