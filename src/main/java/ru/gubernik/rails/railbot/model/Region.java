package ru.gubernik.rails.railbot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "REGION")
public class Region extends BaseEntity {

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "AREA")
    private Integer area;
}
