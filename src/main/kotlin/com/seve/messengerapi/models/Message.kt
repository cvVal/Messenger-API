package com.seve.messengerapi.models

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "MESSAGE")
class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")            var id: Long = 0
    @Column(name = "MESSAGE_BODY")  var body: String = ""
    @Column(name = "CREATED_AT")    var createdAt: Date = Date.from(Instant.now())

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    var sender: User? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "RECIPIENT_ID", referencedColumnName = "ID")
    var recipient: User? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "CONVERSATION_ID", referencedColumnName = "ID")
    var conversation: Conversation? = null
}