package anastasia.diplom.domain.resource

import anastasia.diplom.domain.service.AuthorService
import anastasia.diplom.domain.vo.AuthorRequest
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
@RequestMapping("/api/author")
@Api(tags = arrayOf("author"), description = "Author API")
class AuthorResource (service: AuthorService){

    @Autowired
    val authorService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find author", notes = "Find the Author by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author found"),
            ApiResponse(code = 404, message = "Author not found")
    )
    fun findOne(@PathVariable("id") id: UUID)
            = ResponseEntity.ok(authorService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List authors", notes = "List all authors")
    @ApiResponses(
            ApiResponse(code = 200, message = "Authors found"),
            ApiResponse(code = 404, message = "Authors not found")
    )
    fun findAll() = ResponseEntity.ok(authorService.findAll())


    @PostMapping
    @ApiOperation(value = "Create author", notes = "It permits to create a new author")
    @ApiResponses(
            ApiResponse(code = 201, message = "Author created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(author: AuthorRequest)
            = ResponseEntity(authorService.create(author), HttpStatus.CREATED)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove author", notes = "It permits to remove a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author removed successfully"),
            ApiResponse(code = 404, message = "Author not found")
    )
    fun delete(@PathVariable("id") id: UUID) =
            ResponseEntity(authorService.delete(id), HttpStatus.OK)


    @PutMapping("/{id}")
    @ApiOperation(value = "Update author", notes = "It permits to update a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author update successfully"),
            ApiResponse(code = 404, message = "Author not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, author: AuthorRequest)
            = ResponseEntity(authorService.update(id, author), HttpStatus.OK)

}