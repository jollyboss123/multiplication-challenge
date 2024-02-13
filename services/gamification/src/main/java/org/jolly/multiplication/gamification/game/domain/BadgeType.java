package org.jolly.multiplication.gamification.game.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author jolly
 */
@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BadgeType {
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    FIRST_WON("First time"),
    LUCKY_NUMBER("Lucky number");

    private final String description;
}
