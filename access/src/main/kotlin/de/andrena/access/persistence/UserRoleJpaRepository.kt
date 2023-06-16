package de.andrena.access.persistence

import de.andrena.access.domain.user.UserRoleRepository
import de.andrena.access.domain.user.model.UserRoleEntry
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleJpaRepository : UserRoleRepository, CrudRepository<UserRoleEntry, Long>