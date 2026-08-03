package ra.medicineservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PharmacyAlertMessage {
    private String topicListen;
    private String type;
    private String message;
}
