package ru.zakat.bankappbackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.Date

@Entity
@Table(name = "cards")
data class Card(
    var number: String? = null,
    val expirationDate: Date? = null,
    val svv: Int? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    var account: Account? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    var id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Card

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
