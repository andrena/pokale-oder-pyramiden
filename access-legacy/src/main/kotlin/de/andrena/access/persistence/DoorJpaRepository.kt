package de.andrena.access.persistence

import de.andrena.access.domain.DoorRepository
import de.andrena.access.domain.model.Door
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DoorJpaRepository: DoorRepository, CrudRepository<Door, Long>