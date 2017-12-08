package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.ObjectifyService;
import de.ase11.attendanceTrackingSystem.model.Group;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "groupList")
// If you want you can define the order in which the fields are written
// Optional
@XmlType(propOrder = { "groups"})
public class GroupList {
    @XmlElementWrapper(name = "groups")
    @XmlElement(name = "group")
    private List<Group> groups;

    public GroupList() {}

    public static GroupList createGroupList() {
        GroupList groupList = new GroupList();
        groupList.groups = new ArrayList<>();

        List<Group> groupsTmp = ObjectifyService.ofy().load().type(Group.class).order("groupNumber").list();
        for (Group group : groupsTmp) {
            groupList.groups.add(group);
        }

        return groupList;
    }

    public String groupListToXml() throws JAXBException, IOException {
        OutputStream output = new OutputStream()
        {
            private StringBuilder string = new StringBuilder();
            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b );
            }

            public String toString(){
                return this.string.toString();
            }
        };

        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(GroupList.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Write to output stream
        m.marshal(this, (OutputStream) output);
        String str = output.toString();

        return str;
    }

}
