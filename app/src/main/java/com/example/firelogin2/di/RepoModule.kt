package com.example.firelogin2.di

import com.example.firelogin2.repo.AuthRepo
import com.example.firelogin2.repo.AuthRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun providesAuthRepo(repoImpl: AuthRepoImpl): AuthRepo
}