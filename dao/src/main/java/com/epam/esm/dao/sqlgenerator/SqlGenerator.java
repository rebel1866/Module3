package com.epam.esm.dao.sqlgenerator;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SqlGenerator {
    private static Map<String, Action> actions = new HashMap<>();
    private static final Action concatLike = (sql, key, value) -> {
        StringBuilder newValue = new StringBuilder(value);
        newValue.insert(value.length() - 1, "%");
        newValue.insert(1, "%");
        sql.append("upper(").append(key).append(") like upper(").append(newValue).append(")");
    };
    private static final Action concatPriceFrom = (sql, key, value) -> sql.append("price" + ">=" + value);
    private static final Action concatPriceTo = (sql, key, value) -> sql.append("price" + "<=" + value);
    private static final Action concatCertificate = (sql, key, value) -> sql.append("gift_certificate_id" + " = " + value);

    static {
        actions.put("price_from", concatPriceFrom);
        actions.put("price_to", concatPriceTo);
        actions.put("certificate_name", concatLike);
        actions.put("tag_name", concatLike);
        actions.put("giftCertificateId", concatCertificate);
    }

    public static String generateSQL(String sourceSql, Map<String, String> params) {
        String sorting = params.remove("sorting");
        String sortingOrder = params.remove("sorting_order");
        String targetSql = addWhereBlock(new StringBuilder(sourceSql), params);
        targetSql = addOrderBlock(targetSql, sorting, sortingOrder);
        return targetSql;
    }

    private static String addOrderBlock(String targetSql, String sorting, String sortingOrder) {
        if (sorting != null) return targetSql + " order by " + sorting + " " + sortingOrder;
        else return targetSql;
    }


    private static String addWhereBlock(StringBuilder sourceSql, Map<String, String> params) {
        if (params.size() == 0) return sourceSql.toString();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        sourceSql.append(" where ");
        while (iterator.hasNext()) {
            var entry = iterator.next();
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (!StringUtils.isNumeric(value)) {
                value = wrapApostrophe(value);
            }
            Action action = actions.get(key);
            action.doAction(sourceSql, key, value);
            if (iterator.hasNext()) sourceSql.append(" and ");
        }
        return sourceSql.toString();
    }

    private static String wrapApostrophe(String value) {
        return "'" + value + "'";
    }

    public static String generateUpdateSql(Map<String, String> params, String updateSql) {
        StringBuilder sqlSetPart = new StringBuilder();
        var keyValues = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = keyValues.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (!StringUtils.isNumeric(value)) {
                sqlSetPart.append(key).append("=").append("'").append(value).append("'");
            } else {
                sqlSetPart.append(key).append("=").append(value);
            }
            if (iterator.hasNext()) sqlSetPart.append(", ");
        }
        int insertIndex = 35;
        sqlSetPart.append(" ");
        StringBuilder updateSQlBuilder = new StringBuilder(updateSql);
        updateSQlBuilder.insert(insertIndex, sqlSetPart);
        return updateSQlBuilder.toString();
    }
}
