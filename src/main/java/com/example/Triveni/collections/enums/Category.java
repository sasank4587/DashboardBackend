package com.example.Triveni.collections.enums;

import com.example.Triveni.exception.CategoryNotFound;
import com.example.Triveni.response.ErrorResponse;

public enum Category {
    MEAT,
    VEGETABLE,
    GROCERY;


    public static Category fromString(String categoryName) throws CategoryNotFound {
        if (categoryName == null) {
            return null;
        }
        try {
            return Category.valueOf(categoryName.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle the case where the string does not match any enum value
            throw new CategoryNotFound("category named" + categoryName + "not found");
        }
    }
}
