package com.example.practice.users.data.di

import com.example.practice.users.data.remote.api.UserApi
import com.example.practice.users.data.repository.UsersRepositoryImpl
import com.example.practice.users.domain.repository.UsersRepository
import com.example.practice.users.domain.usecase.GetUsersUseCase
import com.example.practice.users.domain.usecase.GetUsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit
        .create(UserApi::class.java)


    @Provides
    fun provideGetUsersUseCase(
        usersRepository: UsersRepository
    ): GetUsersUseCase = GetUsersUseCaseImpl(usersRepository)


    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindUserRepository(impl: UsersRepositoryImpl): UsersRepository
    }
}