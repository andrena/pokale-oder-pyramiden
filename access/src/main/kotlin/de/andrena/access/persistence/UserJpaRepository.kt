package de.andrena.access.persistence

import de.andrena.access.domain.user.UserRepository
import de.andrena.access.domain.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : UserRepository, CrudRepository<User, Long>