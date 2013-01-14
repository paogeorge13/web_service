
package adder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the adder package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetMonitorData_QNAME = new QName("http://adder/", "setMonitorData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: adder
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetMonitorData }
     * 
     */
    public SetMonitorData createSetMonitorData() {
        return new SetMonitorData();
    }

    /**
     * Create an instance of {@link WiredInterface }
     * 
     */
    public WiredInterface createWiredInterface() {
        return new WiredInterface();
    }

    /**
     * Create an instance of {@link AccessPoint }
     * 
     */
    public AccessPoint createAccessPoint() {
        return new AccessPoint();
    }

    /**
     * Create an instance of {@link MonitorData }
     * 
     */
    public MonitorData createMonitorData() {
        return new MonitorData();
    }

    /**
     * Create an instance of {@link WirelessInterface }
     * 
     */
    public WirelessInterface createWirelessInterface() {
        return new WirelessInterface();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetMonitorData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://adder/", name = "setMonitorData")
    public JAXBElement<SetMonitorData> createSetMonitorData(SetMonitorData value) {
        return new JAXBElement<SetMonitorData>(_SetMonitorData_QNAME, SetMonitorData.class, null, value);
    }

}
