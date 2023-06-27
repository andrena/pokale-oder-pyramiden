package de.andrena.access.persistence

import de.andrena.access.domain.UserRepository
import de.andrena.access.domain.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : UserRepository, CrudRepository<User, Long>