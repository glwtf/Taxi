package com.example.taxi.di

import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsOrdersRepository(impl: OrdersRepositoryImpl) : OrdersRepository
}