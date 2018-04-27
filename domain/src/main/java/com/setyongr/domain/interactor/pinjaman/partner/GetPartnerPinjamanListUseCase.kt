package com.setyongr.domain.interactor.pinjaman.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PartnerPinjamanRepository
import com.setyongr.domain.repository.PinjamanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPartnerPinjamanListUseCase @Inject constructor(
        private val partnerPinjamanRepository: PartnerPinjamanRepository,
        private val schedulerProvider: SchedulerProvider
): UseCaseOptParam<Single<List<Pinjaman>>, Void?> {
    override fun execute(params: Void?): Single<List<Pinjaman>> {
        return partnerPinjamanRepository.getPartnerPinjaman()
                .applyDefaultSchedulers(schedulerProvider)
    }
}