package de.andrena.access.domain.access

import de.andrena.access.domain.access.model.Door

interface DoorRepository {
    fun findItById(doorId: Long): Door?
}