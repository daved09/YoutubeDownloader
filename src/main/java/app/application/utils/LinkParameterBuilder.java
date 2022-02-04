package app.application.utils;

import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class LinkParameterBuilder {

	public Map<String, String> buildParameterMap(URL url){
		Map<String, String> parameterMap = new HashMap<>();
		String query = url.getQuery();
		String[] params = query.split("&");
		for (String param : params){
			String[] split = param.split("=");
			parameterMap.put(split[0], split[1]);
		}
		return parameterMap;
	}

}
