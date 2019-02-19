package software.engineering.task.controller;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.engineering.task.model.PrettyJsonMaker;
import software.engineering.task.model.XmlToJsonConverter;

/**
 * This class waits and processes all incoming requests
 */
@RestController
public class MainController {

    /**
     * Method processes incoming XML and convert it to JSON.
     *
     * @param xml - XML to convert
     * @return - converted JSON or "Not valid" message if XML is not valid
     */
    @PostMapping(path = "/", consumes = "application/xml")
    public ResponseEntity<String> xmlToJson(@RequestBody String xml){
        try {
            return ResponseEntity.ok(PrettyJsonMaker.convert(XmlToJsonConverter.convert(xml)));
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid XML\n");
        }
    }
}
