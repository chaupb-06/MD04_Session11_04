package ra.medicineservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.medicineservice.model.dto.request.MedicineRequestDTO;
import ra.medicineservice.model.dto.request.MedicineUpdateRequestDTO;
import ra.medicineservice.model.dto.response.ApiResponse;
import ra.medicineservice.service.MedicineService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMedicine(@Valid @RequestBody MedicineRequestDTO medicineRequestDTO) {
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                "Tạo thuốc mới thành công!",
                medicineService.createMedicine(medicineRequestDTO),
                null,
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllMedicines() {
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                "Lấy danh sách thuốc thành công!",
                medicineService.getAllMedicines(),
                null,
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getMedicine(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                "Lấy thông tin thuốc thành công!",
                medicineService.getMedicineById(id),
                null,
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineUpdateRequestDTO medicineUpdateRequestDTO) {
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                "Cập nhật thông tin thuốc thành công!",
                medicineService.updateMedicine(id, medicineUpdateRequestDTO),
                null,
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
    @PutMapping("/sell/{id}")
    public void sellMedicine(@PathVariable Long id) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String rsBuy = medicineService.sellMedicine(id);
                System.out.println("Người dùng 1 : " + rsBuy);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String rsBuy = medicineService.sellMedicine(id);
                System.out.println("Người dùng 2 : " + rsBuy);
            }
        });
        thread2.start();
        thread1.start();
    }
}
