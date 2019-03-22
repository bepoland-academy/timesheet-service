package pl.betse.beontime.users.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "STATUS")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "STATUS_NAME", nullable = false)
    private String name;
}
