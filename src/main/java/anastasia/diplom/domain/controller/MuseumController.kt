package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.Museum
import anastasia.diplom.domain.service.MuseumService
import anastasia.diplom.domain.vo.MuseumRequest
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
@RequestMapping("/api/museum")
@Api(tags = arrayOf("museum"), description = "Museum API")
class MuseumController(service: MuseumService) {

    @Autowired
    val museumService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find museum", notes = "Find the Museum by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Museum found"),
            ApiResponse(code = 404, message = "Museum not found")
    )
    fun findOne(@PathVariable("id") id: UUID) = ResponseEntity.ok(museumService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List museums or museum by name", notes = "List all museums or museum by name")
    @ApiResponses(
            ApiResponse(code = 200, message = "Museums found"),
            ApiResponse(code = 404, message = "Museums not found")
    )
    fun find(@RequestParam(value = "name", required = false) name: String?): ResponseEntity<List<Museum>> {
        if (name == null) {
            return ResponseEntity.ok(museumService.findAll())
        }
        return ResponseEntity.ok(museumService.findByName(name))
    }


    @PostMapping
    @ApiOperation(value = "Create museum", notes = "It permits to create a new museum")
    @ApiResponses(
            ApiResponse(code = 201, message = "Museum created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(@RequestParam name: String, @RequestParam address: String, @RequestParam session: String): ResponseEntity<Unit> {
        return if (museumService.isAdmin(session)) {
            ResponseEntity(museumService.create(MuseumRequest(name, address)), HttpStatus.CREATED)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove museum", notes = "It permits to remove a museum")
    @ApiResponses(
            ApiResponse(code = 200, message = "Museum removed successfully"),
            ApiResponse(code = 404, message = "Museum not found")
    )
    fun delete(@PathVariable("id") id: UUID, @RequestParam session: String): ResponseEntity<Unit> {
        return if (museumService.isAdmin(session)) {
            ResponseEntity(museumService.delete(id), HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update museum", notes = "It permits to update a museum")
    @ApiResponses(
            ApiResponse(code = 200, message = "Museum update successfully"),
            ApiResponse(code = 404, message = "Museum not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, @RequestParam name: String, @RequestParam address: String, @RequestParam session: String): ResponseEntity<Museum> {
        return if (museumService.isAdmin(session)) {
            ResponseEntity(museumService.update(id, MuseumRequest(name, address)), HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

}