package anastasia.diplom.domain.service

import java.util.*

open class AbstractService {

    companion object {
        lateinit var userService: UserService
    }

    constructor(userServ: UserService){
        userService = userServ
    }

    fun isAdmin(sessionId: String): Boolean {
        if (userService.checkUserInRedis(sessionId)) {
            val userId = userService.getUserIdFromSessionId(sessionId)
            val user = UserService.userRepository.findOne(UUID.fromString(userId))
            if (user.role == "admin") {
                return true
            }
        }
        return false
    }
}