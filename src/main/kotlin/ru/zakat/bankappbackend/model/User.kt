package ru.zakat.bankappbackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    var id: Long? = null,

    @Column(unique = true)
    var email: String? = null,

    @Column(unique = true)
    var phoneNumber: String? = null,
    private var password: String? = null,

    @OneToOne(mappedBy = "user", optional = false, cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var passport: Passport? = null,

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    var accounts: MutableList<Account> = mutableListOf(),
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    @JsonIgnore
    override fun getPassword(): String? = password

    fun setPassword(encodedPassword: String) {
        password = encodedPassword
    }

    @JsonIgnore
    override fun getUsername(): String? = email

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true
    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true
    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true
    @JsonIgnore
    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = ${passport?.lastName} , lastName = ${passport?.firstName}, email = $email )"
    }
}