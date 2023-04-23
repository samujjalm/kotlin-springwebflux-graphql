package dev.samujjal.graphqlkotlin.database

import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Table
data class Person(@Id var id: Int = 0,
                  var name: String = "",
                  var age: Int = 0)

interface PersonRepository: CoroutineCrudRepository<Person, Int> {
    suspend fun findByNameContains(name: String): List<Person>
    suspend fun findByAgeGreaterThanEqual(age: Int): List<Person>

    @Query("select * from person where age = :age and name like '%:likeName%'")
    suspend fun findPersonOfAgeAndNameLike(age: Int, likeName:String): List<Person>
}
