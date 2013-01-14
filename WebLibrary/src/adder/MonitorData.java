
package adder;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monitorData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monitorData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listA" type="{http://adder/}wiredInterface" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listB" type="{http://adder/}wirelessInterface" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listC" type="{http://adder/}accessPoint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monitorData", propOrder = {
    "listA",
    "listB",
    "listC"
})
public class MonitorData {

    @XmlElement(nillable = true)
    protected List<WiredInterface> listA;
    @XmlElement(nillable = true)
    protected List<WirelessInterface> listB;
    @XmlElement(nillable = true)
    protected List<AccessPoint> listC;

    /**
     * Gets the value of the listA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WiredInterface }
     * 
     * 
     */
    public List<WiredInterface> getListA() {
        if (listA == null) {
            listA = new ArrayList<WiredInterface>();
        }
        return this.listA;
    }

    /**
     * Gets the value of the listB property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listB property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListB().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WirelessInterface }
     * 
     * 
     */
    public List<WirelessInterface> getListB() {
        if (listB == null) {
            listB = new ArrayList<WirelessInterface>();
        }
        return this.listB;
    }

    /**
     * Gets the value of the listC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccessPoint }
     * 
     * 
     */
    public List<AccessPoint> getListC() {
        if (listC == null) {
            listC = new ArrayList<AccessPoint>();
        }
        return this.listC;
    }

}
