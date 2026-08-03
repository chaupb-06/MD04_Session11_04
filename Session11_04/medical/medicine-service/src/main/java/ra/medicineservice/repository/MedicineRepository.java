package ra.medicineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.medicineservice.model.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
