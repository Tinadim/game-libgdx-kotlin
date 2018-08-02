package com.reis.game.entity

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.reis.game.entity.templates.EntityTemplate
import java.util.concurrent.ExecutionException

class EntityTemplateCache {
    companion object {
        const val CACHE_SIZE = 20
        const val TEMPLATES_PATH = "com.reis.game.entity.templates"
    }

    private val cacheLoader = object: CacheLoader<String, EntityTemplate>() {
        @Throws(Exception::class)
        override fun load(key: String): EntityTemplate {
            return createTemplateInstance(key)
        }
    }
    private val cache: LoadingCache<String, EntityTemplate> = CacheBuilder
            .newBuilder()
            .maximumSize(CACHE_SIZE.toLong())
            .build(cacheLoader)

    @Throws(Exception::class)
    private fun createTemplateInstance(className: String): EntityTemplate {
        val entityTemplateClass = getEntityTemplateClass(className)
        val constructor = entityTemplateClass.getDeclaredConstructor()
        return constructor.newInstance()
    }

    @Throws(ClassNotFoundException::class)
    private fun getEntityTemplateClass(name: String): Class<out EntityTemplate> {
        val className = "$TEMPLATES_PATH.$name"
        return Class.forName(className) as Class<out EntityTemplate>
    }

    @Throws(ExecutionException::class)
    fun getEntityTemplate(className: String): EntityTemplate {
        return cache.get(className)
    }
}