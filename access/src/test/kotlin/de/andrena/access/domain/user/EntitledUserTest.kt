package de.andrena.access.domain.user

import de.andrena.access.domain.access.model.UserAccessRight
import de.andrena.access.domain.user.model.MaintainerUser
import de.andrena.access.domain.user.model.ManagerUser
import de.andrena.access.domain.user.model.RegularUser
import de.andrena.access.domain.user.model.UnprivilegedUser
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

private val now = LocalDateTime.now()

internal class EntitledUserTest {
private val accessRight = mockk<UserAccessRight>()

    @Nested
    inner class ManagerUserTest {
        @Test
        internal fun hasAccess_passedAccessRightIsIgnored_ReturnsAlwaysTrue() {
            hasNeverAccess()
            assertThat(ManagerUser().hasAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            hasAlwaysAccess()
            assertThat(ManagerUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }

    @Nested
    inner class RegularUserTest {
        @Test
        internal fun hasAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(RegularUser().hasAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_ReturnsTrue() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(RegularUser().hasAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsFalse() {
            doorInMaintenance()
            hasOnlyAccessNow()
            assertThat(RegularUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            hasAlwaysAccess()
            assertThat(RegularUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }

    @Nested
    inner class MaintainerUserTest {
        @Test
        internal fun hasAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_ReturnsTrue() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsFalse() {
            doorInMaintenance()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyNoAccess_ReturnsFalse() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now.plusSeconds(1))).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyAccess_ReturnsTrue() {
            doorEnabled()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now)).isTrue
        }

        @Test
        internal fun hasMaintenanceAccess_hasCurrentlyAccess_DoorInMaintenance_ReturnsTrue() {
            doorInMaintenance()
            hasOnlyAccessNow()
            assertThat(MaintainerUser().hasMaintenanceAccess(accessRight, now)).isTrue()
        }
    }

    @Nested
    inner class UnprivilegedUserTest {
        @Test
        internal fun hasAccess_passedAccessRightIsIgnored_ReturnsAlwaysTrue() {
            hasAlwaysAccess()
            assertThat(UnprivilegedUser().hasAccess(accessRight, now)).isFalse
        }

        @Test
        internal fun hasMaintenanceAccess_passedAccessRightIsIgnored_ReturnsAlwaysFalse() {
            hasAlwaysAccess()
            assertThat(UnprivilegedUser().hasMaintenanceAccess(accessRight, now)).isFalse
        }
    }

    fun hasAlwaysAccess() {
        every { accessRight.hasAccessRight(any()) } returns true
    }

    fun hasNeverAccess() {
        every { accessRight.hasAccessRight(any()) } returns false
    }

    fun hasOnlyAccessNow() {
        every { accessRight.hasAccessRight(any()) } returns false
        every { accessRight.hasAccessRight(now) } returns true
    }

    fun doorEnabled() {
        every { accessRight.isDoorInMaintenance() } returns false
    }

    fun doorInMaintenance() {
        every { accessRight.isDoorInMaintenance() } returns true
    }
}