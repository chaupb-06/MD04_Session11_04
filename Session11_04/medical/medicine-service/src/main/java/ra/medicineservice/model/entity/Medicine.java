package ra.medicineservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.medicineservice.common.MedicineType;

@Entity
@Table(name = "medicines")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id;
    @Column(name = "medicine_name", nullable = false, length = 200)
    private String medicineName;
    @Column(name = "medicine_description", nullable = false, columnDefinition = "text")
    @Builder.Default
    private String medicineDescription = "Không có mô tả!";
    @Column(name = "medicine_category", nullable = false, length = 200)
    @Builder.Default
    private String medicineCategory = "Chưa có phân loại!";
    @Column(name = "medicine_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MedicineType medicineType = MedicineType.BOX;
    @Column(name = "medicine_price", nullable = false, columnDefinition = "numeric(10,2) default 0.00")
    @Builder.Default
    private Double medicinePrice = 0.00;
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;
}
