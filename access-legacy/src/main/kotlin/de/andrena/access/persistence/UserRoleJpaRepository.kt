package de.andrena.access.persistence

import de.andrena.access.domain.UserRoleRepository
import de.andrena.access.domain.model.UserRoleEntry
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleJpaRepository : UserRoleRepository, CrudRepository<UserRoleEntry, Long>