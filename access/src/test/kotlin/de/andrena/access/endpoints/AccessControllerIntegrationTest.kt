package de.andrena.access.endpoints

import de.andrena.access.domain.access.model.AccessRight
import de.andrena.access.domain.access.model.Door
import de.andrena.access.domain.access.model.Schedule
import de.andrena.access.domain.user.model.UserRole
import de.andrena.access.domain.user.model.User
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

private const val userId = 123L
private const val doorId = 456L
private val door = Door(doorId, "entrance")
private val accessRightAlways = AccessRight(
    111, userId, doorId, listOf(Schedule(222, LocalTime.MIN, LocalTime.MAX,
        DayOfWeek.values().toList()))
)
private val accessRightNever = AccessRight(
    111, userId, doorId, listOf(Schedule(222, LocalTime.MIN, LocalTime.MAX,
        emptyList()))
)
private val regularUser = User(userId, UserRole.REGULAR)
private val maintainerUser = User(userId, UserRole.MAINTAINER)

@SpringBootTest
@Transactional
internal class AccessControllerIntegrationTest {
    @Autowired
    private lateinit var entityManager: EntityManager
    @Autowired
    private lateinit var controller: AccessController
    @BeforeEach
    internal fun setUp() {
        entityManager.persist(door)
    }
    @Test
    internal fun isAccessAllowed_NoData_ReturnsFalse() {
        assertThat(controller.isAccessAllowed(888, 999, LocalDateTime.now())).isFalse
    }
    @Test
    internal fun isAccessAllowed_RegularUserWithoutRights_ReturnsFalse() {
        entityManager.persist(accessRightNever)
        entityManager.persist(regularUser)
        assertThat(controller.isAccessAllowed(doorId, userId, LocalDateTime.now())).isFalse
    }
    @Test
    internal fun isAccessAllowed_RegularUserWithRights_ReturnsTrue() {
        entityManager.persist(accessRightAlways)
        entityManager.persist(regularUser)
        assertThat(controller.isAccessAllowed(doorId, userId, LocalDateTime.now())).isTrue
    }

    @Test
    internal fun isMaintenanceAccessAllowed_RegularUserWithRights_ReturnsFalse() {
        entityManager.persist(accessRightAlways)
        entityManager.persist(regularUser)

        assertThat(controller.isMaintenanceAccessAllowed(doorId, userId, LocalDateTime.now())).isFalse
    }

    @Test
    internal fun isMaintenanceAccessAllowed_MaintainerUserWithRights_ReturnsTrue() {
        entityManager.persist(accessRightAlways)
        entityManager.persist(maintainerUser)

        assertThat(controller.isMaintenanceAccessAllowed(doorId, userId, LocalDateTime.now())).isTrue
    }
}