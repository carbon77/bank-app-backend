package ru.zakat.bankappbackend.model

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
@Table(name = "passports", uniqueConstraints = [
    UniqueConstraint(columnNames = ["number", "series"])
])
data class Passport(
    var number: String? = null,
    var series: String? = null,
    var issueDate: Date? = null,
    var issuePlace: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var patronimic: String? = null,
    var departmentCode: String? = null,
    var birthday: Date? = null,

    @Id
    @Column(name = "user_id")
    var id: Long? = null,

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    var user: User? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Passport

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
