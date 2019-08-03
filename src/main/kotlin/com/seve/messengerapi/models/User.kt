package com.seve.messengerapi.models

import com.seve.messengerapi.listeners.UserListener
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS")
@EntityListeners(UserListener::class)
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")                var id: Long = 0
    @Column(name = "USERNAME")          var username: String = ""
    @Column(name = "PHONE_NUMBER")      var phoneNumber: String = ""
    @Column(name = "PASSWORD")          var password: String = ""
    @Column(name = "STATUS")            var status: String = "available"
    @Column(name = "ACCOUNT_STATUS")    var accountStatus = "activated"
    @Column(name = "CREATED_AT")        var createdAt: Date = Date.from(Instant.now())

    @OneToMany(mappedBy = "sender", targetEntity = Message::class)
    var sentMessages: Collection<Message>? = null

    @OneToMany(mappedBy = "recipient", targetEntity = Message::class)
    var receivedMessages: Collection<Message>? = null

}