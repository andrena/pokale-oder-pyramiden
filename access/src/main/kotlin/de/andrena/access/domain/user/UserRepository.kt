package de.andrena.access.domain.user

import de.andrena.access.domain.user.model.User

interface UserRepository {
    fun findItById(id: Long): User?
}