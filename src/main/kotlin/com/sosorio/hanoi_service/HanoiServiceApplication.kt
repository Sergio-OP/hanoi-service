package com.sosorio.hanoi_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HanoiServiceApplication

fun main(args: Array<String>) {
	runApplication<HanoiServiceApplication>(*args)
}
