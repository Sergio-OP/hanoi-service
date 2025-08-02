package com.sosorio.hanoi_service

import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.BufferedWriter
import java.io.OutputStreamWriter

@RestController
@RequestMapping("/api/hanoi")
class HanoiController {

    @GetMapping("/{n}", produces = [TEXT_PLAIN_VALUE])
    fun streamMoves(
        @PathVariable n: Int,
    ): ResponseEntity<StreamingResponseBody> {
        val stream = StreamingResponseBody { outputStream ->
            BufferedWriter(OutputStreamWriter(outputStream)).use { writer ->
                hanoi(n, 1, 3, writer)
           }
        }

        return ResponseEntity.ok(stream)
    }

    private fun hanoi(n: Int, start: Int, end: Int, writer: BufferedWriter) {
        if (n == 1) {
            writeMove(start, end, writer)
        } else {
            val otherRod = 6 - (start + end)
            hanoi(n - 1, start, otherRod, writer)
            writeMove(start, end, writer)
            hanoi(n - 1, otherRod, end, writer)
        }
    }

    private fun writeMove(from: Int, to: Int, writer: BufferedWriter) {
        val json = """{"from": $from, "to": $to}"""
        writer.write(json)
        writer.newLine()
        writer.flush()
    }
}