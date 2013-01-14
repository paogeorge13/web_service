
package adder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accessPoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accessPoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_APChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_APESSID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_APMAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_APMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_APSignalLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessPoint", propOrder = {
    "apChannel",
    "apessid",
    "apmac",
    "apMode",
    "apSignalLevel"
})
public class AccessPoint {

    @XmlElement(name = "_APChannel")
    protected String apChannel;
    @XmlElement(name = "_APESSID")
    protected String apessid;
    @XmlElement(name = "_APMAC")
    protected String apmac;
    @XmlElement(name = "_APMode")
    protected String apMode;
    @XmlElement(name = "_APSignalLevel")
    protected String apSignalLevel;

    /**
     * Gets the value of the apChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPChannel() {
        return apChannel;
    }

    /**
     * Sets the value of the apChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPChannel(String value) {
        this.apChannel = value;
    }

    /**
     * Gets the value of the apessid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPESSID() {
        return apessid;
    }

    /**
     * Sets the value of the apessid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPESSID(String value) {
        this.apessid = value;
    }

    /**
     * Gets the value of the apmac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPMAC() {
        return apmac;
    }

    /**
     * Sets the value of the apmac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPMAC(String value) {
        this.apmac = value;
    }

    /**
     * Gets the value of the apMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPMode() {
        return apMode;
    }

    /**
     * Sets the value of the apMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPMode(String value) {
        this.apMode = value;
    }

    /**
     * Gets the value of the apSignalLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPSignalLevel() {
        return apSignalLevel;
    }

    /**
     * Sets the value of the apSignalLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPSignalLevel(String value) {
        this.apSignalLevel = value;
    }

}
