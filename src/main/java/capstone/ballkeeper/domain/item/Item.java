package capstone.ballkeeper.domain.item;

import capstone.ballkeeper.domain.ReservationItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<ReservationItem> reservationItems = new ArrayList<>();

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ItemStatus status;

    @Column(name = "IMAGE_URL", length = 100)
    private String imageUrl;


    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    // 생성 메서드
    public static Item registerItem(String name, String description, ItemStatus status) {
        Item item = new Item();
        item.name = name;
        item.description = description;
        item.status = status;
        item.createdAt = LocalDateTime.now();

        return item;
    }

    /*
     * 연관관계 메서드
     * */
    // 상태 변경
    public void changeStatus(ItemStatus status) {
        this.status = status;
    }
}
