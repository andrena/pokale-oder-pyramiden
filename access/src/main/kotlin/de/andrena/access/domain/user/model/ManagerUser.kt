package de.andrena.access.domain.user.model

import de.andrena.access.domain.access.model.UserAccessRight
import java.time.LocalDateTime

class ManagerUser : EntitledUser {
    override fun hasAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean {
        return true
    }

    override fun hasMaintenanceAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean {
        return false
    }
}