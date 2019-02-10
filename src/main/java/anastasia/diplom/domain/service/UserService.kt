package anastasia.diplom.domain.service

import anastasia.diplom.domain.exception.MuseumNotFoundException
import anastasia.diplom.domain.exception.UserNotFoundException
import anastasia.diplom.domain.models.User
import anastasia.diplom.domain.repository.UserRepository
import anastasia.diplom.domain.vo.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.boot.json.JacksonJsonParser
import org.codehaus.groovy.ast.tools.GeneralUtils.params
import org.springframework.http.RequestEntity.post
import org.springframework.http.ResponseEntity.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.net.URI


@Service
@Transactional(readOnly = true)
open class UserService {

    companion object {
        lateinit var userRepository: UserRepository
        lateinit var redisTemplate: RedisTemplate<String, String>
    }

    @Autowired
    constructor(repository: UserRepository, rt: RedisTemplate<String, String>) {
        userRepository = repository
        redisTemplate = rt
    }


    @Transactional
    open fun create(userRequest: UserRequest) {
        val user = User()
        user.name = userRequest.name!!.trim()
        user.password = user.generatePassword(userRequest.password!!).trim()
        user.role = "visitor"
        user.username = userRequest.username!!.trim()
        user.srcPhoto = userRequest.srcPhoto
        userRepository.save(user)
    }


    @Transactional
    open fun update(id: UUID, userRequest: UserRequest): User {
        val user = userRepository.findOne(id)
        user.username = userRequest.username ?: user.username
        user.name = userRequest.name ?: user.name
        user.password = userRequest.password ?: user.password
        user.srcPhoto = userRequest.srcPhoto ?: user.srcPhoto
        return userRepository.save(user)

    }


    @Transactional
    open fun delete(id: UUID) {
        userRepository.delete(id)
    }


    // add session of user to redis
    fun addSessionRedis(session: String, username: String) {
        val userId = getIdByUsername(username)
        redisTemplate.opsForValue().set(session, userId)
    }

    //check username exists db and compare password
    fun check(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            return user.compare(password, user.generatePassword(password))
        } else return false
    }


    fun getIdByUsername(username: String): String = userRepository.findByUsername(username)!!.id.toString()


    fun findByUsername(username: String) = userRepository.findByUsername(username)


    fun deleteSessionRedis(session: String){
        redisTemplate.opsForValue().operations.delete(session)
    }
}