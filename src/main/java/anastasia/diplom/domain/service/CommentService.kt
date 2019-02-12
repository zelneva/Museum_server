package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Comment
import anastasia.diplom.domain.repository.CommentRepository
import anastasia.diplom.domain.repository.UserRepository
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
    }

    @Autowired
    constructor(repository: CommentRepository, rt: RedisTemplate<String, String>, userRepo: UserRepository) {
        commentRepository = repository
        redisTemplate = rt
        userRepository = userRepo
    }


    @Transactional
    open fun create(commentRequest: CommentRequest, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            val comment = Comment()
            comment.text = commentRequest.text
            comment.date = commentRequest.date
            comment.showpiece = commentRequest.showpiece
            val userId = redisTemplate.opsForValue().get(session)
            comment.user = userRepository.findOne(UUID.fromString(userId))
            commentRepository.save(comment)
        }
    }


    @Transactional
    open fun update(id: UUID, commentRequest: CommentRequest, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            var comment = commentRepository.findOne(id)
            if (comment.user == userRepository.findOne(UUID.fromString(redisTemplate.opsForValue().get(session)))) {
                comment.text = commentRequest.text ?: comment.text
                comment.date = commentRequest.date
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


    fun findAllByShowpieceId(showpieceId: UUID): List<Comment> {
        val dataListByShowpieceId = mutableListOf<Comment>()
        for (l in commentRepository.findAll()) {
            if (l.showpiece!!.id == showpieceId) {
                dataListByShowpieceId.add(l)
            }
        }
        return dataListByShowpieceId.sortedBy { it.date }
    }
}