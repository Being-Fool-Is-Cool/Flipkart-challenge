import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cabBooking {

    public static void AverageRating(String rideDetails[][], int R, int C, String customerName) {
        /* New change*/
        Map<String, List> driverCustomerMap = new HashMap<String, List>();
        for(int i=0; i<R;i++) {
            if(!driverCustomerMap.containsKey(rideDetails[i][0])) {
                List<String> lst = new ArrayList<String>();
                lst.add(rideDetails[i][2]);
                driverCustomerMap.put(rideDetails[i][0], lst);
            }
            else {
                List<String> lst = new ArrayList<String>();
                lst = driverCustomerMap.get(rideDetails[i][0]);
                lst.add(rideDetails[i][2]);
                driverCustomerMap.put(rideDetails[i][0], lst);
            }
        }


        Map<String, List> customer = new HashMap<String, List>();
        for(int i=0;i<R;i++) {
            if(!customer.containsKey(rideDetails[i][2])) {
                List<Integer> lst = new ArrayList<Integer>();
                lst.add(Integer.parseInt(rideDetails[i][3]));
                customer.put(rideDetails[i][2], lst);
            }
            else{
                List<Integer> lst = new ArrayList<Integer>();
                lst = customer.get(rideDetails[i][2]);
                lst.add(Integer.parseInt(rideDetails[i][3]));
                customer.put(rideDetails[i][2], lst);
            }
        }

        Map<String, List> driver = new HashMap<String, List>();
        for(int i=0;i<R;i++) {
            if(!driver.containsKey(rideDetails[i][0])) {
                List<Integer> lst = new ArrayList<Integer>();
                lst.add(Integer.parseInt(rideDetails[i][1]));
                driver.put(rideDetails[i][1], lst);
            }
            else{
                List<Integer> lst = new ArrayList<Integer>();
                lst = driver.get(rideDetails[i][0]);
                lst.add(Integer.parseInt(rideDetails[i][1]));
                driver.put(rideDetails[i][0], lst);
            }
        }

        //Finding Average Customer Rating.....
        Map<String, Map> customerRating = new HashMap<String, Map>();
        for(int i=0; i< R; i++) {
            if(!customerRating.containsKey(rideDetails[i][2])) {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                temp.put("rating", Integer.parseInt(rideDetails[i][3]));
                temp.put("count", 1);
                customerRating.put(rideDetails[i][2], temp);
            }
            else
            {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                temp = customerRating.get(rideDetails[i][2]);
                int rating = temp.get("rating");
                int count = temp.get("count");
                rating += Integer.parseInt(rideDetails[i][3]);
                count++;
                temp.put("rating", rating);
                temp.put("count", count);
            }
        }

        //Printing Average Customer Rating.....
        for ( String name: customerRating.keySet()){
            Map<String, Integer> temp = new HashMap<String, Integer>();
            temp = customerRating.get(name);
            float rate = (float)temp.get("rating") / (float)temp.get("count");
            System.out.print(name);
            System.out.print(" Rating is ");
            System.out.println(rate);
        }

        //Finding Driver Average Customer Rating.....
        Map<String, Map> driverRating = new HashMap<String, Map>();
        for(int i=0; i< R; i++) {
            if(!driverRating.containsKey(rideDetails[i][0])) {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                temp.put("rating", Integer.parseInt(rideDetails[i][1]));
                temp.put("count", 1);
                driverRating.put(rideDetails[i][0], temp);
            }else {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                temp = driverRating.get(rideDetails[i][0]);
                int rating = temp.get("rating");
                int count = temp.get("count");
                rating += Integer.parseInt(rideDetails[i][1]);
                count++;
                temp.put("rating", rating);
                temp.put("count", count);
            }
        }


        System.out.print("For Given Customer ");
        System.out.println(customerName);
        System.out.println("Eligible Drivers are ");

        //Getting Customer rating for Given Customer
        Map<String, Integer> givenCustomerRate = new HashMap<String, Integer>();
        givenCustomerRate = customerRating.get(customerName);
        int customerRate = givenCustomerRate.get("rating");

        //if CustomerRate is less than or equal to 1, No driver to be assign
        List<Integer> lst = new ArrayList<Integer>();
        lst = customer.get(customerName);
        for(int ij: lst) {
            if(ij <=1 ) {
                System.out.println("Customer is Not Eligible");
                return;
            }

        }

        {
            int num = 0;
            for(String driverName: driverRating.keySet()) {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                temp = driverRating.get(driverName);
                int driverRate = temp.get("rating");
                int count = temp.get("count");
                if(driverRate>= customerRate) {
                    System.out.println(driverName);
                    num ++;
                }
            }
            //If No driver fullfill first creteria.....
            if(num == 0) {
                for(String driverName: driverRating.keySet()) {
                    Map<String, Integer> temp = new HashMap<String, Integer>();
                    temp = driverRating.get(driverName);
                    int driverRate = temp.get("rating");
                    int count = temp.get("count");
                    List<Integer> lst1 = new ArrayList<Integer>();
                    lst1 = driver.get(driverName);
                    for(int ij: lst1) {
                        if(ij != 1){
                            //List<String> temp = new ArrayList<String>();
                            List<String> customerList = driverCustomerMap.get(driverName);
                            for (String custName: customerList) {
                                if(custName == customerName) {
                                    System.out.println(driverName);
                                    num++;
                                }
                            }
                        }
                    }

                }
            }
            //When No eligible driver Found
            if(num == 0) {
                System.out.println("No Eligible Driver Found");
                return;
            }
        }


    }


    public static void main(String []args) {
        String rideDetails[][] = {
            //{"Driver's Name", "Driver's Rating", "Customer's Name", "Customer's Rating"}
            {"d1", "4", "c1", "5"},
            {"d1", "5", "c2", "4"},
            {"d1", "1", "c3", "2"},
            {"d2", "5", "c1", "1"},
            {"d2", "5", "c2", "5"},
            {"d2", "4", "c3", "5"},
            {"d3", "3", "c1", "2"},
            {"d3", "4", "c2", "5"},
            {"d3", "3", "c3", "3"}
        };
        String customerName = "c1";
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        AverageRating(rideDetails, 9, 4, customerName);
    }
}
