package de.andrena.access.domain

import de.andrena.access.domain.model.User

interface UserRepository {
    fun findItById(id: Long): User?
}