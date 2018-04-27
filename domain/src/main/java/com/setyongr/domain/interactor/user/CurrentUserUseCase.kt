package com.setyongr.domain.interactor.user

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.interactor.UseCaseOptParam
import com.setyongr.domain.model.Register
import com.setyongr.domain.model.RegisterResult
import com.setyongr.domain.model.User
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrentUserUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerProvider: SchedulerProvider
): UseCaseOptParam<Single<User>, Void?> {
    override fun execute(params: Void?): Single<User> {
        return userRepository.remoteCurrentUser()
                .applyDefaultSchedulers(schedulerProvider)
    }

}