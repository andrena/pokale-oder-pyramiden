package de.andrena.access.domain.access.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class AccessRight(@Id  private val id: Long,
                  private val userId: Long,
                  private val doorId: Long,
                  @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
                  @JoinColumn(name = "accessright_id", nullable = false, updatable = false)
                  private val schedules: List<Schedule>) {
    fun isAccessAllowed(dateTime: LocalDateTime): Boolean = schedules.any { it.isAccessAllowed(dateTime) }
}
