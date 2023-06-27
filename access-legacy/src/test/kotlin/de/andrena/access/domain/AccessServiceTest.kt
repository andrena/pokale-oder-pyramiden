package de.andrena.access.domain

import de.andrena.access.domain.model.*
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private const val userId = 123L
private const val doorId = 456L
private val door = Door(doorId, "entrance")
private val doorInMaintenance = Door(doorId, "secondEntrance", true)

@ExtendWith(MockKExtension::class)
internal class AccessServiceTest {
    @MockK
    private lateinit var doorRepo: DoorRepository
    @MockK
    private lateinit var userRepo: UserRepository
    @MockK
    private lateinit var accessRightRepo: AccessRightRepository

    @InjectMockKs
    private lateinit var service: AccessService

    @BeforeEach
    internal fun setUp() {
        every { doorRepo.findItById(doorId) } returns door
        every { userRepo.findItById(userId) } returns User(1,UserRole.REGULAR)
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns null
    }

    @Test
    internal fun isAccessAllowedTo_doorDoesNotExists_ReturnsFalse() {
        every { doorRepo.findItById(any()) } returns null
        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, LocalDateTime.now())
        assertThat(isAccessAllowed).isFalse
    }

    @Test
    internal fun isAccessAllowedTo_doorIsInMaintenance_ReturnsFalse() {
        every { doorRepo.findItById(doorId) } returns doorInMaintenance

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, LocalDateTime.now())

        assertThat(isAccessAllowed).isFalse
    }

    @Test
    internal fun isAccessAllowedTo_accessRightDoesNotExists_ReturnsFalse() {
        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns null

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, LocalDateTime.now())

        assertThat(isAccessAllowed).isFalse
    }

    @Test
    internal fun isAccessAllowedTo_CurrentDayOfWeekIsNotPresentInAnySchedule_ReturnsFalse() {
        val now = LocalDateTime.now()

        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns AccessRight(1, doorId, userId,
            listOf(
                Schedule(11,
                    LocalTime.MIN,
                    LocalTime.MAX,
                    DayOfWeek.values().filter { it != now.dayOfWeek }
                )
            ))

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, now)

        assertThat(isAccessAllowed).isFalse
    }

    @Test
    internal fun isAccessAllowedTo_CurrentTimeIsOutsideOfAllSchedules_ReturnsFalse() {
        val now = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0, 0))

        every { accessRightRepo.findByUserIdAndDoorId(any(), any()) } returns
                AccessRight(
                    1,
                    doorId, userId,
                    listOf(
                        Schedule(
                            11,
                            LocalTime.of(10, 0, 0),
                            LocalTime.of(11, 0, 0),
                            DayOfWeek.values().toList()
                        )
                    )
                )

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, now)

        assertThat(isAccessAllowed).isFalse
    }

    @Test
    internal fun isAccessAllowedTo_CurrentDateAndTimeAreInsideOfSchedules_ReturnsTrue() {
        val now = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0, 0))

        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns
                AccessRight(
                    1,
                    doorId, userId, listOf(
                        Schedule(
                            11,
                            LocalTime.MIN,
                            LocalTime.MAX,
                            DayOfWeek.values().toList()
                        )
                    )
                )

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, now)

        assertThat(isAccessAllowed).isTrue
    }

    @Test
    internal fun isAccessAllowedTo_CurrentDateAndTimeIsBetweenSchedules_ReturnsTrue() {
        val now = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0, 0))

        every { accessRightRepo.findByUserIdAndDoorId(userId, doorId) } returns
                AccessRight(
                    1,
                    doorId, userId, listOf(
                        Schedule(
                            11,
                            LocalTime.of(7, 0, 0),
                            LocalTime.of(8, 0, 0),
                            DayOfWeek.values().toList()
                        ),
                        Schedule(
                            12,
                            LocalTime.of(10, 0, 0),
                            LocalTime.of(11, 0, 0),
                            DayOfWeek.values().toList()
                        )
                    )
                )

        val isAccessAllowed = service.isAccessAllowedTo(doorId, userId, now)

        assertThat(isAccessAllowed).isFalse

    }
}