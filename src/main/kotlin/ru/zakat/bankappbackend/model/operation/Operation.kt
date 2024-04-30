package ru.zakat.bankappbackend.model.operation

import com.fasterxml.jackson.annotation.JsonIdentityReference
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.zakat.bankappbackend.model.Account
import java.util.*

@Entity
@Table(name = "operations")
data class Operation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    var type: OperationType? = null,

    @Enumerated(EnumType.STRING)
    var status: OperationStatus? = null,

    var amount: Double? = null,

    @Type(JsonType::class)
    @Column(columnDefinition = "jsonb")
    var extraFields: List<OperationField>? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date? = null,

    @ManyToOne(cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    var account: Account? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    var category: OperationCategory? = null,
)
