package com.setyongr.domain.interactor.pinjaman

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PinjamanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPinjamanByIdUseCase @Inject constructor(
        private val pinjamanRepository: PinjamanRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Single<Pinjaman>, Int> {
    override fun execute(params: Int): Single<Pinjaman> {
        return pinjamanRepository.getPinjamanById(params)
                .applyDefaultSchedulers(schedulerProvider)
    }
}