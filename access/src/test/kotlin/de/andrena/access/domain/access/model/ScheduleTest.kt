package de.andrena.access.domain.access.model

import de.andrena.access.domain.user.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

private val AT_9_AM = LocalTime.of(9, 0)
private val AT_5_PM = LocalTime.of(17, 0)

internal class ScheduleTest {
    @ParameterizedTest
    @MethodSource("dayOfWeeks")
    fun map(allowedDays: List<DayOfWeek>, actualDay: DayOfWeek, expected: Boolean) {
        val schedule = Schedule(-1, LocalTime.MIN, LocalTime.MAX, allowedDays)
        assertThat(schedule.isAccessAllowed(LocalDateTime.now().with(actualDay))).isEqualTo(expected)
    }
    @ParameterizedTest
    @MethodSource("time")
    fun map(allowedStart: LocalTime, allowedEnd: LocalTime, actualTime: LocalTime, expected: Boolean) {
        val schedule = Schedule(-1, allowedStart, allowedEnd, DayOfWeek.values().toList())
        assertThat(schedule.isAccessAllowed(LocalDateTime.now().with(actualTime))).isEqualTo(expected)
    }
    companion object {
        @JvmStatic
        fun time() = listOf(
            Arguments.of(AT_9_AM, AT_5_PM, AT_9_AM.minusSeconds(1), false),
            Arguments.of(AT_9_AM, AT_5_PM, AT_9_AM, true),
            Arguments.of(AT_9_AM, AT_5_PM, AT_5_PM, true),
            Arguments.of(AT_9_AM, AT_5_PM, AT_5_PM.plusSeconds(1), false),
        )

        @JvmStatic
        fun dayOfWeeks() = listOf(
            Arguments.of(emptyList<DayOfWeek>(), DayOfWeek.MONDAY, false),
            Arguments.of(DayOfWeek.values().toList(), DayOfWeek.MONDAY, true),
            Arguments.of(DayOfWeek.values().toList(), DayOfWeek.FRIDAY, true),
            Arguments.of(DayOfWeek.values().toList(), DayOfWeek.SUNDAY, true),
            Arguments.of(listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY), DayOfWeek.MONDAY, true),
            Arguments.of(listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY), DayOfWeek.TUESDAY, false),
            Arguments.of(listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY), DayOfWeek.WEDNESDAY, true)
        )

    }

}