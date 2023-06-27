package de.andrena.access.domain.access.model

import de.andrena.access.domain.access.AccessRightRepository
import de.andrena.access.domain.access.DoorRepository
import de.andrena.access.domain.access.UserAccessRightService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val userId = 123L
private const val doorId = 456L
private val door = Door(doorId, "entrance")

@ExtendWith(MockKExtension::class)
internal class UserAccessRightServiceTest {
    @MockK
    private lateinit var doorRepo: DoorRepository
    @MockK
    private lateinit var accessRightRepo: AccessRightRepository
    private val accessRight = AccessRights.alwaysAccess()

    @InjectMockKs
    private lateinit var service: UserAccessRightService

    @BeforeEach
    internal fun setUp() {
        every { doorRepo.findItById(doorId) } returns door
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns accessRight
    }

    @Test
    internal fun getUserAccessRight_doorDoesNotExists_ReturnsNoRight() {
        every { doorRepo.findItById(doorId) } returns null
        val right = service.getUserAccessRight(doorId, userId)
        assertThat(right).isSameAs(NO_RIGHT)
    }

    @Test
    internal fun getUserAccessRight_ReturnsUserAccessRight() {
        val right = service.getUserAccessRight(doorId, userId)
        val expected = UserAccessRight(accessRight, door)
        assertThat(right).isEqualTo(expected)
    }

}
