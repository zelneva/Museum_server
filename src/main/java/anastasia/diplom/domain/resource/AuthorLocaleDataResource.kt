package anastasia.diplom.domain.resource

import anastasia.diplom.domain.models.AuthorLocaleData
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

@RestController
@RequestMapping("/api/locale/author")
@Api(tags = arrayOf("author"), description = "Author locale data API")
class AuthorLocaleDataResource (service: AuthorLocaleDataService){

    @Autowired
    val authorLocaleDataService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find author locale data", notes = "Find the Author locale data by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author local data found"),
            ApiResponse(code = 404, message = "Author local data not found")
    )
    fun findOne(@PathVariable("id") id: UUID)
            = ResponseEntity.ok(authorLocaleDataService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List author locale data or list locale data about author", notes = "List all data")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author local data found"),
            ApiResponse(code = 404, message = "Author local data not found")
    )
    fun find(@RequestParam(value = "id", required = false) authorId: UUID?): ResponseEntity<List<AuthorLocaleData>> {
        if(authorId != null){
            return ResponseEntity.ok(authorLocaleDataService.findDataById(authorId))
        }
        return ResponseEntity.ok(authorLocaleDataService.findAll())
    }


    @PostMapping
    @ApiOperation(value = "Create author locale data", notes = "It permits to create a new author")
    @ApiResponses(
            ApiResponse(code = 201, message = "Author locale data created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(authorLocaleData: AuthorLocaleDataRequest)
            = ResponseEntity(authorLocaleDataService.create(authorLocaleData), HttpStatus.CREATED)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove author", notes = "It permits to remove a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author locale data removed successfully"),
            ApiResponse(code = 404, message = "Author locale data not found")
    )
    fun delete(@PathVariable("id") id: UUID) =
            ResponseEntity(authorLocaleDataService.delete(id), HttpStatus.OK)


    @PutMapping("/{id}")
    @ApiOperation(value = "Update author locale data", notes = "It permits to update a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author locale data update successfully"),
            ApiResponse(code = 404, message = "Author locale data not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, authorLocaleData: AuthorLocaleDataRequest)
            = ResponseEntity(authorLocaleDataService.update(id, authorLocaleData), HttpStatus.OK)

}