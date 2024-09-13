package supermarket.inventory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Delivery {
    private String trackNo;
    private String name;
    private int postCode;
    private String[] address; 
    private Order order;

    public Delivery(String trackNo,String name,int postCode, String[] address){
        this.trackNo = trackNo;
        this.name = name;
        this.postCode = postCode;
        this.address = address;
    }

    public static String genTrackNo(){
        StringBuilder tNo = new StringBuilder(10);      //Length is 10//
        for (int i=0 ; i<3;i++) {
            tNo.append((char)('A' + Math.random() * ('F' - 'A' + 1)));
        }
        for (int i = 0; i < 7; i++) {
            tNo.append((int)(Math.random()*10));
        }

        //set the random generated tracking No the delivery
        return tNo.toString();
    }

    public static Delivery addDelivery(Scanner scanner,Order order){
        boolean finish = false; 
        do {
                String[] address = new String[3];
                String receipentName;
                int postCode = 0;   
                int lineNo = 0;
                    
                System.out.print("\nEnter receipent name (or enter to skip delivery): ");
                receipentName = scanner.nextLine().trim();
                    if(receipentName.isEmpty())
                        break;
                    else
                        receipentName = formatString(receipentName);
                    System.out.printf("Enter address line %d: ",lineNo+1);
                    address[lineNo] = scanner.nextLine().trim();
                    if(address[lineNo].isEmpty()){
                        System.out.println("\u001B[31mPlease enter a valid address.\u001B[0m");
                        continue;
                    }
                    lineNo++;
                    for(; lineNo < 3;lineNo++){
                        //start from line 2 since 
                        System.out.printf("Enter address line %d (Enter to skip): ",lineNo+1);
                        address[lineNo] = scanner.nextLine().trim();
                        if(address[lineNo].isEmpty())
                            break;
                    }

                    do{
                        postCode = 0;
                        try {
                            System.out.print("Enter a 5-digit postcode: ");
                            postCode = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            scanner.nextLine();
                        }finally{
                            if(postCode < 10000)    //if less than 5 digit
                                System.out.println("\u001B[31mInvalid postcode. It must be 5 DIGITS\u001B[0m");
                        }                         
                    }while(postCode < 10000);
                    
                    System.out.printf("\nDeliver to: %s\n",receipentName);
                    for (String addLine : address) {
                        if (addLine != null) {
                            System.out.println(addLine);
                        }
                        else
                            break;
                    }
                    System.out.println(postCode);
                    System.out.print("Is this ok? (y/n): ");
                    if(scanner.nextLine().trim().equalsIgnoreCase("y")){
                        finish = true;
                        return (new Delivery(Delivery.genTrackNo(), receipentName, postCode, address));
                    }
                } while(!finish);

        return null;
    }

    private static String formatString(String name){
        //To format the name of a product (with or w/o multiple words)
             String[] tempStr = name.split(" ");
                    name = ""; //reset the variable temporary, but set it later after formating
                    for(String str: tempStr){
                        if(!str.isEmpty()){
                            name += (str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + " ");
                        }
                    }
                    return name.trim();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\u001B[33mTracking No:\u001B[0m %s\n", trackNo));
        sb.append(String.format("\u001B[33mOrder No:\u001B[0m %s\n", order.getOrderId()));
        sb.append(String.format("\u001B[33mRecipient:\u001B[0m %s\n", name));
        sb.append("\u001B[33mAddress:\u001B[0m\n");
        for (String line : address) {
            if (line != null && !line.isEmpty()) {
                sb.append(line).append("\n");
            }
        }
        sb.append(String.format("\u001B[33mPostcode\u001B[0m: %d", postCode));
        return sb.toString();
    }
}
