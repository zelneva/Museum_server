package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Favorite
import anastasia.diplom.domain.repository.FavoriteRepository
import anastasia.diplom.domain.repository.UserRepository
import anastasia.diplom.domain.vo.FavoriteRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class FavoriteService {

    companion object {
        lateinit var favoriteRepository: FavoriteRepository
        lateinit var redisTemplate: RedisTemplate<String, String>
        lateinit var userRepository: UserRepository
    }

    @Autowired
    constructor(repository: FavoriteRepository, rt: RedisTemplate<String, String>, userRepo: UserRepository) {
        favoriteRepository = repository
        redisTemplate = rt
        userRepository = userRepo
    }


    @Transactional
    open fun create(favoriteRequest: FavoriteRequest, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            val favorite = Favorite()
            favorite.showpiece = favoriteRequest.showpiece
            val userId = redisTemplate.opsForValue().get(session)
            favorite.user = userRepository.findOne(UUID.fromString(userId))
            favoriteRepository.save(favorite)
        }
    }


    @Transactional
    open fun delete(id: UUID, session: String) {
        if (redisTemplate.opsForValue().get(session) != null) {
            val userId = redisTemplate.opsForValue().get(session)
            if (favoriteRepository.findOne(id).user == userRepository.findOne(UUID.fromString(userId))) {
                favoriteRepository.delete(id)
            }
        }
    }


    private fun favoriteByUserId(userId: UUID) = favoriteRepository.findByUserId(userId)


    fun favoriteByUser(session: String): List<Favorite> {
        if (redisTemplate.opsForValue().get(session) != null) {
            val userId = redisTemplate.opsForValue().get(session)
            return favoriteByUserId(UUID.fromString(userId))
        }else{
            return emptyList()
        }
    }
}
