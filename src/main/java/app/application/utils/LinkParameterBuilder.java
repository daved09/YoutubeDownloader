package app.application.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkParameterBuilder {

	public Map<String, String> buildParameterMap(String query){
		Map<String, String> parameterMap = new HashMap<>();
		if(query != null){
			String[] params = query.split("&");
			for (String param : params){
				String[] split = param.split("=");
				parameterMap.put(split[0], split[1]);
			}
		}
		return parameterMap;
	}

}
