package de.ase11.attendanceTrackingSystem;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;

public class AttendanceLogResource extends ServerResource {

    @Get
    public String represent() throws JAXBException, IOException {

        AttendanceLog attendanceLog = AttendanceLog.createAttendanceLog();

        return this.attendanceLogToXml(attendanceLog);

    }

    private String attendanceLogToXml(AttendanceLog attendanceLog) throws JAXBException, IOException {
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
        JAXBContext context = JAXBContext.newInstance(AttendanceLog.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Write to output stream
        m.marshal(attendanceLog, (OutputStream) output);
        String str = output.toString();

        return str;
    }

}
