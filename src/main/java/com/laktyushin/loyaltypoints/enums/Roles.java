package com.laktyushin.loyaltypoints.enums;

public enum Roles {
    ADMIN,
    REGISTERED_BUSINESS,    // can award points to customer
    COMMON_BUSINESS,         // can only write off customer's points
    UNREGISTERED
}
