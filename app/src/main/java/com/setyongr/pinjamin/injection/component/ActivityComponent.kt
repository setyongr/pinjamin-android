package com.setyongr.pinjamin.injection.component

import com.setyongr.pinjamin.injection.PerActivity
import com.setyongr.pinjamin.injection.module.ActivityModule
import com.setyongr.pinjamin.presentation.detail.DetailActivity
import com.setyongr.pinjamin.presentation.main.home.HomeFragment
import com.setyongr.pinjamin.presentation.main.order.OrderFragment
import com.setyongr.pinjamin.presentation.pinjamin.mypinjaman.MyPinjamanFragment
import com.setyongr.pinjamin.presentation.pinjamin.ordertome.OrderToMeFragment
import com.setyongr.pinjamin.presentation.pinjamin.addpinjaman.AddPinjamanFragment
import com.setyongr.pinjamin.presentation.main.profile.ProfileFragment
import com.setyongr.pinjamin.presentation.main.profile.edit.EditProfileDialog
import com.setyongr.pinjamin.presentation.main.profile.verify.VerifyProfileActivity
import com.setyongr.pinjamin.presentation.mydetail.MyDetailActivity
import com.setyongr.pinjamin.presentation.order.OrderActivity
import com.setyongr.pinjamin.presentation.pinjamin.OrderToMeDetailActivity
import com.setyongr.pinjamin.presentation.portal.PortalActivity
import com.setyongr.pinjamin.presentation.signin.SignInActivity
import com.setyongr.pinjamin.presentation.signup.SignUpActivity
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(activityModule: ActivityModule): Builder

        fun build(): ActivityComponent
    }

    fun inject(signInActivity: SignInActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(portalActivity: PortalActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(myDetailActivity: MyDetailActivity)
    fun inject(orderActivity: OrderActivity)
    fun inject(orderToMeDetailActivity: OrderToMeDetailActivity)
    fun inject(verifyProfileActivity: VerifyProfileActivity)

    fun inject(homeFragment: HomeFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(addPinjamanFragment: AddPinjamanFragment)
    fun inject(myPinjamanFragment: MyPinjamanFragment)
    fun inject(orderFragment: OrderFragment)
    fun inject(orderToMeFragment: OrderToMeFragment)

    fun inject(editProfileDialog: EditProfileDialog)
}