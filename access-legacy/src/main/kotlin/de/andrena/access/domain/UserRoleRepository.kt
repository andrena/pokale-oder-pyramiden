package de.andrena.access.domain

import de.andrena.access.domain.model.UserRoleEntry

interface UserRoleRepository {
    fun findByUserId(userId: Long): UserRoleEntry?
}