package org.example.formazione.soap.contractfirst;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PersonTest {

    private static final Logger LOG = LoggerFactory.getLogger(PersonTest.class);

    /**
     * Helper method to copy bytes from an InputStream to an OutputStream.
     */
    private static void copyInputStream(InputStream in, OutputStream out) throws Exception {
        int c = 0;
        try {
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            in.close();
        }
    }

    /**
     * Helper method to read bytes from an InputStream and return them as a String.
     */
    private static String getStringFromInputStream(InputStream in) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        copyInputStream(in, bos);
        bos.close();
        return bos.toString();
    }

    @Test
    public void sendRequestXpathComponent() throws Exception {

        String res;

        URLConnection connection = new URL("http://localhost:8181/cxf/person_xpath_component").openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);


        OutputStream os = connection.getOutputStream();
        InputStream fis = PersonTest.class.getResourceAsStream("/request.xml");
        copyInputStream(fis, os);

        InputStream is = connection.getInputStream();
        LOG.info("the response is ====> ");
        res = getStringFromInputStream(is);
        LOG.info(res);
        Assert.assertTrue(res.contains("OK"));
    }


    @Test
    public void sendRequestXpathJava() throws Exception {

        String res;

        URLConnection connection = new URL("http://localhost:8181/cxf/person_xpath_java").openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);


        OutputStream os = connection.getOutputStream();
        InputStream fis = PersonTest.class.getResourceAsStream("/request.xml");
        copyInputStream(fis, os);

        InputStream is = connection.getInputStream();
        LOG.info("the response is ====> ");
        res = getStringFromInputStream(is);
        LOG.info(res);
        Assert.assertTrue(res.contains("OK"));
    }


    @Test
    public void sendRequestPojo() throws Exception {

        String res;

        URLConnection connection = new URL("http://localhost:8181/cxf/person_Pojo").openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);


        OutputStream os = connection.getOutputStream();
        InputStream fis = PersonTest.class.getResourceAsStream("/request.xml");
        copyInputStream(fis, os);

        InputStream is = connection.getInputStream();
        LOG.info("the response is ====> ");
        res = getStringFromInputStream(is);
        LOG.info(res);
        Assert.assertTrue(res.contains("OK"));
    }



}
