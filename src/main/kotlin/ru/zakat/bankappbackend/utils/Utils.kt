package ru.zakat.bankappbackend.utils

fun generateRandomString(length: Int): String {
    val charPool: List<Char> = ('0'..'9').toList()

    return (1..length)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}