package ru.gubernik.rails.railbot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Сотрудник
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "EMPLOYEE")
public class Employee extends BaseEntity {

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "CHAT_ID")
    private String chatId;

    @Column(name = "IS_ADMIN")
    private boolean isAdmin;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Work> workList;

    public Employee() {
    }

    public Employee(String login, String chatId) {
        this.login = login;
        this.chatId = chatId;
    }
}
