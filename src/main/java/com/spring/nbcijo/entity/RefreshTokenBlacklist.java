package com.spring.nbcijo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token_blacklist")
public class RefreshTokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String refreshToken;

    @Column
    private Date expirationDate;

    @Builder
    public RefreshTokenBlacklist(String refreshToken, Date expirationDate) {
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
    }
}
