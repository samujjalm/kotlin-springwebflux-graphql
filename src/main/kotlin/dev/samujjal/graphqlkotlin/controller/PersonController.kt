package dev.samujjal.graphqlkotlin.controller

import dev.samujjal.graphqlkotlin.database.Person
import dev.samujjal.graphqlkotlin.database.PersonRepository
import dev.samujjal.graphqlkotlin.dto.Request
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/people")
class PersonController(
    private val personRepository: PersonRepository
) {
    @GetMapping("/sam")
    suspend fun getSam(): String {
        return "Samujal made this"
    }

    @GetMapping("/search")
    suspend fun getPeopleByName(@RequestParam name:String) : List<Person> {
        return personRepository.findByNameContains(name)
    }

    @GetMapping("/adults")
    suspend fun getAdults(): List<Person> {
        return personRepository.findByAgeGreaterThanEqual(18)
    }

    @GetMapping("/all")
    suspend fun getAllPerson(): List<Person> {
        return personRepository.findAll().toList()
    }

    @PostMapping
    suspend fun createPerson(@RequestBody request: Request.CreatePerson) {
        personRepository.save(Person(0,request.name, request.age))
    }

    @PutMapping
    suspend fun updatePerson(@RequestBody request: Request.UpdatePerson): ResponseEntity<Void> {
        val person = personRepository.findById(request.id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        person.name = request.name
        person.age = request.age
        personRepository.save(person)
        return ResponseEntity(HttpStatus.OK)
    }
}