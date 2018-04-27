package com.setyongr.domain.interactor.user

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.TokenProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.Login
import com.setyongr.domain.model.User
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerProvider: SchedulerProvider,
        private val tokenProvider: TokenProvider
): UseCase<Single<User>, Login> {
    override fun execute(params: Login): Single<User> {
        return userRepository.login(params)
                .flatMap {
                    tokenProvider.setToken(it.token)
                    userRepository.remoteCurrentUser()
                            .applyDefaultSchedulers(schedulerProvider)
                }
                .applyDefaultSchedulers(schedulerProvider)
    }

}