package anastasia.diplom.domain.controller

import anastasia.diplom.domain.models.ShowpieceLocaleData
import anastasia.diplom.domain.service.ShowpieceLocaleDataService
import anastasia.diplom.domain.vo.ShowpieceLocaleDataRequest
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
@RequestMapping("/api/locale/showpiece")
@Api(tags = arrayOf("showpiece"), description = "Showpiece locale data API")
class ShowpieceLocaleDataController(service: ShowpieceLocaleDataService) {

    @Autowired
    val showpieceLocaleDataService = service


    @GetMapping(value = ["/{id}"])
    @ApiOperation(value = "Find showpiece locale data", notes = "Find the showpiece locale data by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece local data found"),
            ApiResponse(code = 404, message = "Showpiece local data not found")
    )
    fun findOne(@PathVariable("id") id: UUID) = ResponseEntity.ok(showpieceLocaleDataService.findOne(id))


    @GetMapping
    @ApiOperation(value = "List showpiece locale data or list locale data about showpiece", notes = "List all data")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece local data found"),
            ApiResponse(code = 404, message = "Showpiece local data not found")
    )
    fun find(@RequestParam(value = "id", required = false) showpieceId: UUID?): ResponseEntity<List<ShowpieceLocaleData>> {
        if (showpieceId != null) {
            return ResponseEntity.ok(showpieceLocaleDataService.findDataById(showpieceId))
        }
        return ResponseEntity.ok(showpieceLocaleDataService.findAll())
    }


    @PostMapping
    @ApiOperation(value = "Create showpiece locale data", notes = "It permits to create a new showpiece")
    @ApiResponses(
            ApiResponse(code = 201, message = "Showpiece locale data created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(showpieceLocaleDataRequest: ShowpieceLocaleDataRequest, req: HttpServletRequest): ResponseEntity<Unit> {
        if (showpieceLocaleDataService.isAdmin(req.session.id)) {
            return ResponseEntity(showpieceLocaleDataService.create(showpieceLocaleDataRequest), HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove showpiece", notes = "It permits to remove a showpiece")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece locale data removed successfully"),
            ApiResponse(code = 404, message = "Showpiece locale data not found")
    )
    fun delete(@PathVariable("id") id: UUID, req: HttpServletRequest): ResponseEntity<Unit> {
        if (showpieceLocaleDataService.isAdmin(req.session.id)) {
            return ResponseEntity(showpieceLocaleDataService.delete(id), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update showpiece locale data", notes = "It permits to showpiece a author")
    @ApiResponses(
            ApiResponse(code = 200, message = "Showpiece locale data update successfully"),
            ApiResponse(code = 404, message = "Showpiece locale data not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, showpieceLocaleDataRequest: ShowpieceLocaleDataRequest,
               req: HttpServletRequest): ResponseEntity<ShowpieceLocaleData> {
        if (showpieceLocaleDataService.isAdmin(req.session.id)) {
            return ResponseEntity(showpieceLocaleDataService.update(id, showpieceLocaleDataRequest), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

}