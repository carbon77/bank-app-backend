package ru.zakat.bankappbackend.model.payment

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import ru.zakat.bankappbackend.model.AccountDetails
import ru.zakat.bankappbackend.model.operation.OperationCategory

@Entity
@Table(name = "payment_infos")
data class PaymentInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.MERGE], optional = false, orphanRemoval = true)
    @JoinColumn(name = "category_id", nullable = false)
    var category: OperationCategory? = null,

    @Type(JsonType::class)
    @Column(columnDefinition = "jsonb")
    var fields: List<PaymentField>? = null,

    @Embedded
    var accountDetails: AccountDetails? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PaymentInfo

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , category = $category )"
    }
}