package anastasia.diplom.domain.controller

import anastasia.diplom.domain.service.CommentService
import anastasia.diplom.domain.vo.CommentRequest
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
import javax.servlet.http.HttpServletRequest

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
    fun create(commentRequest: CommentRequest, req: HttpServletRequest)
            = ResponseEntity(commentService.create(commentRequest, req.session.id ), HttpStatus.CREATED)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove comment", notes = "It permits to remove a comment")
    @ApiResponses(
            ApiResponse(code = 200, message = "Comment removed successfully"),
            ApiResponse(code = 404, message = "Comment not found")
    )
    fun delete(@PathVariable("id") id: UUID, req: HttpServletRequest) =
            ResponseEntity(commentService.delete(id, req.session.id), HttpStatus.OK)


    @PutMapping("/{id}")
    @ApiOperation(value = "Update comment", notes = "It permits to update a comment")
    @ApiResponses(
            ApiResponse(code = 200, message = "Comment update successfully"),
            ApiResponse(code = 404, message = "Comment not found"),
            ApiResponse(code = 400, message = "Invalid request")
    )
    fun update(@PathVariable("id") id: UUID, commentRequest: CommentRequest, req: HttpServletRequest)
            = ResponseEntity(commentService.update(id, commentRequest, req.session.id), HttpStatus.OK)


    @GetMapping
    @ApiOperation(value = "Find list comment of the exhibition", notes = "Find list comment of  the exhibition by ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "List comment found"),
            ApiResponse(code = 404, message = "List comment not found")
    )
    fun findListCommentByExhibitionId(@RequestParam("exhibition_id") exhibitionId: UUID)
            = ResponseEntity.ok(commentService.findAllByShowpieceId(exhibitionId))

}