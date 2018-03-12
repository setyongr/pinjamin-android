package com.setyongr.pinjamin

import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.RequestModel
import com.setyongr.pinjamin.data.models.ResponseModel
import io.reactivex.observers.TestObserver
import org.junit.Test

class PinjaminServiceUnitTest {
    private val service = PinjaminService.create(TestTokenProvider())
    @Test
    fun can_sign_in() {
        val testSubs = TestObserver<ResponseModel.Token>()
        service.login(RequestModel.Login("admin", "qwerty123")).subscribe(testSubs)
        testSubs.assertNoErrors()
        println(testSubs.values().toString())
    }
}