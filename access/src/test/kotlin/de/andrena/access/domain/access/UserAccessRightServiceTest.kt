package de.andrena.access.domain

import de.andrena.access.domain.access.AccessRightRepository
import de.andrena.access.domain.access.DoorRepository
import de.andrena.access.domain.access.UserAccessRightService
import de.andrena.access.domain.access.model.AccessRight
import de.andrena.access.domain.access.model.Door
import de.andrena.access.domain.access.model.NO_RIGHT
import de.andrena.access.domain.access.model.UserAccessRight
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private val userId = 123L
private val doorId = 456L
private val door = Door(doorId, "entrance")

@ExtendWith(MockKExtension::class)
internal class UserAccessRightServiceTest {
    @MockK
    private var doorRepo = mockk<DoorRepository>()
    @MockK
    private var accessRightRepo = mockk<AccessRightRepository>()
    private var accessRight = mockk<AccessRight>()
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
