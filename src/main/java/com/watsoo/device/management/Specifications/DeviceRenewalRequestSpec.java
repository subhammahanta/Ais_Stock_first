package com.watsoo.device.management.Specifications;

import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;
import java.util.Date;

public class DeviceRenewalRequestSpec {

    public static Specification<DeviceRenewalRequest> hasSearch(String providedSearch){

        return  (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("reqCode")),"%"+providedSearch +"%");
    }

    public static Specification<DeviceRenewalRequest> fromDateToDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            if (fromDate != null && toDate != null) {
                // Adjust toDate to end of the day
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(toDate);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                Date endOfDayToDate = calendar.getTime();

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), fromDate),
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), endOfDayToDate)
                );
            } else if (fromDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), fromDate);
            } else if (toDate != null) {
                // Adjust toDate to end of the day
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(toDate);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                Date endOfDayToDate = calendar.getTime();

                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), endOfDayToDate);
            }
            return null;
        };
    }

}
