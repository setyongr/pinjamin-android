package com.setyongr.domain.interactor.order.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Order
import com.setyongr.domain.repository.OrderRepository
import com.setyongr.domain.repository.PartnerOrderRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPartnerOrderByIdUseCase @Inject constructor(
    private val partnerOrderRepository: PartnerOrderRepository,
    private val schedulerProvider: SchedulerProvider
) : UseCase<Single<Order>, Int> {
    override fun execute(params: Int): Single<Order> {
        return partnerOrderRepository.getOrderById(params)
                .applyDefaultSchedulers(schedulerProvider)
    }
}