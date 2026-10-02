package ru.laba.film.server.domain;

import java.io.Serial;
import java.io.Serializable;
import java.security.MessageDigest;

/** Учётная запись: пароль представлен солью и PBKDF2-хэшем. */
public final class UserAccount implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private final String username;
    private final byte[] salt;
    private final byte[] passwordHash;
    public UserAccount(String username, byte[] salt, byte[] passwordHash) {
        this.username = username;
        this.salt = salt.clone();
        this.passwordHash = passwordHash.clone();
    }
    public String username() { return username; }
    public byte[] salt() { return salt.clone(); }
    public boolean hasPasswordHash(byte[] candidate) { return MessageDigest.isEqual(passwordHash, candidate); }
}
