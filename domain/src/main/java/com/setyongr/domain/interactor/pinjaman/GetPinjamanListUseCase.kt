package com.setyongr.domain.interactor.pinjaman

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PinjamanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPinjamanListUseCase @Inject constructor(
        private val pinjamanRepository: PinjamanRepository,
        private val schedulerProvider: SchedulerProvider
): UseCaseOptParam<Single<List<Pinjaman>>, Void?> {
    override fun execute(params: Void?): Single<List<Pinjaman>> {
        return pinjamanRepository.getPinjaman()
                .applyDefaultSchedulers(schedulerProvider)
    }
}