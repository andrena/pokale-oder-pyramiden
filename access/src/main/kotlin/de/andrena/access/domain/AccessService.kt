package de.andrena.access.domain

import de.andrena.access.domain.access.UserAccessRightService
import de.andrena.access.domain.user.EntitledUserService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AccessService(
    private var userAccessRightService: UserAccessRightService, private var userService: EntitledUserService
) {
    // TODO 2023-05-16, lalvincz - Integrationtest, mit h2? Unittest ohne mocks bzw. nur Repos mocken?

    fun isAccessAllowedTo(doorId: Long, userId: Long, dateTime: LocalDateTime): Boolean {
        val user = userService.getEntitledUser(userId)
        val accessRight = userAccessRightService.getUserAccessRight(doorId, userId)
        return user.hasAccess(accessRight, dateTime)
    }

    fun isMaintenanceAccessAllowedTo(doorId: Long, userId: Long, dateTime: LocalDateTime): Boolean {
        val user = userService.getEntitledUser(userId)
        val accessRight = userAccessRightService.getUserAccessRight(doorId, userId)
        return user.hasMaintenanceAccess(accessRight, dateTime)
    }
}