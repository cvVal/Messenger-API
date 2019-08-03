package com.seve.messengerapi.models

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "CONVERSATION")
class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")            var id: Long = 0
    @Column(name = "CREATED_AT")    var createdAt: Date = Date.from(Instant.now())

    @ManyToOne(optional = false)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    var sender: User? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "RECIPIENT_ID", referencedColumnName = "ID")
    var recipient: User? = null

    @OneToMany(mappedBy = "conversation", targetEntity = Message::class)
    var messages: Collection<Message>? = null
}