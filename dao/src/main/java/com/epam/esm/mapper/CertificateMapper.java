package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateMapper implements RowMapper<Certificate> {
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setGiftCertificateId(rs.getInt("gift_certificate_id"));
        certificate.setCertificateName(rs.getString("certificate_name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getInt("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        certificate.setLastUpdateTime(rs.getTimestamp("last_update_time").toLocalDateTime());
        return certificate;
    }
}