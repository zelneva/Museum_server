package anastasia.diplom.domain.resource

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

@RestController
@RequestMapping("/api/user")
@Api(tags = arrayOf("user"), description = "User API")
class UserResource(service: UserService) {

    @Autowired
    var userService = service

    @PostMapping
    @ApiOperation(value = "Create user", notes = "It permits to create a new user")
    @ApiResponses(
            ApiResponse(code = 201, message = "User created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(user: UserRequest) = ResponseEntity(userService.create(user), HttpStatus.CREATED)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove user", notes = "It permits to remove a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User removed successfully"),
            ApiResponse(code = 404, message = "User not found")
    )
    fun delete(@PathVariable("id") id: UUID) =
            ResponseEntity(userService.delete(id), HttpStatus.OK)


    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", notes = "It permits to update a user")
    @ApiResponses(
            ApiResponse(code = 200, message = "User update successfully"),
            ApiResponse(code = 404, message = "User not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, user: UserRequest)
            = ResponseEntity(userService.update(id, user), HttpStatus.OK)

}