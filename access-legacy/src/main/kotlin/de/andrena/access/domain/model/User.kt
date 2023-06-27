package de.andrena.access.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User(@Id val id: Long, val role: UserRole)