package ru.zakat.bankappbackend.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
open class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    var id: Long? = null

    var name: String? = null
    var balance: Double? = null
    var closed: Boolean? = null

    @Enumerated(EnumType.STRING)
    var accountType: AccountType? = null

    @Temporal(TemporalType.DATE)
    var createdAt: Date? = null

    @Embedded
    var accountDetails: AccountDetails? = null
    var userId: String? = null
    var userFirstName: String? = null
    var userLastName: String? = null

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    var cards: MutableList<Card> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Account

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
