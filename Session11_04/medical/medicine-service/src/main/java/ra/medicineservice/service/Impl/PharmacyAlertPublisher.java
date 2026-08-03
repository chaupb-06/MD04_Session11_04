package ra.medicineservice.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ra.medicineservice.model.dto.request.PharmacyAlertMessage;

@Service
@RequiredArgsConstructor
public class PharmacyAlertPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public String getPharmacyAlertMessage(PharmacyAlertMessage pharmacyAlertMessage) {
            redisTemplate.convertAndSend(pharmacyAlertMessage.getTopicListen(), pharmacyAlertMessage);
            return "Đã gửi thông báo đến kênh: " + pharmacyAlertMessage.getTopicListen();
    }
}
