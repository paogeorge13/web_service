
package adder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wiredInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wiredInterface">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_Bcast" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_ConsumedRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_CurrentTransfer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_DefaultGetway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_InterfaceIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_InterfaceMAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_InterfaceMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_InterfaceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_MaxTransfer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_NetworkAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_PacketError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wiredInterface", propOrder = {
    "bcast",
    "consumedRate",
    "currentTransfer",
    "defaultGetway",
    "interfaceIP",
    "interfaceMAC",
    "interfaceMask",
    "interfaceName",
    "maxTransfer",
    "networkAddress",
    "packetError"
})
@XmlSeeAlso({
    WirelessInterface.class
})
public class WiredInterface {

    @XmlElement(name = "_Bcast")
    protected String bcast;
    @XmlElement(name = "_ConsumedRate")
    protected String consumedRate;
    @XmlElement(name = "_CurrentTransfer")
    protected String currentTransfer;
    @XmlElement(name = "_DefaultGetway")
    protected String defaultGetway;
    @XmlElement(name = "_InterfaceIP")
    protected String interfaceIP;
    @XmlElement(name = "_InterfaceMAC")
    protected String interfaceMAC;
    @XmlElement(name = "_InterfaceMask")
    protected String interfaceMask;
    @XmlElement(name = "_InterfaceName")
    protected String interfaceName;
    @XmlElement(name = "_MaxTransfer")
    protected String maxTransfer;
    @XmlElement(name = "_NetworkAddress")
    protected String networkAddress;
    @XmlElement(name = "_PacketError")
    protected String packetError;

    /**
     * Gets the value of the bcast property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBcast() {
        return bcast;
    }

    /**
     * Sets the value of the bcast property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBcast(String value) {
        this.bcast = value;
    }

    /**
     * Gets the value of the consumedRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumedRate() {
        return consumedRate;
    }

    /**
     * Sets the value of the consumedRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumedRate(String value) {
        this.consumedRate = value;
    }

    /**
     * Gets the value of the currentTransfer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentTransfer() {
        return currentTransfer;
    }

    /**
     * Sets the value of the currentTransfer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentTransfer(String value) {
        this.currentTransfer = value;
    }

    /**
     * Gets the value of the defaultGetway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultGetway() {
        return defaultGetway;
    }

    /**
     * Sets the value of the defaultGetway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultGetway(String value) {
        this.defaultGetway = value;
    }

    /**
     * Gets the value of the interfaceIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceIP() {
        return interfaceIP;
    }

    /**
     * Sets the value of the interfaceIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceIP(String value) {
        this.interfaceIP = value;
    }

    /**
     * Gets the value of the interfaceMAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceMAC() {
        return interfaceMAC;
    }

    /**
     * Sets the value of the interfaceMAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceMAC(String value) {
        this.interfaceMAC = value;
    }

    /**
     * Gets the value of the interfaceMask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceMask() {
        return interfaceMask;
    }

    /**
     * Sets the value of the interfaceMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceMask(String value) {
        this.interfaceMask = value;
    }

    /**
     * Gets the value of the interfaceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Sets the value of the interfaceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceName(String value) {
        this.interfaceName = value;
    }

    /**
     * Gets the value of the maxTransfer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxTransfer() {
        return maxTransfer;
    }

    /**
     * Sets the value of the maxTransfer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxTransfer(String value) {
        this.maxTransfer = value;
    }

    /**
     * Gets the value of the networkAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkAddress() {
        return networkAddress;
    }

    /**
     * Sets the value of the networkAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkAddress(String value) {
        this.networkAddress = value;
    }

    /**
     * Gets the value of the packetError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPacketError() {
        return packetError;
    }

    /**
     * Sets the value of the packetError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPacketError(String value) {
        this.packetError = value;
    }

}
