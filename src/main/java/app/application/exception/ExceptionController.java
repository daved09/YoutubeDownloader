package app.application.exception;

import app.application.utils.DialogManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ExceptionController {

	private DialogManager dialogManager;

	public ExceptionController(DialogManager dialogManager) {
		this.dialogManager = dialogManager;
	}

	@PostConstruct
	public void error(){
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> dialogManager.openExceptionDialog(throwable));
	}

}
