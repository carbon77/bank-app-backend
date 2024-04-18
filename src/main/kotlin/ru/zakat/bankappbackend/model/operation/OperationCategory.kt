package ru.zakat.bankappbackend.model.operation

import jakarta.persistence.*

@Entity
@Table(name = "operation_categories")
data class OperationCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Long? = null,

    @Column(unique = true)
    var name: String? = null,
)
