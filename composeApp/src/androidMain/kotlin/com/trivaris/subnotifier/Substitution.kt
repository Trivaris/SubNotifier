package com.trivaris.subnotifier

data class Substitution(
    val courses: List<Course>,
    val period: String,
    val room: String,
    val teacher: String,
    val subject: String,
    val information: String,
    val cancelled: Boolean
) {
    fun getFormattedTeacher() =
        teacher.substringAfter('(').substringBefore(')')
}

enum class Course {
    X,
    BIO_UWT,
    MEDIEN,
    INTA,
    BILI;
}

fun getCurrentTeachers(subs: List<Substitution>): List<String> {
    val teachers = mutableListOf<String>()
    for (sub in subs)
        teachers.add(sub.getFormattedTeacher())
    return teachers
}