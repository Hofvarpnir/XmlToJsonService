package software.engineering.task.controller;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.engineering.task.model.PrettyJsonMaker;
import software.engineering.task.model.XmlToJsonConverter;

/**
 * This class waits and processes all incoming requests
 */
@RestController
public class MainController {
    private static final String API_KEY = "HwprFNso";
    private static final String STATION = "26063";

    @Autowired
    private RestTemplate restTemplate;
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

    @GetMapping(value="/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {

        return "This is Home page";
    }

    @GetMapping(value="/weather", produces = MediaType.TEXT_HTML_VALUE)
    private String getWeather(@RequestParam("start") String startDate, @RequestParam("end") String endDate){
        String url = "https://api.meteostat.net/v1/history/daily?station={station}&start={start_date}&end={end_date}&key={key}";
        String html;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class, STATION, startDate, endDate, API_KEY);
        String responseBody = response.getBody();

        JSONObject jsonObject = new JSONObject(responseBody);
        jsonObject.remove("meta");

        //return jsonObject.toString();
        html = CDL.toString(new JSONArray(jsonObject.get("data").toString()));
        html = html + "1";
        html = html.replaceAll("\n","</td></tr>\n<tr>\n<td>");
        html = html.replace("date,precipitation,snowdepth,snowfall,sunshine,temperature_min,temperature,temperature_max,windspeed,pressure,peakgust,winddirection</td></tr>\n",
                "<table class=\"table table-bordered table-hover table-condensed\">\n" +
                        "<thead><tr><th title=\"Field #1\">date</th>\n" +
                        "<th title=\"Field #2\">precipitation</th>\n" +
                        "<th title=\"Field #3\">snowdepth</th>\n" +
                        "<th title=\"Field #4\">snowfall</th>\n" +
                        "<th title=\"Field #5\">sunshine</th>\n" +
                        "<th title=\"Field #6\">temperature_min</th>\n" +
                        "<th title=\"Field #7\">temperature</th>\n" +
                        "<th title=\"Field #8\">temperature_max</th>\n" +
                        "<th title=\"Field #9\">windspeed</th>\n" +
                        "<th title=\"Field #10\">pressure</th>\n" +
                        "<th title=\"Field #11\">peakgust</th>\n" +
                        "<th title=\"Field #12\">winddirection</th>\n" +
                        "</tr></thead>\n" +
                        "<tbody>");
        html = html.replace(",","</td>\n<td>");
        html = html.replace("<tr>\n<td>1", "</tbody></table>");
        return html;
    }
}
