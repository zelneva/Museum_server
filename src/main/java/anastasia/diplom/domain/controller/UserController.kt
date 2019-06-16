package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.LoginForm
import anastasia.diplom.domain.model.SessionObject
import anastasia.diplom.domain.model.User
import anastasia.diplom.domain.service.UserService
import anastasia.diplom.domain.vo.UserRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/user")
@EnableRedisHttpSession
@Api(tags = ["user"], description = "User API")
open class UserController(service: UserService) {

    @Autowired
    var userService = service

    @PostMapping("/register")
    @ApiOperation(value = "Sign up user", notes = "It permits to create a new user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun register(@RequestParam(value = "username", required = false) username: String,
                 @RequestParam(value = "password", required = false) password: String,
                 @RequestParam(value = "name", required = false) name: String,
                 req: HttpServletRequest): ResponseEntity<SessionObject> {
        val user = userService.create(username, password, name)
        val sessionObj = SessionObject(UUID.fromString(req.session.id),
                UUID.fromString(userService.getIdByUsername(user.username!!.trim())))
        userService.addSessionRedis(sessionObj.sessionId.toString(), user.username!!)
        return ResponseEntity(sessionObj, HttpStatus.CREATED)
    }


    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "Sign in user", notes = "It permits to login user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User login successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun login(@RequestBody loginForm: LoginForm,
              req: HttpServletRequest): ResponseEntity<SessionObject> {
        if (loginForm.username!!.trim() == "" || loginForm.password!!.trim() == "") {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        } else if (!userService.check(loginForm.username!!, loginForm.password!!)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } else if (userService.check(loginForm.username!!, loginForm.password!!)) {
            val sessionObj = SessionObject(UUID.fromString(req.session.id),
                    UUID.fromString(userService.getIdByUsername(loginForm.username!!.trim())))
            userService.addSessionRedis(sessionObj.sessionId.toString(), loginForm.username!!)
            return ResponseEntity(sessionObj, HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @PostMapping("/logout")
    @ApiOperation(value = "Logout user", notes = "It permits to logout a user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User logout successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun logout(@RequestParam sessionId: String): ResponseEntity<Any> {
        return ResponseEntity(userService.deleteSessionRedis(sessionId), HttpStatus.OK)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove user", notes = "It permits to remove a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User removed successfully"),
            ApiResponse(code = 404, message = "User not found")
    )
    fun delete(@RequestParam(value = "sessionId", required = false) sessionId: String): ResponseEntity<Unit> {
        val userId = userService.getUserIdFromSessionId(sessionId)
        userService.deleteSessionRedis(sessionId)
        return ResponseEntity(userService.delete(UUID.fromString(userId)), HttpStatus.OK)
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", notes = "It permits to update a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User update successfully"),
            ApiResponse(code = 404, message = "User not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID,
               @RequestParam(value = "name", required = false) name: String?,
               @RequestParam(value = "username", required = false) username: String?,
               @RequestParam(value = "password", required = false) password: String?,
               @RequestParam(value = "session", required = false) session: String?)
            = ResponseEntity(userService.update(id, name!!, username!!, password!!), HttpStatus.OK)


    @GetMapping("/{id}")
    @ApiOperation(value = "Is user info")
    @ApiResponses(
            ApiResponse(code = 201, message = "User find successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun getUser(@PathVariable("id") id: UUID): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return ResponseEntity(user, HttpStatus.OK)
    }

}
