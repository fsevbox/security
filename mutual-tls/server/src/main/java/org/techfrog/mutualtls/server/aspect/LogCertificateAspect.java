package org.techfrog.mutualtls.server.aspect;

import org.apache.commons.beanutils.ConvertUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.x500.X500Principal;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class LogCertificateAspect {

    private static final Logger LOGGER = Logger.getLogger(LogCertificateAspect.class.getName());
    private static final String KEY_CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";

    @Before("@annotation(logCertificate)")
    public void logCertificateIfPresent(LogCertificate logCertificate) {
        Optional<String> certificateDetails;
        if(logCertificate.detailed()) {
            certificateDetails = getPublicCertificate();
        } else {
            certificateDetails = getSubjectDistinguishedName();
        }
        certificateDetails.ifPresent(certificate -> LOGGER.info("Received the following certificate details: " + certificate));
    }

    private Optional<String> getSubjectDistinguishedName() {
        return getCertificateFromRequest()
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .map(X509Certificate::getSubjectX500Principal)
                .map(X500Principal::getName);
    }

    private Optional<String> getPublicCertificate() {
        return getCertificateFromRequest()
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .map(ConvertUtils::convert);
    }

    private Optional<X509Certificate[]> getCertificateFromRequest() {
        return Optional.ofNullable((X509Certificate[])((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute(KEY_CERTIFICATE_ATTRIBUTE));
    }
}
