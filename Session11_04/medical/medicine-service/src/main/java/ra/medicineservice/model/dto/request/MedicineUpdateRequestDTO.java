package ra.medicineservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.medicineservice.common.MedicineType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicineUpdateRequestDTO {
    private String medicineName;
    private String medicineDescription;
    private String medicineCategory;
    private MedicineType medicineType;
    private Double medicinePrice;
    private Integer quantity;
}
