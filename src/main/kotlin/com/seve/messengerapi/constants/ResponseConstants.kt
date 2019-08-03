package com.seve.messengerapi.constants

enum class ResponseConstants(val value: String) {

    SUCCESS("success"),
    ERROR("error"),
    USERNAME_UNAVAILABLE("US_001"),
    INVALID_USER_ID("US_002"),
    EMPTY_STATUS("ST_003"),
    MESSAGE_EMPTY("MS_004"),
    MESSAGE_RECIPIENT_INVALID("MS_005"),
    ACCOUNT_DEACTIVATED("403"),
    BUSINESS("409")
}