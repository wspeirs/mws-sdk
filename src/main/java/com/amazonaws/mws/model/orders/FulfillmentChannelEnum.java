
package com.amazonaws.mws.model.orders;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FulfillmentChannelEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FulfillmentChannelEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MFN"/>
 *     &lt;enumeration value="AFN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FulfillmentChannelEnum")
@XmlEnum
public enum FulfillmentChannelEnum {

    MFN,
    AFN;

    public String value() {
        return name();
    }

    public static FulfillmentChannelEnum fromValue(String v) {
        return valueOf(v);
    }

}
