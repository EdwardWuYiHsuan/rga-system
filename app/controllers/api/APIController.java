package controllers.api;

import java.util.Map;

import controllers.exception.ApiException;
import controllers.response.DefaultResponse;
import controllers.response.Result;
import play.mvc.Controller;
import rga.APICode;

public class APIController extends Controller {

	protected static String readApiParameter(String key, boolean required) throws ApiException {
        Map<String, String[]> inputMap = params.all();
        String value = "";
        if (inputMap.get(key) != null) {
        	if (inputMap.get(key)[0] != null) {
        			value = inputMap.get(key)[0];
        	}
        }
        
        if (value.isEmpty() && required) {
            throw new ApiException(APICode.MissingParameter, String.format("Missing Parameter '%s'", key));
        }

        return value;
    }
	
	protected static void respondError(Throwable error) {
		error.printStackTrace();
		renderJSON(new DefaultResponse(Result.error, error));
	}
	
	
}
