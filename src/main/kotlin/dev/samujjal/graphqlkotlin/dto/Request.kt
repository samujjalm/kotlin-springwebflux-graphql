package dev.samujjal.graphqlkotlin.dto

object Request {
    data class CreatePerson(val name: String, val age: Int)
    data class UpdatePerson(val id: Int, val name: String, val age: Int)
}