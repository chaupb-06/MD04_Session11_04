package ra.medicineservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.medicineservice.model.dto.request.PharmacyAlertMessage;
import ra.medicineservice.service.Impl.PharmacyAlertPublisher;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final PharmacyAlertPublisher pharmacyAlertPublisher;

    @PostMapping
    public String sendAlert(@RequestBody PharmacyAlertMessage pharmacyAlertMessage) {
        return pharmacyAlertPublisher.getPharmacyAlertMessage(pharmacyAlertMessage);
    }
}
