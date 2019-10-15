package com.jazzchris.currencyexchange.aspect;

import com.jazzchris.currencyexchange.service.FailMessage;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionMetrics {

    private final MeterRegistry meterRegistry;

    @Autowired
    public TransactionMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("execution(* com.jazzchris.currencyexchange.service.*.proceed*(..))")
    public String aroundTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        increment(Tag.TOTAL);
        String result = null;
        try {
            result = (String) joinPoint.proceed();
        } finally {
            if (result == null) {
                increment(Tag.ERROR);
            }
            else if (result.startsWith("Success")) {
                increment(Tag.SUCCESS);
            }
            else {
                FailMessage message = FailMessage.parse(result);
                switch (message) {
                    case RATES_NOT_ACTUAL:
                        increment(Tag.RATES_NOT_ACTUAL);
                        break;
                    case USER_NO_FUNDS:
                        increment(Tag.USER_NO_FUNDS);
                        break;
                    case OFFICE_NO_FUNDS:
                        increment(Tag.OFFICE_NO_FUNDS);
                        break;
                    case CANNOT_CONNECT:
                        increment(Tag.NO_CONNECTION);
                        break;
                }
            }
        }
        return result;
    }

    private void increment(Tag tag) {
        meterRegistry.counter("transaction", "type", tag.separatedByDots).increment();
    }

    private enum Tag {
        TOTAL("total"),
        SUCCESS("success"),
        RATES_NOT_ACTUAL("rates.not.actual"),
        USER_NO_FUNDS("user.no.funds"),
        OFFICE_NO_FUNDS("office.no.funds"),
        NO_CONNECTION("no.connection"),
        ERROR("error.during.proceeding");

        private final String separatedByDots;

        private Tag(String separatedByDots) {
            this.separatedByDots = separatedByDots;
        }
    }
}
