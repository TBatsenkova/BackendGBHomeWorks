package lesson4.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lesson4.dto.api.Aisle;
import lombok.Data;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "aisles",
        "cost",
        "startDate",
        "endDate",
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetShoppingList {
    private ArrayList<Aisle> aisles;
    private Double cost;
    private Integer startDate;
    private Integer endDate;
}



