package io.mosip.registration.processor.adjudication.config;

import io.mosip.registration.processor.adjudication.util.ManualVerificationRequestValidator;
import io.mosip.registration.processor.adjudication.util.ManualVerificationUpdateUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.mosip.registration.processor.adjudication.exception.handler.ManualVerificationExceptionHandler;
import io.mosip.registration.processor.adjudication.service.ManualAdjudicationService;
import io.mosip.registration.processor.adjudication.service.impl.ManualAdjudicationServiceImpl;

@Configuration
public class ManualAdjudicationConfigBean {
	
	@Bean
    ManualAdjudicationService getManualVerificationService() {
		return new ManualAdjudicationServiceImpl();
	}

	@Bean
	ManualVerificationRequestValidator getManualVerificationRequestValidator() {
		return new ManualVerificationRequestValidator();
	}
	
	@Bean
	ManualVerificationExceptionHandler getManualVerificationExceptionHandler() {
		return new ManualVerificationExceptionHandler();
	}

	@Bean
	ManualVerificationUpdateUtility manualVerificationUpdateUtility() {
		return new ManualVerificationUpdateUtility();
	}
}