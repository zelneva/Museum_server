package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.User
import anastasia.diplom.domain.model.User.Companion.compare
import anastasia.diplom.domain.model.User.Companion.generatePassword
import anastasia.diplom.domain.repository.UserRepository
import anastasia.diplom.domain.vo.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import org.springframework.data.redis.core.RedisTemplate


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
    open fun create(username: String, password: String, name:String): User {
        val user = User()
        user.name = name.trim()
        user.password = generatePassword(password).trim()
        user.role = "visitor"
        user.username = username.trim()
        user.srcPhoto = null
        userRepository.save(user)
        return user
    }



    @Transactional
    open fun update(id: UUID, name: String, username: String, password: String): User {
        val user = userRepository.findOne(id)
        user.username = username
        user.name = name
        user.password = password
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
            return compare(password, generatePassword(password))
        } else return false
    }


    fun getIdByUsername(username: String): String = userRepository.findByUsername(username)!!.id.toString()


    fun findByUsername(username: String) = userRepository.findByUsername(username)


    fun findUserById(id: UUID) = userRepository.findOne(id)


    fun deleteSessionRedis(session: String){
        redisTemplate.opsForValue().operations.delete(session)
    }

    fun checkUserInRedis(sessionId: String)= redisTemplate.opsForValue().get(sessionId) != null

    fun getUserIdFromSessionId(sessionId: String) = redisTemplate.opsForValue().get(sessionId)



}