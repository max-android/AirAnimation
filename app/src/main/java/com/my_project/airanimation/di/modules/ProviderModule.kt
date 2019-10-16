package com.my_project.airanimation.di.modules

import android.content.Context
import com.my_project.airanimation.data.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Module
class ProviderModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideResourceProvider() = ResourceProvider(context)

}