package de.andrena.access.domain.user.model

import de.andrena.access.domain.access.model.UserAccessRight
import java.time.LocalDateTime

class UnprivilegedUser : EntitledUser {
    override fun hasAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean = false

    override fun hasMaintenanceAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean = false
}