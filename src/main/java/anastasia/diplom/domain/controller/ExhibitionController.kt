package anastasia.diplom.domain.controller

import anastasia.diplom.domain.service.ExhibitionService
import anastasia.diplom.domain.vo.ExhibitionRequest
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
@RequestMapping("/api/exhibition")
@Api(tags = arrayOf("exhibition"), description = "Exhibition API")
class ExhibitionController(service: ExhibitionService) {

    @Autowired
    val exhibitionService = service

    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find exhibition", notes = "Find the exhibition by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Exhibition found"),
            ApiResponse(code = 404, message = "Exhibition not found")
    )
    fun findOne(@PathVariable("id") id: UUID)
            = ResponseEntity.ok(exhibitionService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List exhibition", notes = "List all exhibition")
    @ApiResponses(
            ApiResponse(code = 200, message = "Exhibition found"),
            ApiResponse(code = 404, message = "Exhibition not found")
    )
    fun findAll() = ResponseEntity.ok(exhibitionService.findAll())


    @PostMapping
    @ApiOperation(value = "Create exhibition", notes = "It permits to create a new exhibition")
    @ApiResponses(
            ApiResponse(code = 201, message = "Exhibition created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(exhibitionRequest: ExhibitionRequest)
            = ResponseEntity(exhibitionService.create(exhibitionRequest ), HttpStatus.CREATED)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove exhibition", notes = "It permits to remove a exhibition")
    @ApiResponses(
            ApiResponse(code = 200, message = "Exhibition removed successfully"),
            ApiResponse(code = 404, message = "Exhibition not found")
    )
    fun delete(@PathVariable("id") id: UUID) =
            ResponseEntity(exhibitionService.delete(id), HttpStatus.OK)


    @PutMapping("/{id}")
    @ApiOperation(value = "Update exhibition", notes = "It permits to update a exhibition")
    @ApiResponses(
            ApiResponse(code = 200, message = "Exhibition update successfully"),
            ApiResponse(code = 404, message = "Exhibition not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun updateExhibition(@PathVariable("id") id: UUID, exhibitionRequest: ExhibitionRequest)
            = ResponseEntity(exhibitionService.update(id, exhibitionRequest), HttpStatus.OK)

}
