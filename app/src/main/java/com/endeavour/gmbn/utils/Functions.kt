package com.endeavour.gmbn.utils

import org.joda.time.format.ISOPeriodFormat
import org.joda.time.format.PeriodFormatterBuilder

fun String.toDuration(): String {
    val period =  ISOPeriodFormat.standard().parsePeriod(this)
    return  PeriodFormatterBuilder()
        .appendHours()
        .appendSeparator(":")
        .appendMinutes()
        .appendSeparator(":")
        .printZeroAlways()
        .minimumPrintedDigits(2)
        .appendSeconds().toFormatter()
        .print(period)

}