package ru.zakat.bankappbackend.model.operation

import jakarta.persistence.*
import ru.zakat.bankappbackend.model.Account
import java.util.Date

@Entity
@Table(name = "operations")
data class Operation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    var type: OperationType? = null,

    var amount: Double? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date? = null,

    @ManyToOne(cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    var category: OperationCategory? = null,
)