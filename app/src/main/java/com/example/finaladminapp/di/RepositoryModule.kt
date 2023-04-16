package com.example.finaladminapp.di

import com.example.adminapp.FirebaseRealtimeDb.repository.RealtimeDbRepository
import com.example.adminapp.FirebaseRealtimeDb.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealtimeRepository(
        repo: RealtimeDbRepository
    ):RealtimeRepository


}