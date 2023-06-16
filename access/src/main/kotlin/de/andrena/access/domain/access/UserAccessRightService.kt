package de.andrena.access.domain.access

import de.andrena.access.domain.access.model.NO_RIGHT
import de.andrena.access.domain.access.model.UserAccessRight
import org.springframework.stereotype.Component

@Component
class UserAccessRightService(
    private var doorRepo: DoorRepository,
    private var accessRightRepo: AccessRightRepository
) {

    fun getUserAccessRight(doorId: Long, userId: Long): UserAccessRight {
        val door = doorRepo.findItById(doorId) ?: return NO_RIGHT
        val rights = accessRightRepo.findByUserIdAndDoorId(userId, doorId)
        return UserAccessRight(rights, door)
    }
}