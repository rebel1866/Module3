package com.epam.esm.converter;

import com.epam.esm.dto.UpdateCertificateRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateDtoToMapConverter {
    public static Map<String, String> convertToMap(UpdateCertificateRequest request) {
        Map<String, String> params = new HashMap<>();
        String certificateName = request.getCertificateName();
        String description = request.getDescription();
        String price = request.getPrice();
        String duration = request.getDuration();
        if (certificateName != null) {
            params.put("certificate_name", certificateName);
        }
        if (description != null) {
            params.put("description", description);
        }
        if (price != null) {
            params.put("price", price);
        }
        if (duration != null) {
            params.put("duration", duration);
        }
        return params;
    }
}
