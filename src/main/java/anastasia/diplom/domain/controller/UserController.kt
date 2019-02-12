package anastasia.diplom.domain.controller

import anastasia.diplom.domain.models.SessionObject
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
                 @RequestParam(value = "name", required = false) name: String): ResponseEntity<Unit> {
        return ResponseEntity(userService.create(username, password, name), HttpStatus.CREATED)
    }


    @PostMapping("/login")
    @ApiOperation(value = "Sign in user", notes = "It permits to login user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User login successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun login(@RequestParam(value = "username", required = false) username: String,
               @RequestParam(value = "password", required = false) password: String,
               req: HttpServletRequest): ResponseEntity<SessionObject> {
        if (username.trim() == "" || password.trim() == "") {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        } else if (!userService.check(username, password)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }else{
            val sessionObj = SessionObject(req.session.id, userService.getIdByUsername(username.trim()))
            userService.addSessionRedis(sessionObj.sessionId, username)
            return ResponseEntity(sessionObj, HttpStatus.CREATED)
        }
    }


    @PostMapping("/logout")
    @ApiOperation(value = "Logout user", notes = "It permits to logout a user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User logout successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun logout(req: HttpServletRequest){
        userService.deleteSessionRedis(req.requestedSessionId)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove user", notes = "It permits to remove a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User removed successfully"),
            ApiResponse(code = 404, message = "User not found")
    )
    fun delete(@PathVariable("id") id: UUID, req: HttpServletRequest) {
        userService.deleteSessionRedis(req.requestedSessionId)
        ResponseEntity(userService.delete(id), HttpStatus.OK)
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", notes = "It permits to update a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User update successfully"),
            ApiResponse(code = 404, message = "User not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, user: UserRequest) = ResponseEntity(userService.update(id, user), HttpStatus.OK)
}
