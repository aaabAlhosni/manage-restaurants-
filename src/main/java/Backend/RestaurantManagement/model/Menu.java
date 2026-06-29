package Backend.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "MENU")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "menuId")
@ToString(exclude = {"user", "items"})
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO (Oracle migration): Change to GenerationType.SEQUENCE with @SequenceGenerator(sequenceName = "MENU_SEQ")
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL", nullable = false)
    private User user;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> items;
}
