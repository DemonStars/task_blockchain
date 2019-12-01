package com.lfkekpoint.blockchain.task.presentation.helper

import java.text.SimpleDateFormat
import java.util.*

object FormatHelper {

    fun getTimeFromDateString(date: String): String {

        when (date.isNotEmpty()) {

            true -> {

                val cal = Calendar.getInstance()
                val ruLocale = Locale("ru", "RU")
                cal.clear()

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", ruLocale)//2012-04-23T00:00:00.00
                cal.time = sdf.parse(date)

                val newDateFormat = SimpleDateFormat("HH:mm", ruLocale)
                val shortDate = newDateFormat.format(cal.time)

                return shortDate
            }

            else -> return ""
        }
    }

    fun getReqDateFromDateString(date: String): String {

        when (date.isNotEmpty()) {

            true -> {

                val cal = Calendar.getInstance()
                val ruLocale = Locale("ru", "RU")
                cal.clear()

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", ruLocale)//2012-04-23T00:00:00.00
                cal.time = sdf.parse(date)

                val newDateFormat = SimpleDateFormat("yyyy-MM-dd", ruLocale) //2019-05-14
                val shortDate = newDateFormat.format(cal.time)

                return shortDate
            }

            else -> return ""
        }
    }

    fun getShortDateFromDateString(date: String): String {

        when (date.isNotEmpty()) {

            true -> {

                val cal = Calendar.getInstance()
                val ruLocale = Locale("ru", "RU")
                cal.clear()

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", ruLocale)//2012-04-23T00:00:00.00
                cal.time = sdf.parse(date)

                val newDateFormat = SimpleDateFormat("dd.MM.yyyy", ruLocale)
                val shortDate = newDateFormat.format(cal.time)

                return shortDate
            }

            else -> return ""
        }
    }

    fun getCalendarInstanceFromDateString(date: String) = if (date.isNotEmpty()) {

        val cal = Calendar.getInstance()
        val ruLocale = Locale("ru", "RU")
        cal.clear()

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", ruLocale)//2012-04-23T00:00:00.00
        cal.time = sdf.parse(date)

        cal
    } else {
        null
    }

    fun getCalendarInstanceFromShortFormat(date: String): Calendar {

        val cal = Calendar.getInstance()
        val ruLocale = Locale("ru", "RU")
        cal.clear()

        val sdf = SimpleDateFormat("yyyy-MM-dd", ruLocale) //1989-05-25
        cal.time = sdf.parse(date)

        return cal
    }

    fun getShortTimeFromCalendar(currentCalendar: Calendar?) = currentCalendar?.let {

        val ruLocale = Locale("ru", "RU")
        val newDateFormat = SimpleDateFormat("dd MMMM", ruLocale)
        val shortDate = newDateFormat.format(it.time)

        shortDate
    } ?: ""


    fun getShortTimeFromUnix(unixTime: Long?) = unixTime?.let {

        val cal = Calendar.getInstance()
        cal.timeInMillis = unixTime

        val ruLocale = Locale("ru", "RU")
        val newDateFormat = SimpleDateFormat("dd MMMM", ruLocale)
        val shortDate = newDateFormat.format(cal.time)

        shortDate
    } ?: ""


    fun calculateAgeWithPostfix(birthday: String): String {

        if (birthday.isNotBlank()) {
            val now = Calendar.getInstance()
            val dob = getCalendarInstanceFromShortFormat(birthday)
            if (dob.after(now)) {
                throw  IllegalArgumentException("Can't be born in the future");
            }
            val year1 = now.get(Calendar.YEAR);
            val year2 = dob.get(Calendar.YEAR);
            var age = year1 - year2;
            val month1 = now.get(Calendar.MONTH);
            val month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                val day1 = now.get(Calendar.DAY_OF_MONTH);
                val day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }

            val sb = StringBuilder()
            sb.append(age)

            when (sb.toString().get(sb.lastIndex).toString()) {
                "0", "9", "8", "7", "6", "5" -> sb.append(" лет")
                "1" -> sb.append(" год")
                "2", "3", "4" -> sb.append(" года")
            }

            return sb.toString()

        } else return ""
    }
}