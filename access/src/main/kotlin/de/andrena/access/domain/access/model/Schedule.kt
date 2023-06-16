package de.andrena.access.domain.access.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
class Schedule(@Id  val id: Long, val startTime: LocalTime, val endTime: LocalTime, val validAtDays: List<DayOfWeek>) {
    fun isAccessAllowed(dateTime: LocalDateTime): Boolean = isAccessAllowedAtDayOfWeek(dateTime) && isAccessAllowedAtTime(dateTime.toLocalTime())
    private fun isAccessAllowedAtDayOfWeek(dateTime: LocalDateTime): Boolean = validAtDays.contains(dateTime.dayOfWeek)
    private fun isAccessAllowedAtTime(time: LocalTime): Boolean = !time.isBefore(startTime) && !time.isAfter(endTime)
}
