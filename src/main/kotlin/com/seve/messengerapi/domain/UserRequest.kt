package com.seve.messengerapi.domain

import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.NumberFormat
import javax.validation.constraints.Digits

class UserRequest {

    var userId: Long? = 0

    @Length(min = 2, max = 100)
    var username: String? = ""

    @Digits(integer = 100, fraction = 0, message = "Must be a number")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Length(min = 10, max = 10)
    var phoneNumber: String? = ""

    @Length(max = 60)
    var password: String? = ""
}