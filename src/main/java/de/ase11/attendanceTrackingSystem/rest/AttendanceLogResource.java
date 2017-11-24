package de.ase11.attendanceTrackingSystem.rest;

import de.ase11.attendanceTrackingSystem.AttendanceLog;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class AttendanceLogResource extends ServerResource {

    @Get
    public String represent() throws JAXBException, IOException {

        AttendanceLog attendanceLog = AttendanceLog.createAttendanceLog();

        return attendanceLog.attendanceLogToXml();

    }

}
