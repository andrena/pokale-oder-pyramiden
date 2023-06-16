package de.andrena.access.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
class Schedule(@Id  val id: Long, val startTime: LocalTime, val endTime: LocalTime, val validAtDays: List<DayOfWeek>)
