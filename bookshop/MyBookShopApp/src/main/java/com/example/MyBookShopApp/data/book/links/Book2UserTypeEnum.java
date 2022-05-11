package com.example.MyBookShopApp.data.book.links;

public enum Book2UserTypeEnum {
    KEPT,
    CART,
    PAID,
    ARCHIVED,
    GIFT;

    public static String getTypeByTypeId(Integer typeId) {
        switch (typeId) {
            case 1:
                return Book2UserTypeEnum.KEPT.toString();
            case 2:
                return Book2UserTypeEnum.CART.toString();
            case 3:
                return Book2UserTypeEnum.PAID.toString();
            case 4:
                return Book2UserTypeEnum.ARCHIVED.toString();
            case 5:
                return Book2UserTypeEnum.GIFT.toString();
            default:
                return "";
        }
    }
}
