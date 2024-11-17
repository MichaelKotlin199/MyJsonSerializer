package org.example

import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.util.concurrent.Executors

@Serializable
data class TestData(val id: Int, val name: String, val attributes: Map<String, String>)

class MultithreadedJson {
    private val json = Json { prettyPrint = true }

    suspend fun <T> serialize(data: List<T>, serializer: KSerializer<T>): List<String> =
        coroutineScope {
            val dispatcher = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()).asCoroutineDispatcher()
            val jobs = data.map { item ->
                async(dispatcher) {
                    json.encodeToString(serializer, item)
                }
            }
            jobs.awaitAll().also { dispatcher.close() }
        }

    suspend fun <T> deserialize(jsonList: List<String>, deserializer: KSerializer<T>): List<T> =
        coroutineScope {
            val dispatcher = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()).asCoroutineDispatcher()
            val jobs = jsonList.map { jsonString ->
                async(dispatcher) {
                    json.decodeFromString(deserializer, jsonString)
                }
            }
            jobs.awaitAll().also { dispatcher.close() }
        }
}
