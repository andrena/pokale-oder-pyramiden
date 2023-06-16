package de.andrena.access.domain.user.model

import de.andrena.access.domain.access.model.UserAccessRight
import java.time.LocalDateTime

interface EntitledUser {
    fun hasAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean
    fun hasMaintenanceAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean
}