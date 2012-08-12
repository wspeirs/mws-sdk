
package com.amazonaws.mws.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OrderStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Unshipped"/>
 *     &lt;enumeration value="PartiallyShipped"/>
 *     &lt;enumeration value="Shipped"/>
 *     &lt;enumeration value="Canceled"/>
 *     &lt;enumeration value="Unfulfillable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OrderStatusEnum")
@XmlEnum
public enum OrderStatusEnum {

    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Unshipped")
    UNSHIPPED("Unshipped"),
    @XmlEnumValue("PartiallyShipped")
    PARTIALLY_SHIPPED("PartiallyShipped"),
    @XmlEnumValue("Shipped")
    SHIPPED("Shipped"),
    @XmlEnumValue("Canceled")
    CANCELED("Canceled"),
    @XmlEnumValue("Unfulfillable")
    UNFULFILLABLE("Unfulfillable");
    private final String value;

    OrderStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderStatusEnum fromValue(String v) {
        for (OrderStatusEnum c: OrderStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
