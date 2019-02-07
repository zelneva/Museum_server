package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.User
import anastasia.diplom.domain.repository.UserRepository
import anastasia.diplom.domain.vo.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class UserService {

    companion object {
        lateinit var userRepository: UserRepository
    }

    @Autowired
    constructor(repository: UserRepository){
        userRepository = repository
    }


    @Transactional
    open fun create(userRequest: UserRequest){
        val user = User()
        user.name = userRequest.name!!
        user.password = user.generatePassword(userRequest.password!!)
        user.role = "visitor"
        user.username = userRequest.username!!
        userRepository.save(user)
    }


    @Transactional
    open fun update(id: UUID, userRequest: UserRequest):User{
        val user = userRepository.findOne(id)
        user.username = userRequest.username ?: user.username
        user.name = userRequest.name ?: user.name
        user.password = userRequest.password ?: user.password
        return userRepository.save(user)

    }


    @Transactional
    open fun delete(id:UUID){
        userRepository.delete(id)
    }
}