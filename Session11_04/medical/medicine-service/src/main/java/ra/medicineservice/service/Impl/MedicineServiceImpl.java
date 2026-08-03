package ra.medicineservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ra.medicineservice.model.dto.request.MedicineRequestDTO;
import ra.medicineservice.model.dto.request.MedicineUpdateRequestDTO;
import ra.medicineservice.model.dto.response.MedicineResponseDTO;
import ra.medicineservice.model.entity.Medicine;
import ra.medicineservice.repository.MedicineRepository;
import ra.medicineservice.service.MedicineService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final RedissonClient redissonClient;

    @Override
    @CachePut(value = "medicines", key = "#result.id")
    @CacheEvict(value = "medicine_list", allEntries = true)
    public MedicineResponseDTO createMedicine(MedicineRequestDTO medicineRequestDTO) {
        Medicine medicine = Medicine.builder()
                .medicineName(medicineRequestDTO.getMedicineName())
                .medicineDescription(medicineRequestDTO.getMedicineDescription())
                .medicineCategory(medicineRequestDTO.getMedicineCategory())
                .medicineType(medicineRequestDTO.getMedicineType())
                .medicinePrice(medicineRequestDTO.getMedicinePrice())
                .quantity(medicineRequestDTO.getQuantity())
                .build();
        medicineRepository.save(medicine);
        return MedicineResponseDTO.builder()
                .id(medicine.getId())
                .medicineName(medicine.getMedicineName())
                .medicineDescription(medicine.getMedicineDescription())
                .medicineCategory(medicine.getMedicineCategory())
                .medicineType(medicine.getMedicineType())
                .medicinePrice(medicine.getMedicinePrice())
                .quantity(medicine.getQuantity())
                .build();
    }

    @Override
    @Cacheable(value = "medicine_list", key = "'all'")
    public List<MedicineResponseDTO> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        return medicines.stream()
                .map(medicine -> MedicineResponseDTO.builder()
                        .id(medicine.getId())
                        .medicineName(medicine.getMedicineName())
                        .medicineDescription(medicine.getMedicineDescription())
                        .medicineCategory(medicine.getMedicineCategory())
                        .medicineType(medicine.getMedicineType())
                        .medicinePrice(medicine.getMedicinePrice())
                        .quantity(medicine.getQuantity())
                        .build()
                ).toList();
    }

    @Override
    @Cacheable(value = "medicines", key = "#id")
    public MedicineResponseDTO getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại thuốc có id " + id));
        return MedicineResponseDTO.builder()
                .id(medicine.getId())
                .medicineName(medicine.getMedicineName())
                .medicineDescription(medicine.getMedicineDescription())
                .medicineCategory(medicine.getMedicineCategory())
                .medicineType(medicine.getMedicineType())
                .medicinePrice(medicine.getMedicinePrice())
                .quantity(medicine.getQuantity())
                .build();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "medicines", key = "#id"),
            @CacheEvict(value = "medicine_list", allEntries = true)
    })
    public MedicineResponseDTO updateMedicine(Long id, MedicineUpdateRequestDTO medicineUpdateRequestDTO) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại thuốc có id " + id));
        if (medicineUpdateRequestDTO.getMedicineName() != null) {
            medicine.setMedicineName(medicineUpdateRequestDTO.getMedicineName());
        }
        if (medicineUpdateRequestDTO.getMedicineDescription() != null) {
            medicine.setMedicineDescription(medicineUpdateRequestDTO.getMedicineDescription());
        }
        if (medicineUpdateRequestDTO.getMedicineCategory() != null) {
            medicine.setMedicineCategory(medicineUpdateRequestDTO.getMedicineCategory());
        }
        if (medicineUpdateRequestDTO.getMedicineType() != null) {
            medicine.setMedicineType(medicineUpdateRequestDTO.getMedicineType());
        }
        if (medicineUpdateRequestDTO.getMedicinePrice() != null) {
            medicine.setMedicinePrice(medicineUpdateRequestDTO.getMedicinePrice());
        }
        if (medicineUpdateRequestDTO.getQuantity() != null) {
            medicine.setQuantity(medicineUpdateRequestDTO.getQuantity());
        }
        medicineRepository.save(medicine);
        return MedicineResponseDTO.builder()
                .id(medicine.getId())
                .medicineName(medicine.getMedicineName())
                .medicineDescription(medicine.getMedicineDescription())
                .medicineCategory(medicine.getMedicineCategory())
                .medicineType(medicine.getMedicineType())
                .medicinePrice(medicine.getMedicinePrice())
                .quantity(medicine.getQuantity())
                .build();
    }

    @Override
    public String sellMedicine(Long id) {
        String lockKey = "medicineservice:lock:medicine:" + id;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            Boolean isAcquired = lock.tryLock(3, 5, TimeUnit.SECONDS);
            if (!isAcquired) {
                return "Hệ thống bận, không thể thực hiện giao dịch";
            }
            Medicine medicine = medicineRepository.findById(id).orElse(null);
            if (medicine != null) {
                return "Thuốc không tồn tại!";
            }
            if (medicine.getQuantity() <= 0) {
                return "Sản phẩm đã hết hàng!";
            }
            Thread.sleep(500);
            medicine.setQuantity(medicine.getQuantity() - 1);
            medicineRepository.save(medicine);
            return "Thanh toán thành công thuốc: " + medicine.getQuantity();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Lỗi xử lý luồng!";
        }
        finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
