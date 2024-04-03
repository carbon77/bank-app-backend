package ru.zakat.bankappbackend.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "accounts")
data class Account(
    var balance: Int? = null,

    @Enumerated(EnumType.STRING)
    var accountType: AccountType? = null,

    @Embedded
    var accountDetails: AccountDetails? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,


    @OneToMany(mappedBy = "account", orphanRemoval = true)
    var cards: MutableList<Card> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id", nullable = false)
    var id: Long? = null,
) {
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
