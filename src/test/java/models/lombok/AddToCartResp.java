package models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddToCartResp {
    private String success;
    private String message;
    private String updatetopcartsectionhtm;
    private String updateflyoutcartsectionhtml;
}
