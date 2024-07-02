package com.example.kioskhelper.domain.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Menu {
    //버거킹
    WHOOPER("와퍼", "버거킹"),
    SHRIMP_BURGER("슈림프 버거", "버거킹"),
    QUATTRO_CHEESE_BURGER("콰트로 치즈 버거", "버거킹"),

    // 맥도날드
    BIG_MAC("빅맥", "맥도날드"),
    SHANGHAI_BURGER("상하이 버거", "맥도날드"),
    SWEET_BBQ_BURGER("슈비 버거", "맥도날드"),
    DOUBLE_QUARTER_POUNDER_CHEESE("더블 쿼터파운드 치즈", "맥도날드"),
    QUARTER_POUNDER_CHEESE("쿼터파운드 치즈", "맥도날드"),
    MC_CRISPY_DELUXE_BURGER("맥크리스피 디럭스버거", "맥도날드"),
    MC_CRISPY_CLASSIC_BURGER("맥크리스피 클래식버거", "맥도날드"),
    BURGER_1955("1955 버거", "맥도날드"),
    MC_CHICKEN_MOZZARELLA("맥치킨 모짜렐라", "맥도날드"),
    MC_CHICKEN("맥치킨", "맥도날드"),
    BACON_TOMATO_DELUXE("베이컨 토마토 디럭스", "맥도날드"),
    SHUSHU_BURGER("슈슈버거", "맥도날드"),
    DOUBLE_BULGOGI_BURGER("더블 불고기버거", "맥도날드"),
    EGG_BULGOGI_BURGER("에그 불고기버거", "맥도날드"),
    MAX_SPICY_SHANGHAI_BURGER_SET("맥스파이시 상하이버거 세트", "맥도날드");

    // 롯데리아

    //맥도날드

    //롯데리아
    private final String order;
    private final String brand;

    public static boolean contains(String test, String brand) {
        for (Menu c : Menu.values()) {
            if (c.brand.equals(brand) && test.contains(c.order)) {
                return true;
            }
        }
        return false;
    }
}
