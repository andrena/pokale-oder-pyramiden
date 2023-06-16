package de.andrena.access.domain.user.model

import de.andrena.access.domain.access.model.UserAccessRight
import java.time.LocalDateTime

class MaintainerUser : EntitledUser {
    override fun hasAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean {
        return accessRight.hasAccessRight(dateTime) && !accessRight.isDoorInMaintenance()
    }

    override fun hasMaintenanceAccess(accessRight: UserAccessRight, dateTime: LocalDateTime): Boolean {
        return accessRight.hasAccessRight(dateTime)
    }
}