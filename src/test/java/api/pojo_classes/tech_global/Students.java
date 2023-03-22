package api.pojo_classes.tech_global;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class Students {


    /**
     * {
     *     "firstName": "Sylvester",
     *     "lastName": "Rosenbaum",
     *     "email": "Callie41@gmail.com",
     *     "dob": "1997-01-01"
     * }
     */


    private String firstName;
    private String lastName;
    private String email;
    private String dob;

}
