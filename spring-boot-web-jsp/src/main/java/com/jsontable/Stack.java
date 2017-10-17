package com.jsontable;

class Stack {

    private final StringBuffer text = new StringBuffer();
    private int level = 0;

    void reset() {
        text.setLength(0);
    }

    void push(String token) {
        if (isClosingTag(token)) {
            level--;
        }

        text.append(getIndentation(level));
        text.append(token);

        if (isOpeningTag(token)) {
            level++;
        }

        text.append(String.format("%n"));
    }

    String getValue() {
        return text.toString();
    }

    private boolean isOpeningTag(String token) {
        return token.startsWith("<") && !token.startsWith("</") && token.endsWith(">");
    }

    private boolean isClosingTag(String token) {
        return token.startsWith("</") && token.endsWith(">");
    }

    private String getIndentation(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("    ");
        }
        return sb.toString();
    }
}
