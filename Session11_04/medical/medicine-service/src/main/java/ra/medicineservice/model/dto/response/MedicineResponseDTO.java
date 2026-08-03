package ra.medicineservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.medicineservice.common.MedicineType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicineResponseDTO {
    private Long id;
    private String medicineName;
    private String medicineDescription;
    private String medicineCategory;
    private MedicineType medicineType;
    private Double medicinePrice;
    private Integer quantity;
}
