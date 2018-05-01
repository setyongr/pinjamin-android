package com.setyongr.pinjamin.injection.module

import com.setyongr.pinjamin.injection.PerActivity
import com.setyongr.pinjamin.presentation.auth.portal.PortalActivity
import com.setyongr.pinjamin.presentation.auth.signin.SignInActivity
import com.setyongr.pinjamin.presentation.partner.PartnerActivity
import com.setyongr.pinjamin.presentation.partner.PartnerOrderDetaillActivity
import com.setyongr.pinjamin.presentation.partner.UsePinjamanActivity
import com.setyongr.pinjamin.presentation.partner.createpinjaman.CreatePinjamanActivity
import com.setyongr.pinjamin.presentation.partner.order.PartnerOrderFragment
import com.setyongr.pinjamin.presentation.partner.pinjaman.PartnerPinjamanFragment
import com.setyongr.pinjamin.presentation.partner.pinjamandetail.PartnerPinjamanDetailActivity
import com.setyongr.pinjamin.presentation.user.main.MainActivity
import com.setyongr.pinjamin.presentation.user.main.home.HomeFragment
import com.setyongr.pinjamin.presentation.user.main.order.OrderFragment
import com.setyongr.pinjamin.presentation.user.main.profile.ProfileFragment
import com.setyongr.pinjamin.presentation.user.main.profile.edit.EditProfileDialog
import com.setyongr.pinjamin.presentation.user.main.profile.verify.VerifyProfileActivity
import com.setyongr.pinjamin.presentation.user.orderdetail.OrderDetailActivity
import com.setyongr.pinjamin.presentation.user.pinjamandetail.PinjamanDetailActivity
import com.setyongr.pinjamin.presentation.user.placeorder.PlaceOrderActivity
import com.setyongr.pinjamin.presentation.user.point.PointActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity() : MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun signInActivity() : SignInActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun portalActivity() : PortalActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun pinjamanDetailActivity() : PinjamanDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun placeOrderActivity() : PlaceOrderActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun partnerOrderDetailActivity() : PartnerOrderDetaillActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun verifyProfileActivity() : VerifyProfileActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun createPinjamanActivity() : CreatePinjamanActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun partnerActivity() : PartnerActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun orderDetailActivity() : OrderDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun usePinjamanActivity() : UsePinjamanActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun pointActivity() : PointActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun partnerPinjamanDetailActivity() : PartnerPinjamanDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun homeFragment() : HomeFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun profileFragment() : ProfileFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun orderFragment() : OrderFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun orderToMeFragment() : PartnerOrderFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun partnerPinjamanFragment() : PartnerPinjamanFragment


    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun editProfileDialog() : EditProfileDialog

}