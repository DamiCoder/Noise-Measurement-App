package dcwiek.noisemeasurmentapp.domain.model

import java.time.LocalDateTime
import java.util.*

class AppUser(val id: Int, val username: String, val createdDate: String, val userRoles: Set<UserRole>) {
    constructor(): this(0, "", LocalDateTime.now().toString(), Collections.emptySet())
}