package app.application.utils;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class ExceptionExtractor {

	public Throwable getTargetOfException(Exception exception){
		exception = (Exception) exception.getCause();
		if(!isTargetException(exception)){
			return exception.getCause();
		}
		return ((InvocationTargetException) exception).getTargetException();
	}

	private boolean isTargetException(Exception exception){
		return exception instanceof InvocationTargetException;
	}

}
