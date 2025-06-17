package com.trivaris.subnotifier

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.get
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.Locale

object Web {
    private const val GYFA_URL = "https://m.gymnasium-farmsen.de"
    private var csrftoken = ""

    var username = System.getenv("GYFA_USER")
    var password = System.getenv("GYFA_PASSWORD")

    private val client = HttpClient(CIO) {
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
    }

    suspend fun getSubstitutions(): MutableList<Substitution> {
        val resultList = mutableListOf<Substitution>()
        val response = connect()

        if (!response.status.isSuccess())
            return resultList
        println("[OUT] Successfully got data: " + response.bodyAsText()
            .lowercase(Locale.getDefault())
            .contains(username.substringBefore(".").lowercase()))

        val document = Jsoup.parse(response.bodyAsText())
        val table = document.select("table[style='width: 100%;']")
        val tbody = table.select("tbody")

        tbody.select("tr").forEach { tr ->
            val tdElements = tr.select("td")

            var cancelled = false
            val values:MutableList<String> = tdElements.map { td ->
                cancelled = td.toString().contains("line-through")
                Regex(">([^<]+)<").find(td.toString())?.groups?.get(1)?.value?.trim() ?: ""
            }.toMutableList()
            if (values.size < 2)
                return@forEach

            val coursesStrings = values[0]
                .replace("-", "_")
                .split(", ")

            val courses = mutableListOf<Course>()

            coursesStrings.forEach { courses.add(Course.valueOf(it.substringAfter(".").uppercase())) }

            val substitution = Substitution(
                courses,
                values[1],
                values[2],
                values[3],
                values[4],
                values[5],
                cancelled
            )

            println("[OUT] Adding: $values")
            resultList.add(substitution)
        }
        return resultList
    }

    private suspend fun login() {
        getToken()

        val formData = Parameters.build {
            append("csrfmiddlewaretoken", csrftoken)
            append("username", username)
            append("password", password)
            append("submit", "")
        }

        client.submitForm(
            url = "${GYFA_URL}/login/",
            formParameters = formData
        ) {
            header(HttpHeaders.Referrer, url)
        }
        println("[OUT] Submitted Form")
    }

    private suspend fun getToken() {
        val response = client.get("$GYFA_URL/login")
        val document: Document = Jsoup.parse(response.bodyAsText())
        csrftoken = document.select("input[name=csrfmiddlewaretoken]").first()?.attr("value") ?: "None"
        println("[OUT] Obtained Token: $csrftoken")
    }

    private suspend fun connect(): HttpResponse {
        login()
        return client.get("$GYFA_URL/substitutions")
    }

}