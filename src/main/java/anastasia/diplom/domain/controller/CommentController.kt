package anastasia.diplom.domain.controller

import anastasia.diplom.domain.model.SessionObject
import anastasia.diplom.domain.service.CommentService
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
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/api/comment")
@Api(tags = arrayOf("comment"), description = "Comment API")
class CommentController(service: CommentService) {

    @Autowired
    val commentService = service


    @PostMapping
    @ApiOperation(value = "Create comment", notes = "It permits to create a new comment")
    @ApiResponses(
            ApiResponse(code = 201, message = "Comment created successfully"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun create(@RequestParam(value = "showpiece", required = false) showpieceId: String,
               @RequestParam(value = "text", required = false) text: String,
               @RequestParam("session") sessionId: String): ResponseEntity<Unit> {
        val date = Date()
        if (commentService.isUserLogin(sessionId)) {
            return ResponseEntity(commentService.create(showpieceId, text, date, sessionId), HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove comment", notes = "It permits to remove a comment")
    @ApiResponses(
            ApiResponse(code = 200, message = "Comment removed successfully"),
            ApiResponse(code = 404, message = "Comment not found")
    )
    fun delete(@PathVariable("id") id: UUID,
               @RequestParam("session") sessionId: String): ResponseEntity<Unit> {
        if (commentService.isUserLogin(sessionId)) {
            return ResponseEntity(commentService.delete(id, sessionId), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update comment", notes = "It permits to update a comment")
    @ApiResponses(
            ApiResponse(code = 200, message = "Comment update successfully"),
            ApiResponse(code = 404, message = "Comment not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID,
               @RequestParam("text") text: String,
               @RequestParam("session") sessionId: String): ResponseEntity<Unit> {
        if (commentService.isUserLogin(sessionId)) {
            return ResponseEntity(commentService.update(id, text, Date(), sessionId), HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @GetMapping
    @ApiOperation(value = "Find list comment of the showpiece", notes = "Find list comment of  the showpiece by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "List comment found"),
            ApiResponse(code = 404, message = "List comment not found")
    )
    fun findListCommentByShowpieceId(@RequestParam("showpieceId") showpieceId: UUID) = ResponseEntity.ok(commentService.findAllByShowpieceId(showpieceId))
}
