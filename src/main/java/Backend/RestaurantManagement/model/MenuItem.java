package Backend.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "MENU_ITEMS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "menuItemId")
@ToString(exclude = "menu")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO (Oracle migration): Change to GenerationType.SEQUENCE with @SequenceGenerator(sequenceName = "MENU_ITEMS_SEQ")
    @Column(name = "MENU_ITEM_ID")
    private Long menuItemId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AVAILABLE", nullable = false)
    private boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", nullable = false)
    private Menu menu;
}
