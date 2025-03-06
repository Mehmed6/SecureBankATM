package com.doganmehmet.app.entity;

import com.doganmehmet.app.enums.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transactionId;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private Action transactionType;

    private BigDecimal amount;

    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    public Transaction(Action transactionType, BigDecimal amount, String description, LocalDateTime transactionDate, String transactionStatus, User fromUser, User toUser)
    {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
