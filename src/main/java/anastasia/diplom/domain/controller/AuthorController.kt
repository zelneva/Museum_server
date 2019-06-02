package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.Author
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
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/author")
@Api(tags = arrayOf("author"), description = "Author API")
class AuthorController(service: AuthorService) {

    @Autowired
    val authorService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find author", notes = "Find the Author by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author found"),
            ApiResponse(code = 404, message = "Author not found")
    )
    fun findOne(@PathVariable("id") id: UUID) = ResponseEntity.ok(authorService.findOne(id)!!)


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
    fun create(photoSrc: String?, bornAt: String, deadAt: String,
               titleRus: String, descriptionRus: String,
               titleEng: String, descriptionEng: String,
               titleGer: String, descriptionGer: String, session: String): ResponseEntity<Unit> {
        return if (authorService.isAdmin(session)) {
            ResponseEntity(authorService.create(photoSrc,
                    bornAt, deadAt,
                    titleRus, descriptionRus,
                    titleEng, descriptionEng,
                    titleGer, descriptionGer), HttpStatus.CREATED)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove author", notes = "It permits to remove a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author removed successfully"),
            ApiResponse(code = 404, message = "Author not found")
    )
    fun delete(@PathVariable("id") id: UUID, session: String): ResponseEntity<Unit> {
        return if (authorService.isAdmin(session)) {
            ResponseEntity(authorService.delete(id), HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update author", notes = "It permits to update a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Author update successfully"),
            ApiResponse(code = 404, message = "Author not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, photoSrc: String?,
               bornAt: String, deadAt: String,
               titleRus: String, descriptionRus: String,
               titleEng: String, descriptionEng: String,
               titleGer: String, descriptionGer: String, session: String): ResponseEntity<Author> {
        return if (authorService.isAdmin(session)) {
            ResponseEntity(authorService.update(id,
                    photoSrc, bornAt, deadAt,
                    titleRus, descriptionRus,
                    titleEng, descriptionEng,
                    titleGer, descriptionGer), HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)

    }

}