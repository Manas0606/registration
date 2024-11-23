package io.mosip.registration.processor.stages.utils;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.registration.processor.core.constant.LoggerFileConstant;
import io.mosip.registration.processor.core.logger.RegProcessorLogger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.mosip.kernel.biometrics.commons.CbeffValidator;
import io.mosip.kernel.biometrics.constant.BiometricType;
import io.mosip.kernel.biometrics.constant.ProcessedLevelType;
import io.mosip.kernel.biometrics.constant.PurposeType;
import io.mosip.kernel.biometrics.constant.QualityType;
import io.mosip.kernel.biometrics.entities.BDBInfo;
import io.mosip.kernel.biometrics.entities.BIR;
import io.mosip.kernel.biometrics.entities.BIRInfo;
import io.mosip.kernel.biometrics.entities.BiometricRecord;
import io.mosip.kernel.biometrics.entities.RegistryIDType;
import io.mosip.kernel.biometrics.entities.VersionType;
import io.mosip.kernel.cbeffutil.container.impl.CbeffContainerImpl;
@Component
public class BiometricsXSDValidator {

    private static Logger regProcLogger = RegProcessorLogger.getLogger(BiometricsXSDValidator.class);

    @Value("${mosip.kernel.xsdstorage-uri}")
    private String configServerFileStorageURL;
    
    @Value("${mosip.kernel.xsdfile}")
    private String schemaFileName;

    private byte[] xsd = null;
    
    public void validateXSD(BiometricRecord biometricRecord ) throws Exception  {
        if(xsd==null) {
            try (InputStream inputStream = new URL(configServerFileStorageURL + schemaFileName).openStream()) {
                xsd =  IOUtils.toByteArray(inputStream);
                regProcLogger.debug(LoggerFileConstant.SESSIONID.toString(),
                        LoggerFileConstant.REGISTRATIONID.toString(), "XSD Value is", (new String(xsd, StandardCharsets.UTF_8)));
            }
        }
        regProcLogger.debug(LoggerFileConstant.SESSIONID.toString(),
                LoggerFileConstant.REGISTRATIONID.toString(), "XSD Value Present", (xsd == null ? false : true));
        CbeffContainerImpl cbeffContainer = new CbeffContainerImpl();
        BIR bir = cbeffContainer.createBIRType(biometricRecord.getSegments());
        CbeffValidator.createXMLBytes(bir, xsd);//validates XSD
    }

	
}
