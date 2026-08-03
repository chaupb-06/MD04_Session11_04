package ra.medicineservice.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.medicineservice.model.dto.request.PharmacyAlertMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyAlertSubscriber {
    private final ObjectMapper objectMapper;
    public void handleMessage(String message) {
        try {
            PharmacyAlertMessage alertMessage = objectMapper.readValue(message, PharmacyAlertMessage.class);
            log.info("=== THÔNG BÁO DASHBOARD QUẢN LÝ ===");
            log.info("Type: {}", alertMessage.getType());
            log.info("Message: {}", alertMessage.getMessage());
            log.info("===================================");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }
}
