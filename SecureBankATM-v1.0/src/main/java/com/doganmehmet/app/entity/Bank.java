package com.doganmehmet.app.entity;

import com.doganmehmet.app.utility.SwiftCodeGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private long bankId;

    @Column(name = "bank_name")
    private String bankName;

    private String swiftCode = SwiftCodeGenerator.generateSwiftCode();

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
