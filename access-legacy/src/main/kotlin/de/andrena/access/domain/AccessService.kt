package de.andrena.access.domain

import de.andrena.access.domain.model.Schedule
import de.andrena.access.domain.model.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AccessService {
    @Autowired
    private lateinit var doorRepo: DoorRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var accessRightRepo: AccessRightRepository

    fun isAccessAllowedTo(doorId: Long, userId: Long, dateTime: LocalDateTime): Boolean {
        val userRole = userRepository.findItById(userId)?.role
        val userAdmitted = isRegularUser(userRole) || isManager(userRole)
        val accessGranted = isManager(userRole) || (checkDoor(doorId) && checkAccessRights(userId, doorId, dateTime))
        return userAdmitted && accessGranted
    }

    fun isMaintenanceAccessAllowedTo(doorId: Long, userId: Long, dateTime: LocalDateTime): Boolean {
        val userRole = userRepository.findItById(userId)?.role
        val userAdmitted = isMaintainer(userRole)
        return userAdmitted && checkAccessRights(userId, doorId, dateTime)
    }

    private fun isRegularUser(userRole: UserRole?) = userRole == UserRole.REGULAR
    private fun isManager(userRole: UserRole?) = userRole == UserRole.MANAGER
    private fun isMaintainer(userRole: UserRole?) = userRole == UserRole.MAINTAINER

    private fun checkAccessRights(userId: Long, doorId: Long, dateTime: LocalDateTime): Boolean {
        val accessRightsFromDatabase = accessRightRepo.findByUserIdAndDoorId(userId, doorId)

        val schedules = accessRightsFromDatabase?.schedules ?: emptyList()
        val accessIsAllowedToday = isAccessAllowedAtDayOfWeek(schedules, dateTime)
        val accessIsAllowedAtTime = isAccessAllowedAtTime(schedules, dateTime)

        return accessIsAllowedToday && accessIsAllowedAtTime
    }

    private fun checkDoor(doorId: Long): Boolean {
        val doorFromDatabase = doorRepo.findItById(doorId) ?: return false
        return !doorFromDatabase.inMaintenance
    }

    private fun isAccessAllowedAtDayOfWeek(schedules: List<Schedule>, dateTime: LocalDateTime): Boolean {
        return schedules.any { it.validAtDays.contains(dateTime.dayOfWeek) }
    }

    private fun isAccessAllowedAtTime(schedules: List<Schedule>, dateTime: LocalDateTime): Boolean {
        return schedules.any { !dateTime.toLocalTime().isBefore(it.startTime) && !dateTime.toLocalTime().isAfter(it.endTime) }
    }
}