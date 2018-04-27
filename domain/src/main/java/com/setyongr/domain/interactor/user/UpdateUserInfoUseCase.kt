package com.setyongr.domain.interactor.user

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.User
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Single<User>, UpdateUserInfoUseCase.UserInfoParam> {
    override fun execute(params: UpdateUserInfoUseCase.UserInfoParam): Single<User> {
        return userRepository.updateUser(name = params.name, email = params.email, phone = params.phone)
                .applyDefaultSchedulers(schedulerProvider)
    }

    class UserInfoParam(
            val name: String? = null,
            val email: String? = null,
            val phone: String? = null
    )

}