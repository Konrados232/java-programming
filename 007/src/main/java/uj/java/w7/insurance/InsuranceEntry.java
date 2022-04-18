package uj.java.w7.insurance;

import java.math.BigDecimal;

public record InsuranceEntry(BigDecimal policyID, String stateCode, String county,
                             BigDecimal eqSiteLimit, BigDecimal huSiteLimit, BigDecimal flSiteLimit,
                             BigDecimal frSiteLimit, BigDecimal tiv2011, BigDecimal tiv2012,
                             BigDecimal eqSiteDeductible, BigDecimal huSiteDeductible, BigDecimal flSiteDeductible,
                             BigDecimal frSiteDeductible, BigDecimal pointLatitude, BigDecimal pointLongitude,
                             String line, String construction, BigDecimal pointGranularity) {
}