package ra.medicineservice.service;

import ra.medicineservice.model.dto.request.MedicineRequestDTO;
import ra.medicineservice.model.dto.request.MedicineUpdateRequestDTO;
import ra.medicineservice.model.dto.response.MedicineResponseDTO;

import java.util.List;

public interface MedicineService {
    MedicineResponseDTO createMedicine(MedicineRequestDTO medicineRequestDTO);
    List<MedicineResponseDTO> getAllMedicines();
    MedicineResponseDTO getMedicineById(Long id);
    MedicineResponseDTO updateMedicine(Long id, MedicineUpdateRequestDTO medicineUpdateRequestDTO);
    String sellMedicine(Long id);
}
