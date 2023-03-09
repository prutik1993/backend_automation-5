package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

/**
 * with the help of data witch is coming from
 *  lombok we can eliminate getters and setters
 **/
@Data
/**
 * With builder, we are able to assign value to the attributes
 */
@Builder

public class UpdateGoRestUserWithLombok {

    /**
     * {
     *     "name": "",
     *     "gender": "",
     *     "email": "Ophelia.Mraz@hotmail.com",
     *     "status": ""
     * }
     */

    private String gender;
    private String email;
    private String status;


}
