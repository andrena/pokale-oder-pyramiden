package de.andrena.access.domain

import de.andrena.access.domain.model.Door

interface DoorRepository {
    fun findItById(doorId: Long): Door?
}