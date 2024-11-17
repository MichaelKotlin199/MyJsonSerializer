package org.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    val testData = List(1000) { TestData(it, "Name $it", mapOf("key" to "value")) }
    val gson = Gson()
    val jackson = ObjectMapper().registerModule(KotlinModule())
    val kotlinx = MultithreadedJson()

    runBlocking {
        val kotlinxSerializationTime = measureTimeMillis {
            val jsonStrings = kotlinx.serialize(testData, TestData.serializer())
            kotlinx.deserialize(jsonStrings, TestData.serializer())
        }
        println("kotlinx.serialization (multithreaded): $kotlinxSerializationTime ms")

        val gsonTime = measureTimeMillis {
            val jsonStrings = testData.map { gson.toJson(it) }
            jsonStrings.map { gson.fromJson(it, TestData::class.java) }
        }
        println("Gson: $gsonTime ms")

        val jacksonTime = measureTimeMillis {
            val jsonStrings = testData.map { jackson.writeValueAsString(it) }
            jsonStrings.map { jackson.readValue<TestData>(it) }
        }
        println("Jackson: $jacksonTime ms")
    }
}
