package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.services.CustomerServices;
import com.example.services.CustomerServicesImpl;
import com.example.services.ModelLoader;
import com.example.services.ModelLoaderImpl;
import com.example.utils.Util;

public class CustomerServicesTest {

    private static CustomerServices customerServices;
    private static ModelLoader loader;
    private static String addressLookupURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng={lat}," +
            "{lng}&key=AIzaSyAuX8p86wzC1zFVY9haqQiheH-LXH34cf4";

    @BeforeClass
    public static void setUp() {
        loader = ModelLoaderImpl.getInstance("data.json", "/tmp/test_output.json");
        customerServices = CustomerServicesImpl.getInstance(addressLookupURL);
    }

    @AfterClass
    public static void tearDown() {
        customerServices = null;
    }

    @Test
    public void testFindClosestCustomer() {
        Customer customer = new Customer();
        customer.set_id("testCustomer");
        customer.setLatitude(48.490681);
        customer.setLongitude(-123.389058);
        String expected_id = "5411823e363f1af9b61016c2";
        Iterable<Customer> customers = loader.loadCustomers();
        Customer result = customerServices.findClosestCustomer(customer, customers);
        assertTrue(expected_id.equals(result.get_id()));
        customer.setLatitude(48.490681);
        customer.setLongitude(123.389058);
        result = customerServices.findClosestCustomer(customer, customers);
        assertTrue(expected_id.equals(result.get_id()));
        customer.setLatitude(-48.490681);
        customer.setLongitude(-123.389058);
        result = customerServices.findClosestCustomer(customer, customers);
        assertTrue(expected_id.equals(result.get_id()));
        customer.setLatitude(-48.490681);
        customer.setLongitude(123.389058);
        result = customerServices.findClosestCustomer(customer, customers);
        assertTrue(expected_id.equals(result.get_id()));
    }

    @Test
    public void testGetCustomersOrderedByEmail() {
        List<String> expectedOrdered = new ArrayList<String>(Arrays.asList("aimee.fisher@utarian.me",
                "amanda.conrad@extragen.info", "beryl.young@plasmox.co.uk", "bradshaw.coffey@evidends.tv",
                "carly.woodward@daycore.ca", "carter.rutledge@verbus.net", "chandler.brown@xleen.org",
                "deborah.adams@extrawear.org", "glover.whitney@mangelica.me", "graham.duran@supremia.us",
                "herring.miller@bovis.net", "holcomb.burks@bostonic.com", "huffman.ryan@iplax.com",
                "irene.langley@netbook.co.uk", "jocelyn.bond@extremo.net", "long.frye@limozen.io",
                "mcclure.rush@zaphire.io", "michele.hampton@geostele.us", "monica.lott@corepan.biz",
                "nannie.walter@enquility.us", "odessa.case@ginkogene.info", "patricia.bender@techtrix.com",
                "penny.wolfe@zillan.io", "quinn.gay@insurety.co.uk", "ryan.miranda@mantro.biz",
                "simon.curtis@magmina.biz", "tillman.fitzgerald@ziore.ca", "vega.nunez@exoplode.tv",
                "whitley.webb@infotrips.biz", "wise.acevedo@pivitol.biz"));
        Iterable<Customer> customers = loader.loadCustomers();
        SortedSet<Customer> sortedByMail = customerServices.getCustomersOrderedByEmail(customers ,SortOrder.ASC);
        List<String> sortedEmails = sortedByMail.stream().map(customer -> customer.getEmail()).collect(Collectors.toList());
        assertArrayEquals("Array match and have the same order", expectedOrdered.toArray(), sortedEmails.toArray());
    }

    @Test
    public void testLookupAddress() {
        Address address = null;
        Customer customer = new Customer();
        customer.set_id("testCustomer");
        customer.setLatitude(48.490681);
        customer.setLongitude(-123.389058);
        address = customerServices.lookupAddress(customer.getLatitude(), customer.getLongitude());
        assertTrue(address != null);
        String addrExpected = "4452 West Saanich Road, Victoria, BC V8Z 5K8, Canada";
        String addrResult = address.getAddresses().stream()
                .filter(addrDetails -> addrDetails.getAddress().equals(addrExpected))
                .collect(Collectors.toList()).get(0).getAddress();
        assertTrue(addrResult.contains(addrExpected));
    }

    @Test
    public void testGetCustomerByEyeColour() {
        Customer cust1 = new Customer();
        cust1.set_id("cust1");
        cust1.setEyeColor("brown");
        Customer cust2 = new Customer();
        cust2.set_id("cust2");
        cust2.setEyeColor("blue");
        Customer cust3 = new Customer();
        cust3.set_id("cust3");
        cust3.setEyeColor("green");
        Customer cust4 = new Customer();
        cust4.set_id("cust4");
        cust4.setEyeColor("blue");
        Customer cust5 = new Customer();
        cust5.set_id("cust5");
        cust5.setEyeColor("brown");
        Iterable<Customer> customers = new ArrayList<Customer>(Arrays.asList(cust1, cust2, cust3, cust4, cust5));
        Iterable<Customer> resultCustomers = customerServices.getCustomersByEyeColour(customers, EyeColour.BROWN);
        List<Customer> expectedCustomer = new ArrayList<Customer>(Arrays.asList(cust1, cust5));
        assertArrayEquals("The customer filtered by Eye colour match", expectedCustomer.toArray(), 
                Util.getStreamForIterable(resultCustomers).collect(Collectors.toList()).toArray());
    }
}
