package com.setyongr.domain.interactor.user

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Register
import com.setyongr.domain.model.RegisterResult
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Single<RegisterResult>, Register> {
    override fun execute(params: Register): Single<RegisterResult> {
        return userRepository.register(params)
                .applyDefaultSchedulers(schedulerProvider)
    }

}