package com.setyongr.domain.interactor.user

import com.setyongr.domain.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.UseCase
import com.setyongr.domain.model.User
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class UpdateUserImageUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase<Single<User>, UpdateUserImageUseCase.ImageParam> {
    override fun execute(params: ImageParam): Single<User> {
        return userRepository.updateUserImage(params.field, params.image)
                .applyDefaultSchedulers(schedulerProvider)
    }

    class ImageParam(val field: String, val image: File)
}