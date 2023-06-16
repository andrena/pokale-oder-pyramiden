package de.andrena.access.domain.access.model

import java.time.DayOfWeek
import java.time.LocalTime


fun alwaysAccess(): AccessRight {
    return AccessRight(-1, -1, -1, listOf(Schedule(-1,LocalTime.MIN, LocalTime.MAX, DayOfWeek.values().toList())))
}

fun neverAccess(): AccessRight {
    return AccessRight(-1, -1, -1, listOf(Schedule(-1,LocalTime.MAX, LocalTime.MAX, emptyList())))
}