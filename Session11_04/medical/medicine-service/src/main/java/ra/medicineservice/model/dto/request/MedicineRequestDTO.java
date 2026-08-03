package ra.medicineservice.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.medicineservice.common.MedicineType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicineRequestDTO {
    @NotBlank(message = "Không được để trống tên thuốc!")
    private String medicineName;
    private String medicineDescription;
    private String medicineCategory;
    private MedicineType medicineType;
    @NotNull(message = "Không được để trống giá thuốc!")
    @Min(value = 0, message = "Giá thuốc không được là số âm!")
    private Double medicinePrice;
    @NotNull(message = "Không được để trống số lượng!")
    @Min(value = 0, message = "Số lượng không được âm!")
    private Integer quantity;
}
