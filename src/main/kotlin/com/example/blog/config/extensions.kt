package com.example.blog.config

import java.util.*

fun <T : Any> Iterable<T>.firstOptional(): Optional<T> = Optional.ofNullable(firstOrNull())