package de.andrena.access.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class AccessRight(@Id  private val id: Long,
                  private val userId: Long,
                  private val doorId: Long,
                  @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
                  @JoinColumn(name = "accessright_id", nullable = false, updatable = false)
                  val schedules: List<Schedule>)