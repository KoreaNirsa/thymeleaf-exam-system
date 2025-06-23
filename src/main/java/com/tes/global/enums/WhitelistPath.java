package com.tes.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WhitelistPath {
    LOGIN("/login"),
    JOIN("/join"),
    STATIC_CSS("/css"),
    STATIC_JS("/js"),
    STATIC_IMG("/images"),
    FAVICON("/favicon.ico");

    private final String path;
}
