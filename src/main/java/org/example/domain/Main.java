package org.example.domain;

import org.example.domain.ext.Metadata;
import org.example.util.MyBeanUtils;

/**
 * @date : 2021/8/9
 */
public class Main {
    public static void main(String[] args) {
        Product product = new Product();

        Metadata metadata = new Metadata();
        String upcomingValue="{\"occurrenceId\":\"1oyPoiIgSLyLupl-mzeDyQ\",\"startTime\":1628645400.000000000,\"duration\":60}";

        MyBeanUtils.setPropValue(metadata,"upcomingOccurrence",upcomingValue);

       // String value="";
       // MyBeanUtils.setPropValue(metadata,"alternativeHost",);
        System.out.println(metadata);
    }
}
