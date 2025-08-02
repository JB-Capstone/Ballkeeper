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
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ItemStatus status;

    @Column(name = "image_url", length = 100)
    private String imageUrl;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItem> reservationItems = new ArrayList<>();

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
