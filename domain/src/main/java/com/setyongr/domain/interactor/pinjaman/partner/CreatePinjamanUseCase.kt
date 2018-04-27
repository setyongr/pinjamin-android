package com.setyongr.domain.interactor.pinjaman.partner

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PartnerPinjamanRepository
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class CreatePinjamanUseCase @Inject constructor(
        private val partnerPinjamanRepository: PartnerPinjamanRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Single<Pinjaman>, CreatePinjamanUseCase.PinjamanParams> {
    override fun execute(params: CreatePinjamanUseCase.PinjamanParams): Single<Pinjaman> {
        return partnerPinjamanRepository.createPinjaman(params.name, params.description, params.image)
                .applyDefaultSchedulers(schedulerProvider)
    }

    class PinjamanParams(
            val name: String,
            val description: String,
            val image: File?
    )

}