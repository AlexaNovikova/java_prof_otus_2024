package ru.otus.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public record Address(@Id @Column("id") Long id, Long clientId, @Nonnull String street) {}
