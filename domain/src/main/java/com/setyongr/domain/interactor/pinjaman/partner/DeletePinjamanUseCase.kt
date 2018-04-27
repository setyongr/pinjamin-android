package com.setyongr.domain.interactor.pinjaman.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PartnerPinjamanRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class DeletePinjamanUseCase @Inject constructor(
        private val partnerPinjamanRepository: PartnerPinjamanRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Completable, Int> {
    override fun execute(params: Int): Completable {
        return partnerPinjamanRepository.deletePartnerPinjaman(params)
                .applyDefaultSchedulers(schedulerProvider)
    }
}