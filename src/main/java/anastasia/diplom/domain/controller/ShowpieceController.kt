package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.service.ShowpieceService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*


@RestController
@RequestMapping("/api/showpiece")
@Api(tags = arrayOf("showpiece"), description = "Showpiece API")
class ShowpieceController(service: ShowpieceService) {

    @Autowired
    var showpieceService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find showpiece", notes = "Find the showpiece by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece found"),
            ApiResponse(code = 404, message = "Showpiece not found")
    )
    fun findOne(@PathVariable("id") id: UUID) = ResponseEntity.ok(showpieceService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List showpieces", notes = "List all showpieces ")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpieces found"),
            ApiResponse(code = 404, message = "Showpieces not found")
    )
    fun findAll(): ResponseEntity<List<Showpiece>> = ResponseEntity.ok(showpieceService.findAll())


    @PostMapping
    @ApiOperation(value = "Create showpiece",
            notes = "Data needs to pass to the parametrs: exhibition.id, author.id, date, genre, photo")
    @ApiResponses(
            ApiResponse(code = 201, message = "Showpiece created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(srcPhoto: String, year: String, authorName: String,
               titleRus: String, descriptionRus: String,
               titleEng: String, descriptionEng: String,
               titleGer: String, descriptionGer: String, session: String): ResponseEntity<Unit> {
        if (showpieceService.isAdmin(session)) {
            return ResponseEntity(showpieceService.create(srcPhoto, year, authorName,
                    titleRus, descriptionRus,
                    titleEng, descriptionEng,
                    titleGer, descriptionGer), HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove showpiece", notes = "It permits to remove a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece removed successfully"),
            ApiResponse(code = 404, message = "Showpiece not found")
    )
    fun delete(@PathVariable("id") id: UUID, session: String): ResponseEntity<Unit> {
        if (showpieceService.isAdmin(session)) {
            return ResponseEntity(showpieceService.delete(id), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update showpiece", notes = "It permits to update a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece update successfully"),
            ApiResponse(code = 404, message = "Showpiece not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: String, srcPhoto: String?, year: String, authorName: String,
               titleRus: String, descriptionRus: String, titleEng: String,
               descriptionEng: String, titleGer: String, descriptionGer: String, session: String)
            : ResponseEntity<Showpiece> {
        if (showpieceService.isAdmin(session)) {
            return ResponseEntity(showpieceService.update(UUID.fromString(id), srcPhoto, year, authorName, titleRus,
                    descriptionRus, titleEng, descriptionEng, titleGer, descriptionGer), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @PutMapping("/exhibition/{showpieceId}")
    @ApiOperation(value = "Update showpiece", notes = "It permits to update a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece update successfully"),
            ApiResponse(code = 404, message = "Showpiece not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("showpieceId") id: String, exhibitionId: String, session: String): ResponseEntity<Unit> {
        if (showpieceService.isAdmin(session)) {
            return ResponseEntity(showpieceService.updateExhibition(id, exhibitionId), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @GetMapping("/exhibition/{exhibitionId}")
    @ApiOperation(value = "Find showpiece by exhibition", notes = "It permits to find a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece find successfully"),
            ApiResponse(code = 404, message = "Showpiece not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun findListShowpieceByExhibitionId(@PathVariable("exhibitionId") exhibitionId: String): ResponseEntity<List<Showpiece>> {
        return ResponseEntity(showpieceService.getShowpieceByExhibitionId(exhibitionId), HttpStatus.OK)
    }


    @GetMapping("/author/{authorId}")
    @ApiOperation(value = "Find showpiece by author", notes = "It permits to find a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece find successfully"),
            ApiResponse(code = 404, message = "Showpiece not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun findListShowpieceByAuthor(@PathVariable("authorId") authorId: String): ResponseEntity<List<Showpiece>> {
        return ResponseEntity(showpieceService.getShowpieceByAuthor(authorId), HttpStatus.OK)
    }

}
