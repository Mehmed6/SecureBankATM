package com.doganmehmet.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private long sessionId;
    private String username;
    @Column(name = "login_time")
    private LocalDateTime loginTime;
    @Column(name = "logout_time")
    private LocalDateTime logoutTime;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
