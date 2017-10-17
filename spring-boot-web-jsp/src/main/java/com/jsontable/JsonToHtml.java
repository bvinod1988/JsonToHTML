package com.jsontable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonToHtml {

    private static final Gson GSON = new GsonBuilder().create();

    private static final String CSS =
            "<style>\n" +
            "    table {border: 1px solid black;}\n" +
            "    tr {border: 1px solid gray;}\n" +
            "    td {border: 1px solid silver;}\n" +
            "</style>\n";

    private static final String TABLE_OPENING = "<table>";
    private static final String TABLE_CLOSING = "</table>";
    private static final String TR_OPENING = "<tr>";
    private static final String TR_CLOSING = "</tr>";
    private static final String TD_OPENING = "<td>";
    private static final String TD_CLOSING = "</td>";
    private static final String NBSP = "&nbsp;";

    private final Stack stack = new Stack();

    public String convert(String json) {
        stack.reset();

        if (json == null) {
            return "" + json;
        } else {
            LinkedHashMap<?, ?> map = GSON.fromJson(json, LinkedHashMap.class);
            parseValue(map);
            return CSS + stack.getValue();
        }
    }


    private void parseValue(Object value) {
        if (value instanceof List) {
            parseListValue((List) value);
        } else if (value instanceof Map) {
            parseMapValue((Map) value);
        } else {
            stack.push(value != null ? "" + value : NBSP);
        }
    }

    private void parseListValue(List items) {
        stack.push(TABLE_OPENING);

        if (!items.isEmpty()) {
            Object item = items.get(0);
            if (item instanceof Map) {
                parseListHeader((Map) item);
            }
        }

        for (Object item : items) {
            if (item instanceof Map) {
                parseListRow((Map) item);
            } else {
                stack.push("" + item);
            }
        }

        stack.push(TABLE_CLOSING);
    }


    private void parseListHeader(Map<?, ?> map) {
        stack.push(TR_OPENING);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stack.push(TD_OPENING);
            stack.push(parserListHeaderCell(entry));
            stack.push(TD_CLOSING);
        }
        stack.push(TR_CLOSING);
    }

    private String parserListHeaderCell(Map.Entry<?, ?> entry) {
        return "" + entry.getKey();
    }

    private void parseListRow(Map<?, ?> map) {
        stack.push(TR_OPENING);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stack.push(TD_OPENING);
            stack.push(parserListRowCell(entry));
            stack.push(TD_CLOSING);
        }
        stack.push(TR_CLOSING);
    }

    private String parserListRowCell(Map.Entry<?, ?> entry) {
        return "" + entry.getValue();
    }

    private void parseMapValue(Map<?, ?> items) {
        stack.push(TABLE_OPENING);
        parseTableRow(items);
        stack.push(TABLE_CLOSING);
    }

    private void parseTableRow(Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stack.push(TR_OPENING);
            parserTableCell(entry);
            stack.push(TR_CLOSING);
        }
    }

    private void parserTableCell(Map.Entry<?, ?> entry) {
        if (entry.getValue() != null) {
            parserRow(entry);
        } else {
            stack.push(NBSP);
        }
    }

    private void parserRow(Map.Entry<?, ?> entry) {
        if (isSimple(entry.getKey()) && isSimple(entry.getValue())) {

            stack.push(TD_OPENING);
            parseKey(entry.getKey());
            stack.push(TD_CLOSING);
            stack.push(TD_OPENING);
            parseValue(entry.getValue());
            stack.push(TD_CLOSING);

        } else {
            stack.push(TD_OPENING);

            stack.push(TABLE_OPENING);
            stack.push(TR_OPENING);

            stack.push(TD_OPENING);
            parseKey(entry.getKey());
            stack.push(TD_CLOSING);
            stack.push(TD_OPENING);
            parseValue(entry.getValue());
            stack.push(TD_CLOSING);

            stack.push(TR_CLOSING);
            stack.push(TABLE_CLOSING);

            stack.push(TD_CLOSING);
        }
    }

    private void parseKey(Object key) {
        stack.push("" + key);
    }

    private boolean isSimple(Object o) {
        if (o instanceof String) return true;
        if (o instanceof Byte) return true;
        if (o instanceof Character) return true;
        if (o instanceof Integer) return true;
        if (o instanceof Long) return true;
        if (o instanceof Float) return true;
        if (o instanceof Double) return true;

        return false;
    }
}
