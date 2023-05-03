package com.example.practice.users.domain.usecase

import app.cash.turbine.test
import com.example.practice.users.domain.model.User
import com.example.practice.users.domain.repository.UsersRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetUsersUseCaseImplTest {

    @MockK
    private lateinit var usersRepositoryMockk: UsersRepository

    private lateinit var useCase: GetUsersUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetUsersUseCaseImpl(usersRepository = usersRepositoryMockk)
    }

    @DisplayName("Get users")
    @Nested
    inner class GetUsers {

        @BeforeEach
        fun setup() {
            every { usersRepositoryMockk.getUsers() } returns flow { emit(usersListFake) }
        }

        @DisplayName("Without Excluded UserId")
        @Nested
        inner class WithoutExcludedUserId {

            @DisplayName("And Not Reversed Order")
            @Nested
            inner class AndNotReversedOrder {

                @Test
                fun `should return same list as repository`() = runTest {
                    val expectedUsersList = Result.success(usersListFake)
                    useCase.getUsers(excludedUserId = null, reversed = false).test {
                        Truth.assertThat(awaitItem())
                            .isEqualTo(expectedUsersList)
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            @DisplayName("And Reversed Order")
            @Nested
            inner class AndReversedOrder {

                @Test
                fun `should return same list as repository in reversed order`() = runTest {
                    val expectedUsersList = Result.success(usersListFake.reversed())
                    useCase.getUsers(excludedUserId = null, reversed = true).test {
                        Truth.assertThat(awaitItem())
                            .isEqualTo(expectedUsersList)
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        @DisplayName("With Excluded UserId")
        @Nested
        inner class WithExcludedUserId {

            private val excludedUserId = 2
            private val usersListFakeWithExcludedUser =
                usersListFake.filter { it.id != excludedUserId }

            @DisplayName("And Not Reversed Order")
            @Nested
            inner class AndNotReversedOrder {

                @Test
                fun `should return same list as repository`() = runTest {
                    val expectedUsersList = Result.success(usersListFakeWithExcludedUser)
                    useCase.getUsers(excludedUserId = excludedUserId, reversed = false).test {
                        Truth.assertThat(awaitItem())
                            .isEqualTo(expectedUsersList)
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            @DisplayName("And Reversed Order")
            @Nested
            inner class AndReversedOrder {

                @Test
                fun `should return same list as repository in reversed order`() = runTest {
                    val expectedUsersList = Result.success(usersListFakeWithExcludedUser.reversed())
                    useCase.getUsers(excludedUserId = excludedUserId, reversed = true).test {
                        Truth.assertThat(awaitItem())
                            .isEqualTo(expectedUsersList)
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        @Nested
        inner class WithUsersRepositoryError {

            private val repositoryException = Exception("Failure")

            @BeforeEach
            fun setup() {
                every { usersRepositoryMockk.getUsers() } throws repositoryException
            }

            @Test
            fun `should return same exception as received from repository`() = runTest {
                val expectedResult = Result.failure<Exception>(repositoryException)

                org.junit.jupiter.api.assertThrows<Exception> {
                    useCase.getUsers().test {
                        val result = awaitItem()
                        Truth.assertThat(result)
                            .isEqualTo(expectedResult)
                    }
                }
            }
        }
    }

    companion object {
        private val usersListFake = listOf(
            User(
                id = 1,
                name = "Name 1",
                email = "Email 1",
                city = "City 1",
                companyName = "Company 1"
            ),
            User(
                id = 2,
                name = "Name 2",
                email = "Email 2",
                city = "City 2",
                companyName = "Company 2"
            ),
            User(
                id = 3,
                name = "Name 3",
                email = "Email 3",
                city = "City 3",
                companyName = "Company 3"
            ),
            User(
                id = 4,
                name = "Name 4",
                email = "Email 4",
                city = "City 4",
                companyName = "Company 4"
            ),
        )
    }
}