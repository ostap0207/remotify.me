package converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * .
 * User: Ostap
 * Date: 2/18/14
 * Time: 12:05 PM
 */
public class JsonObjectMapper extends ObjectMapper {

    public JsonObjectMapper(){
        registerModule(new Hibernate4Module());
    }
}
