package de.andrena.access.domain

import de.andrena.access.domain.model.AccessRight

interface AccessRightRepository {
    fun findByUserIdAndDoorId(userId: Long, doorId: Long): AccessRight?
}