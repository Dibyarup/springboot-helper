package edu.dibyarup.springboot.lib.microservice.template;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MicroServiceControllerAdvice {
	@ExceptionHandler(value = { WebApplicationException.class })
	protected ResponseEntity<ErrorBean> handleException(WebApplicationException e, HttpServletRequest request) {

		final int errorCode = e.getResponse().getStatus();
		final String errorMessage = e.getMessage();

		log.error("Request Failed is -> '{}', Error code -> {}, Message -> '{}'", requestUrl(request), errorCode,
				errorMessage);

		return new ResponseEntity<>(ErrorBean.builder().success(false).errorCode(String.valueOf(errorCode))
				.errorMessage(errorMessage).build(), HttpStatus.valueOf(errorCode));
	}

	private String requestUrl(final HttpServletRequest request) {
		return request.getQueryString() == null ? request.getRequestURL().toString()
				: request.getRequestURL() + "?" + request.getQueryString();
	}
}
