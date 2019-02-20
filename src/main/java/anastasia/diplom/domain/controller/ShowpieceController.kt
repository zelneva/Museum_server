package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.service.ShowpieceService
import anastasia.diplom.domain.vo.ShowpieceRequest
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
    fun create(showpiece: ShowpieceRequest, req: HttpServletRequest): ResponseEntity<Unit> {
        if (showpieceService.isAdmin(req.session.id)) {
            return ResponseEntity(showpieceService.create(showpiece), HttpStatus.CREATED)
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
    fun delete(@PathVariable("id") id: UUID, req: HttpServletRequest): ResponseEntity<Unit> {
        if (showpieceService.isAdmin(req.session.id)) {
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
    fun update(@PathVariable("id") id: UUID, showpiece: ShowpieceRequest, req: HttpServletRequest): ResponseEntity<Showpiece> {
        if (showpieceService.isAdmin(req.session.id)) {
            return ResponseEntity(showpieceService.update(id, showpiece), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

}