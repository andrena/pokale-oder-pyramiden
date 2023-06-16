package de.andrena.access.domain.access

import de.andrena.access.domain.access.model.AccessRight

interface AccessRightRepository {
    fun findByUserIdAndDoorId(userId: Long, doorId: Long): AccessRight?
}