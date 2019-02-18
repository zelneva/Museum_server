package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Comment
import anastasia.diplom.domain.repository.CommentRepository
import anastasia.diplom.domain.repository.ShowpieceRepository
import anastasia.diplom.domain.repository.UserRepository
import anastasia.diplom.domain.service.ShowpieceService.Companion.showpieceRepository
import anastasia.diplom.domain.vo.CommentRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class CommentService {

    companion object {
        lateinit var commentRepository: CommentRepository
        lateinit var redisTemplate: RedisTemplate<String, String>
        lateinit var userRepository: UserRepository
        lateinit var showpieceRepository: ShowpieceRepository
        lateinit var userService: UserService
    }

    @Autowired
    constructor(repository: CommentRepository, rt: RedisTemplate<String, String>,
                userRepo: UserRepository, showpieceRepo: ShowpieceRepository,
                userSer: UserService) {
        commentRepository = repository
        redisTemplate = rt
        userRepository = userRepo
        showpieceRepository = showpieceRepo
        userService = userSer
    }


    @Transactional
    open fun create(showpieceId: String, text: String, date: Date, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            val comment = Comment()
            comment.text = text
            comment.date = date
            comment.showpiece = showpieceRepository.findOne(UUID.fromString(showpieceId))
            val userId = redisTemplate.opsForValue().get(session)
            comment.user = userRepository.findOne(UUID.fromString(userId))
            commentRepository.save(comment)
        }
    }


    @Transactional
    open fun update(id: UUID, text: String?, date: Date, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            var comment = commentRepository.findOne(id)
            if (comment.user == userRepository.findOne(UUID.fromString(redisTemplate.opsForValue().get(session)))) {
                comment.text = text ?: comment.text
                comment.date = date
                commentRepository.save(comment)
            }
        }
    }


    @Transactional
    open fun delete(id: UUID, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            if (commentRepository.findOne(id).user == userRepository.findOne(UUID.fromString(redisTemplate.opsForValue().get(session))))
                commentRepository.delete(id)
        }
    }


    fun findAllByShowpieceId(showpieceId: UUID) = commentRepository.findByShowpieceId(showpieceId).sortedBy { it.date }

    fun isUserLogin(sessionId: String)= userService.checkUserInRedis(sessionId)

}