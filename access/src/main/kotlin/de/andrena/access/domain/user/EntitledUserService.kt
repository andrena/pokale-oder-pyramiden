package de.andrena.access.domain.user

import de.andrena.access.domain.user.model.EntitledUser
import de.andrena.access.domain.user.model.MaintainerUser
import de.andrena.access.domain.user.model.ManagerUser
import de.andrena.access.domain.user.model.RegularUser
import de.andrena.access.domain.user.model.UnprivilegedUser
import de.andrena.access.domain.user.model.UserRole
import org.springframework.stereotype.Component

@Component
class EntitledUserService(
    private var repository: UserRepository
) {

    fun getEntitledUser(userId: Long): EntitledUser {
        return when (repository.findItById(userId)?.role) {
            UserRole.REGULAR -> RegularUser()
            UserRole.MANAGER -> ManagerUser()
            UserRole.MAINTAINER -> MaintainerUser()
            else -> UnprivilegedUser()
        }
    }
}