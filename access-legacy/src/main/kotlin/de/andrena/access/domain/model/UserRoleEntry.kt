package de.andrena.access.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class UserRoleEntry(@Id val userId: Long, val role: UserRole)