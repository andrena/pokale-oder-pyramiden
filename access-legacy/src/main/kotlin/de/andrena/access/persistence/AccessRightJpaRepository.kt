package de.andrena.access.persistence

import de.andrena.access.domain.AccessRightRepository
import de.andrena.access.domain.model.AccessRight
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessRightJpaRepository : AccessRightRepository, CrudRepository<AccessRight, Long>