package de.andrena.access.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Door(@Id  val id: Long, val name: String, val inMaintenance: Boolean = false)
