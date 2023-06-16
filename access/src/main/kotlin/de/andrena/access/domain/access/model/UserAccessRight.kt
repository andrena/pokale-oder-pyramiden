package de.andrena.access.domain.access.model

import java.time.LocalDateTime

val NO_RIGHT: UserAccessRight = UserAccessRight(null, null)

data class UserAccessRight(private val rights: AccessRight?, private val door: Door?) {

    fun hasAccessRight(dateTime: LocalDateTime): Boolean = rights?.isAccessAllowed(dateTime) ?: false

    fun isDoorInMaintenance() = door?.inMaintenance ?: false
}