package dcwiek.noisemeasurmentapp.domain.model


class AppUser(val id: Int, val username: String, val createdDate: String, val firstLogIn: Boolean, val userRoles: Set<UserRole>)