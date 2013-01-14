
package adder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wirelessInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wirelessInterface">
 *   &lt;complexContent>
 *     &lt;extension base="{http://adder/}wiredInterface">
 *       &lt;sequence>
 *         &lt;element name="_BaseStationMAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_Channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_DiscardedPackets" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_ESSID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_LinkQuality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_Mode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_NoisePower" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_SignalLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_TransmitPower" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wirelessInterface", propOrder = {
    "baseStationMAC",
    "channel",
    "discardedPackets",
    "essid",
    "linkQuality",
    "mode",
    "noisePower",
    "signalLevel",
    "transmitPower"
})
public class WirelessInterface
    extends WiredInterface
{

    @XmlElement(name = "_BaseStationMAC")
    protected String baseStationMAC;
    @XmlElement(name = "_Channel")
    protected String channel;
    @XmlElement(name = "_DiscardedPackets")
    protected String discardedPackets;
    @XmlElement(name = "_ESSID")
    protected String essid;
    @XmlElement(name = "_LinkQuality")
    protected String linkQuality;
    @XmlElement(name = "_Mode")
    protected String mode;
    @XmlElement(name = "_NoisePower")
    protected String noisePower;
    @XmlElement(name = "_SignalLevel")
    protected String signalLevel;
    @XmlElement(name = "_TransmitPower")
    protected String transmitPower;

    /**
     * Gets the value of the baseStationMAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseStationMAC() {
        return baseStationMAC;
    }

    /**
     * Sets the value of the baseStationMAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseStationMAC(String value) {
        this.baseStationMAC = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the discardedPackets property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscardedPackets() {
        return discardedPackets;
    }

    /**
     * Sets the value of the discardedPackets property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscardedPackets(String value) {
        this.discardedPackets = value;
    }

    /**
     * Gets the value of the essid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESSID() {
        return essid;
    }

    /**
     * Sets the value of the essid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESSID(String value) {
        this.essid = value;
    }

    /**
     * Gets the value of the linkQuality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkQuality() {
        return linkQuality;
    }

    /**
     * Sets the value of the linkQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkQuality(String value) {
        this.linkQuality = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the noisePower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoisePower() {
        return noisePower;
    }

    /**
     * Sets the value of the noisePower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoisePower(String value) {
        this.noisePower = value;
    }

    /**
     * Gets the value of the signalLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignalLevel() {
        return signalLevel;
    }

    /**
     * Sets the value of the signalLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignalLevel(String value) {
        this.signalLevel = value;
    }

    /**
     * Gets the value of the transmitPower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmitPower() {
        return transmitPower;
    }

    /**
     * Sets the value of the transmitPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmitPower(String value) {
        this.transmitPower = value;
    }

}
