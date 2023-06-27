package de.andrena.access.domain.access.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UserAccessRightTest {
    private val now = LocalDateTime.now()
    private val door = Door(-1, "entrance")

    @Test
    internal fun hasAccessRight_NoSchedules_IsFalse() {
        assertThat(buildRight(emptyList(), false).hasAccessRight(now)).isFalse
    }

    @Test
    internal fun hasAccessRight_NoRight_IsFalse() {
        assertThat(UserAccessRight(null, door).hasAccessRight(now)).isFalse
    }

    @Test
    internal fun hasAccessRight_OnlyNegativeSchedules_IsFalse() {
        assertThat(buildRight(listOf(Schedules.never(), Schedules.never()), false).hasAccessRight(now)).isFalse
    }

    @Test
    internal fun hasAccessRight_ManySchedules_OnePositiveSchedule_IsTrue() {
        assertThat(buildRight(listOf(Schedules.never(), Schedules.never(), Schedules.always()), false).hasAccessRight(now)).isTrue
    }

    @Test
    internal fun isDoorInMaintenance_DoorNotInMaintenance_IsFalse() {
        assertThat(buildRight(emptyList(), false).isDoorInMaintenance()).isFalse
    }

    @Test
    internal fun isDoorInMaintenance_DoorInMaintenance_IsTrue() {
        assertThat(buildRight(emptyList(), true).isDoorInMaintenance()).isTrue
    }

    private fun buildRight(schedules: List<Schedule>, doorInMaintenance: Boolean): UserAccessRight {
        return UserAccessRight(AccessRight(-1, -1, -1, schedules), Door(-1, "name", doorInMaintenance))
    }
}