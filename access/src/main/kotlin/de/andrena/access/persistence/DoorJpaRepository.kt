package de.andrena.access.persistence

import de.andrena.access.domain.access.DoorRepository
import de.andrena.access.domain.access.model.Door
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DoorJpaRepository: DoorRepository, CrudRepository<Door, Long>