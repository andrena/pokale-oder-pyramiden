package de.andrena.access.domain

import de.andrena.access.domain.access.UserAccessRightService
import de.andrena.access.domain.access.model.UserAccessRights
import de.andrena.access.domain.user.EntitledUserService
import de.andrena.access.domain.user.model.EntitledUser
import io.mockk.checkUnnecessaryStub
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

private const val DOOR_ID = 123L

private const val USER_ID = 321L

private val now = LocalDateTime.now()

@ExtendWith(MockKExtension::class)
internal class AccessServiceMockTest {
    @MockK
    private lateinit var userAccessRightService: UserAccessRightService
    @MockK
    private lateinit var entitledUserService: EntitledUserService
    private val userAccessRight = UserAccessRights.alwaysAccess()
    private var entitledUser = mockk<EntitledUser>()
    @InjectMockKs
    private lateinit var service: AccessService
    @BeforeEach
    internal fun setUp() {
        every { userAccessRightService.getUserAccessRight(DOOR_ID, USER_ID) } returns userAccessRight
        every { entitledUserService.getEntitledUser(USER_ID) } returns entitledUser
    }
    @AfterEach
    internal fun tearDown() {
        checkUnnecessaryStub(entitledUserService, userAccessRightService, entitledUser)
    }
    @Test
    internal fun isAccessAllowedTo() {
        every { entitledUser.hasAccess(userAccessRight, now) } returns true
        assertThat(service.isAccessAllowedTo(DOOR_ID, USER_ID, now)).isTrue
    }
    @Test
    internal fun isMaintenanceAccessAllowedTo() {
        every { entitledUser.hasMaintenanceAccess(userAccessRight, now) } returns true
        assertThat(service.isMaintenanceAccessAllowedTo(DOOR_ID, USER_ID, now)).isTrue
    }
}