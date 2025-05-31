package com.golemon.blogbackend.enums;

public enum BlogSystemConstantsEnum {

    // Article status
    ARTICLE_STATUS_NORMAL("0"),
    ARTICLE_STATUS_DRAFT("1"),

    // Category status
    CATEGORY_STATUS_NORMAL("0"),

    // Link status
    LINK_STATUS_PASS("0"),

    // Comment root node
    COMMENT_ROOT(-1L),

    // Redis prefix
    REDIS_USER_ID_PREFIX("login:user:id:"),
    REDIS_ARTICLE_VIEW_COUNT_KEY("article:viewCounts"),

    // User type
    ADMIN_USER("1");

    private final String value;
    private final Long longValue;

    // Constructor for String type values
    BlogSystemConstantsEnum(String value) {
        this.value = value;
        this.longValue = null;  // Default no need for long type value
    }

    // Constructor for Long type values
    BlogSystemConstantsEnum(Long longValue) {
        this.value = null;  // Default no need for String type value
        this.longValue = longValue;
    }

    public String getValue() {
        return value;
    }

    public Long getLongValue() {
        return longValue;
    }
}
