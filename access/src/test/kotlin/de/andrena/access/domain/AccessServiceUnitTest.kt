package de.andrena.access.domain

import de.andrena.access.domain.access.AccessRightRepository
import de.andrena.access.domain.access.DoorRepository
import de.andrena.access.domain.access.UserAccessRightService
import de.andrena.access.domain.access.model.AccessRights
import de.andrena.access.domain.access.model.Door
import de.andrena.access.domain.user.EntitledUserService
import de.andrena.access.domain.user.UserRoleRepository
import de.andrena.access.domain.user.model.UserRole
import de.andrena.access.domain.user.model.UserRoleEntry
import io.mockk.checkUnnecessaryStub
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

private val now = LocalDateTime.now()

private const val userId = 123L
private const val doorId = 456L
private val door = Door(doorId, "entrance")

@ExtendWith(MockKExtension::class)
internal class AccessServiceUnitTest {
    @MockK
    private lateinit var doorRepo: DoorRepository
    @MockK
    private lateinit var accessRightRepo: AccessRightRepository
    @MockK
    private lateinit var userRoleRepository: UserRoleRepository
    private lateinit var userAccessRightService: UserAccessRightService
    private lateinit var entitledUserService: EntitledUserService
    private lateinit var service: AccessService

    @BeforeEach
    internal fun setUp() {
        userAccessRightService = UserAccessRightService(doorRepo, accessRightRepo)
        entitledUserService = EntitledUserService(userRoleRepository)
        service = AccessService(userAccessRightService, entitledUserService)

        every { doorRepo.findItById(doorId) } returns door
    }

    @Test
    internal fun isAccessAllowedTo_RegularUserWithAccess_ReturnsTrue() {
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns AccessRights.alwaysAccess()
        every { userRoleRepository.findByUserId(userId) } returns UserRoleEntry(-1, UserRole.REGULAR)

        assertThat(service.isAccessAllowedTo(doorId, userId, now)).isTrue

        checkUnnecessaryStub(doorRepo, accessRightRepo, userRoleRepository)
    }

    @Test
    internal fun isAccessAllowedTo_RegularUserWithoutAccess_ReturnsFalse() {
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns AccessRights.neverAccess()
        every { userRoleRepository.findByUserId(userId) } returns UserRoleEntry(-1,UserRole.REGULAR)

        assertThat(service.isAccessAllowedTo(doorId, userId, now)).isFalse
    }

    @Test
    internal fun isMaintenanceAccessAllowedTo_RegularUserWithAccess_ReturnsFalse() {
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns AccessRights.alwaysAccess()
        every { userRoleRepository.findByUserId(userId) } returns UserRoleEntry(-1,UserRole.REGULAR)

        assertThat(service.isMaintenanceAccessAllowedTo(doorId, userId, now)).isFalse
    }

    @Test
    internal fun isMaintenanceAccessAllowedTo_MaintainerUserWithAccess_ReturnsTrue() {
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns AccessRights.alwaysAccess()
        every { userRoleRepository.findByUserId(userId) } returns UserRoleEntry(-1,UserRole.MAINTAINER)

        assertThat(service.isMaintenanceAccessAllowedTo(doorId, userId, now)).isTrue
    }
}