package de.andrena.access.domain.access.model

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime


object Schedules {
    fun always(): Schedule = Schedule(-1, LocalTime.MIN, LocalTime.MAX, DayOfWeek.values().toList())
    fun never(): Schedule = Schedule(-1, LocalTime.MAX, LocalTime.MAX, emptyList())
    fun onlyAt(now: LocalDateTime): Schedule = Schedule(-1, now.toLocalTime(), now.toLocalTime(), listOf(now.dayOfWeek))
}

object AccessRights {
    fun alwaysAccess(): AccessRight = AccessRight(-1, -1, -1, listOf(Schedule(-1, LocalTime.MIN, LocalTime.MAX, DayOfWeek.values().toList())))

    fun neverAccess(): AccessRight = AccessRight(-1, -1, -1, listOf(Schedule(-1, LocalTime.MAX, LocalTime.MAX, emptyList())))
}

object Doors {
    fun inMaintenance(): Door = Door(1, "", true)
}

object UserAccessRights {
    fun alwaysAccess(): UserAccessRight = UserAccessRight(AccessRights.alwaysAccess(), null)

    fun neverAccess(): UserAccessRight = UserAccessRight(AccessRights.neverAccess(), null)

    fun onlyAccessAt(now: LocalDateTime, door: Door? = null): UserAccessRight = UserAccessRight(
        AccessRight(
            -1, -1, -1, listOf(Schedules.onlyAt(now))
        ), door
    )
}
