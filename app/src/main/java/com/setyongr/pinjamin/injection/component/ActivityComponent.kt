package com.setyongr.pinjamin.injection.component

import com.setyongr.pinjamin.injection.PerActivity
import com.setyongr.pinjamin.injection.module.ActivityModule
import com.setyongr.pinjamin.presentation.partner.pinjaman.PartnerPinjamanFragment
import com.setyongr.pinjamin.presentation.partner.order.PartnerOrderFragment
import com.setyongr.pinjamin.presentation.partner.PartnerOrderDetaillActivity
import com.setyongr.pinjamin.presentation.partner.PartnerActivity
import com.setyongr.pinjamin.presentation.partner.UsePinjamanActivity
import com.setyongr.pinjamin.presentation.partner.createpinjaman.CreatePinjamanActivity
import com.setyongr.pinjamin.presentation.auth.portal.PortalActivity
import com.setyongr.pinjamin.presentation.auth.signin.SignInActivity
import com.setyongr.pinjamin.presentation.auth.signup.SignUpActivity
import com.setyongr.pinjamin.presentation.partner.pinjamandetail.PartnerPinjamanDetailActivity
import com.setyongr.pinjamin.presentation.user.main.home.HomeFragment
import com.setyongr.pinjamin.presentation.user.main.order.OrderFragment
import com.setyongr.pinjamin.presentation.user.main.profile.ProfileFragment
import com.setyongr.pinjamin.presentation.user.main.profile.edit.EditProfileDialog
import com.setyongr.pinjamin.presentation.user.main.profile.verify.VerifyProfileActivity
import com.setyongr.pinjamin.presentation.user.orderdetail.OrderDetailActivity
import com.setyongr.pinjamin.presentation.user.pinjamandetail.PinjamanDetailActivity
import com.setyongr.pinjamin.presentation.user.placeorder.PlaceOrderActivity
import com.setyongr.pinjamin.presentation.user.point.PointActivity
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
    fun inject(pinjamanDetailActivity: PinjamanDetailActivity)
    fun inject(placeOrderActivity: PlaceOrderActivity)
    fun inject(partnerOrderDetaillActivity: PartnerOrderDetaillActivity)
    fun inject(verifyProfileActivity: VerifyProfileActivity)
    fun inject(createPinjamanActivity: CreatePinjamanActivity)
    fun inject(partnerActivity: PartnerActivity)
    fun inject(orderDetailActivity: OrderDetailActivity)
    fun inject(usePinjamanActivity: UsePinjamanActivity)
    fun inject(pointActivity: PointActivity)
    fun inject(partnerPinjamanDetailActivity: PartnerPinjamanDetailActivity)

    fun inject(homeFragment: HomeFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(orderFragment: OrderFragment)
    fun inject(orderToMeFragment: PartnerOrderFragment)
    fun inject(partnerPinjamanFragment: PartnerPinjamanFragment)

    fun inject(editProfileDialog: EditProfileDialog)
}