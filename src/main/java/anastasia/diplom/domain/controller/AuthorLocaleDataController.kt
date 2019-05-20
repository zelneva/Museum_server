package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.AuthorLocaleData
import anastasia.diplom.domain.service.AuthorLocaleDataService
import anastasia.diplom.domain.vo.AuthorLocaleDataRequest
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
@RequestMapping("/api/locale/author")
@Api(tags = arrayOf("author"), description = "Author locale data API")
class AuthorLocaleDataController(service: AuthorLocaleDataService) {

    @Autowired
    val authorLocaleDataService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find author locale data", notes = "Find the Author locale data by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author local data found"),
            ApiResponse(code = 404, message = "Author local data not found")
    )
    fun findOne(@PathVariable("id") id: UUID) = ResponseEntity.ok(authorLocaleDataService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List author locale data or list locale data about author", notes = "List all data")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author local data found"),
            ApiResponse(code = 404, message = "Author local data not found")
    )
    fun find(@RequestParam(value = "id", required = false) authorId: UUID?): ResponseEntity<List<AuthorLocaleData>> {
        if (authorId != null) {
            return ResponseEntity.ok(authorLocaleDataService.findDataById(authorId))
        }
        return ResponseEntity.ok(authorLocaleDataService.findAll())
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove author", notes = "It permits to remove a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author locale data removed successfully"),
            ApiResponse(code = 404, message = "Author locale data not found")
    )
    fun delete(@PathVariable("id") id: UUID, session: String): ResponseEntity<Unit> {
        return if (authorLocaleDataService.isAdmin(session)) {
            ResponseEntity(authorLocaleDataService.delete(id), HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}
