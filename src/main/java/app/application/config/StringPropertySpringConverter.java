package app.application.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class StringPropertySpringConverter implements Converter<String, StringProperty> {

	@Override
	public StringProperty convert(String source) {
		return new SimpleStringProperty(source);
	}
}
