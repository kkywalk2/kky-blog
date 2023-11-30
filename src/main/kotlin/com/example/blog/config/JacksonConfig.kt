package com.example.blog.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Page

@Configuration
class JacksonConfig {

    @Bean
    fun jackson(): ObjectMapper {
        val pageModule = SimpleModule()
        pageModule.addSerializer(Page::class.java, PageJsonSerializer())

        return jacksonObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .findAndRegisterModules()
            .registerModule(pageModule)
    }

}

// This is workaround for spring-boot 3.2
class PageJsonSerializer : JsonSerializer<Page<*>>() {

    override fun serialize(page: Page<*>, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeStartObject()

        gen.writeObjectField("content", page.content)

        gen.writeBooleanField("empty", page.isEmpty)
        gen.writeBooleanField("first", page.isFirst)
        gen.writeBooleanField("last", page.isLast)
        gen.writeNumberField("number", page.number)
        gen.writeNumberField("numberOfElements", page.numberOfElements)
        gen.writeNumberField("size", page.size)
        gen.writeNumberField("totalPages", page.totalPages)
        gen.writeNumberField("totalElements", page.totalElements)

        if (page.pageable.isUnpaged) {
            gen.writeStringField("pageable", "INSTANCE")
        } else {
            gen.writeObjectField("pageable", page.pageable)
        }

        gen.writeObjectField("sort", page.sort)

        gen.writeEndObject()
    }
}
