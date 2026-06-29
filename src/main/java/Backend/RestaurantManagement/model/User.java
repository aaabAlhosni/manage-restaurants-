package Backend.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "APP_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@ToString(exclude = "menus")
public class User {

    @Id
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "F_NAME", nullable = false)
    private String fName;

    @Column(name = "L_NAME", nullable = false)
    private String lName;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Menu> menus;
}
