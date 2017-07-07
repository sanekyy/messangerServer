package ru.spbstu.telematics.messengerServer;


import org.apache.commons.io.FileUtils;
import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by ihb on 17.06.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, ProtocolException {
        //new Server().run();


        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/home/ihb/Documents/Projects/IdeaProjects/messengerServer/package"))));
        byte[] buff = new byte[500];
        gis.read(buff);


        String str = new String(buff);

        System.out.println(str);

    }
}
