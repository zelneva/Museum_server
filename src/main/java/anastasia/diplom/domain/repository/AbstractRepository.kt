package anastasia.diplom.domain.repository

import java.util.ArrayList

abstract class AbstractRepository<T> {
    private val elements = ArrayList<T>()

    fun delete(entity: T) {
        val iterator = elements.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next!!.equals(entity)) {
                iterator.remove()
                break
            }
        }
    }


    fun findAll(): List<T> {
        return this.elements
    }


    fun save(entity: T): T {
        this.elements.add(entity)
        return entity
    }


    fun findOne(id: String): T {
        return this.elements.stream().filter { el -> el!!.equals(id) }.findFirst().get()
    }

}