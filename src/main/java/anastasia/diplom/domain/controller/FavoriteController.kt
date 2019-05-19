package anastasia.diplom.domain.controller

import anastasia.diplom.domain.service.FavoriteService
import anastasia.diplom.domain.vo.CommentRequest
import anastasia.diplom.domain.vo.FavoriteRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/favorite")
@Api(tags = arrayOf("favorite"), description = "Favorite API")
class FavoriteController(service: FavoriteService) {

    @Autowired
    val favoriteService = service


    @PostMapping
    @ApiOperation(value = "Create favorite", notes = "It permits to create a new favorite")
    @ApiResponses(
            ApiResponse(code = 201, message = "Favorite created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(@RequestParam(value = "showpiece", required = false) showpieceId: String,
               @RequestParam(value = "session", required = false) sessionId: String): ResponseEntity<Unit> {
        if (favoriteService.isUserLogin(sessionId)) {
            return ResponseEntity(favoriteService.create(showpieceId, sessionId), HttpStatus.CREATED)
        } else
            return ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove favorite", notes = "It permits to remove a favorite")
    @ApiResponses(
            ApiResponse(code = 200, message = "Favorite removed successfully"),
            ApiResponse(code = 404, message = "Favorite not found")
    )
    fun delete(@PathVariable("id") id: UUID,
               @RequestParam(value = "session", required = false) sessionId: String): ResponseEntity<Unit> {
        if (favoriteService.isUserLogin(sessionId)) {
            return ResponseEntity(favoriteService.delete(id, sessionId), HttpStatus.OK)
        } else
            return ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @GetMapping
    @ApiOperation(value = "Find favorite by user", notes = "Find the favorite by user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Favorite found"),
            ApiResponse(code = 404, message = "Favorite not found")
    )
    fun findFavoriteByUser( @RequestParam(value = "session", required = false) sessionId: String) = ResponseEntity.ok(favoriteService.favoriteByUser(sessionId))

}