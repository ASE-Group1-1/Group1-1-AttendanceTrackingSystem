package de.ase11.attendanceTrackingSystem;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;

public class AttendanceCreateResource extends ServerResource {

    @Post
    public String represent() throws JAXBException, IOException {

        Attendance attendance = new Attendance();

        //TODO: take POST parameter and create a new Attendance

        return this.attendanceToXml(attendance);

    }

    private String attendanceToXml(Attendance attendance) throws JAXBException, IOException {
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
        JAXBContext context = JAXBContext.newInstance(Attendance.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Write to output stream
        m.marshal(attendance, (OutputStream) output);
        String str = output.toString();

        return str;
    }

}
