package de.andrena.access.domain.user

import de.andrena.access.domain.access.model.Doors
import de.andrena.access.domain.access.model.UserAccessRight
import de.andrena.access.domain.access.model.UserAccessRights
import de.andrena.access.domain.user.model.MaintainerUser
import de.andrena.access.domain.user.model.ManagerUser
import de.andrena.access.domain.user.model.RegularUser
import de.andrena.access.domain.user.model.UnprivilegedUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

private val now = LocalDateTime.now()

internal class EntitledUserTest {
    private lateinit var accessRight: UserAccessRight

    @Nested
    inner class ManagerUserTest {
        @Test
        internal fun hasAccess_passedAccessRightIsIgnored_ReturnsAlwaysTrue() {
            accessRight = UserAccessRights.neverAccess()
            assertThat(ManagerUser().hasAccess(accessRight, now)).isTrue
        }
        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            accessRight = UserAccessRights.alwaysAccess()
            assertThat(ManagerUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }

    @Nested
    inner class RegularUserTest {
        @Test
        internal fun hasAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(RegularUser().hasAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_ReturnsTrue() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(RegularUser().hasAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsFalse() {
            accessRight = UserAccessRights.onlyAccessAt(now, Doors.inMaintenance())
            assertThat(RegularUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            accessRight = UserAccessRights.alwaysAccess()
            assertThat(RegularUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }

    @Nested
    inner class MaintainerUserTest {
        @Test
        internal fun hasAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(MaintainerUser().hasAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_ReturnsTrue() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(MaintainerUser().hasAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsFalse() {
            accessRight = UserAccessRights.onlyAccessAt(now, Doors.inMaintenance())
            assertThat(MaintainerUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyAccess_ReturnsTrue() {
            accessRight = UserAccessRights.onlyAccessAt(now)
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsTrue() {
            accessRight = UserAccessRights.onlyAccessAt(now, Doors.inMaintenance())
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now)).isTrue
        }
    }

    @Nested
    inner class UnprivilegedUserTest {
        @Test
        internal fun hasAccess_passedAccessRightIsIgnored_ReturnsAlwaysTrue() {
            accessRight = UserAccessRights.alwaysAccess()
            assertThat(UnprivilegedUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            accessRight = UserAccessRights.alwaysAccess()
            assertThat(UnprivilegedUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }
}