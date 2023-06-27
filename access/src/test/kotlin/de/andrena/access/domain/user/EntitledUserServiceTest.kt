package de.andrena.access.domain.user

import de.andrena.access.domain.user.*
import de.andrena.access.domain.user.model.*
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private const val userId = 123L

@ExtendWith(MockKExtension::class)
internal class EntitledUserServiceTest {

    @MockK
    private lateinit var userRoleRepository: UserRoleRepository

    @InjectMockKs
    private lateinit var service: EntitledUserService

    @ParameterizedTest
    @MethodSource("mapping")
    fun map(role: UserRole?, expected: EntitledUser) {
        val roleEntry = role?.let { UserRoleEntry(-1, it) }
        every { userRoleRepository.findByUserId(userId) } returns roleEntry
        assertThat(service.getEntitledUser(userId)).isInstanceOf(expected.javaClass)
    }

    companion object {
        @JvmStatic
        fun mapping() = listOf(
            Arguments.of(UserRole.REGULAR, RegularUser()),
            Arguments.of(UserRole.MANAGER, ManagerUser()),
            Arguments.of(UserRole.MAINTAINER, MaintainerUser()),
            Arguments.of(UserRole.BLOCKED, UnprivilegedUser()),
            Arguments.of(null, UnprivilegedUser())
        )
    }

}