package com.setyongr.domain.interactor.order.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Order
import com.setyongr.domain.repository.OrderRepository
import com.setyongr.domain.repository.PartnerOrderRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPartnerOrdersListUseCase @Inject constructor(
    private val partnerOrderRepository: PartnerOrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCaseOptParam<Single<List<Order>>, Void?> {
    override fun execute(params: Void?): Single<List<Order>> {
        return partnerOrderRepository.getOrders()
                .applyDefaultSchedulers(schedulerProvider)
    }
}