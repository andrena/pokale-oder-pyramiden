package de.andrena.access.endpoints

import de.andrena.access.domain.AccessService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Controller
@RequestMapping("/access")
class AccessController(private var service: AccessService) {

    @GetMapping("/isAccessAllowed/{doorId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun isAccessAllowed(@PathVariable("doorId") doorId: Long, @RequestParam("byUserId") userId: Long, @RequestParam("time") datetime: LocalDateTime): Boolean {
        return service.isAccessAllowedTo(doorId, userId, datetime)
    }

    @GetMapping("/isMaintenanceAccessAllowed/{doorId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun isMaintenanceAccessAllowed(@PathVariable("doorId") doorId: Long, @RequestParam("byUserId") userId: Long, @RequestParam("time") datetime: LocalDateTime): Boolean {
        return service.isMaintenanceAccessAllowedTo(doorId, userId, datetime)
    }
}