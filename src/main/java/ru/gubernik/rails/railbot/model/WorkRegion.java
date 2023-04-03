package ru.gubernik.rails.railbot.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "WORK_REGION")
@EqualsAndHashCode(callSuper = true)
public class WorkRegion extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "WORK_ID")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private RegionState state;
}
