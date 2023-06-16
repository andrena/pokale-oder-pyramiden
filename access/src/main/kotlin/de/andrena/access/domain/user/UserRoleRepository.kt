package de.andrena.access.domain.user

import de.andrena.access.domain.user.model.UserRoleEntry

interface UserRoleRepository {
    fun findByUserId(userId: Long): UserRoleEntry?
}