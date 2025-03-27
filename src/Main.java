import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShippingSystem shippingSystem = new ShippingSystem(new ArrayList<>());
        StandartPackage sp1 = new StandartPackage("Deniz", "Beste", 6.3, "Yalova", "Türkiye", "İzmir", "2 Days");
        StandartPackage sp2 = new StandartPackage("Onur", "Defne", 3.4, "Venezia", "Italy", "Romania", "5 Days");
        StandartPackage sp3 = new StandartPackage("Mustafa", "Selda", 0.8, "Aydın", "Türkiye", "İstanbul", "3 Days");
        ExpressPackage ep1 = new ExpressPackage("Hakan", "Tunç", 10.2, "Vieanna", "Austria", "İstanbul", "14 Days", 15.0, 1);
        ExpressPackage ep2 = new ExpressPackage("Talha", "Osman", 11.0, "İzmir", "Türkiye", "Kütaya", "4 Days", 15.0, 2);
        FragilePackage fp1 = new FragilePackage("Emre", "Tolga", 4.5, "Manisa", "Türkiye", "Bulgaria", "5 Days", 15, 14, true, true);


        shippingSystem.addPackage(sp1);
        shippingSystem.addPackage(sp2);
        shippingSystem.addPackage(sp3);
        shippingSystem.addPackage(ep1);
        shippingSystem.addPackage(ep2);
        shippingSystem.addPackage(fp1);


        // Trackables
        for (Package p : shippingSystem.getPackages()) {
            if (p instanceof StandartPackage || p instanceof ExpressPackage || p instanceof FragilePackage){
                ((Trackable) p).updateLocation("Türkiye");
                ((Trackable) p).setEstimatedDeliveryTime("4 Days");
            }
        }
        for (Package p : shippingSystem.getPackages()) {
            if (p instanceof StandartPackage || p instanceof ExpressPackage || p instanceof FragilePackage){
                ((Trackable) p).getTrackingInfo();
                ((Trackable) p).getEstimatedDeliveryTime();
            }
        }

        // Insurables
        for (Package p : shippingSystem.getPackages()){
            if (p instanceof FragilePackage || p instanceof ExpressPackage){
                ((Insurable) p).insurePackage(10.0);
            }
        }
        // split into to for loops just to indicate the bottom one is optional
        for (Package p : shippingSystem.getPackages()){
            if (p instanceof FragilePackage || p instanceof ExpressPackage){
                ((Insurable) p).claimInsurance("damaged");
            }
        }

        System.out.println(" ");

        // Refundables
        for (Package p : shippingSystem.getPackages()) {
            if (p instanceof FragilePackage) {
                ((FragilePackage) p).requestFund("damaged");
                ((FragilePackage) p).logRefundRequest("sp1");
                //or

                System.out.println("Refund amount: "+((FragilePackage) p).getRefundAmount());
                System.out.println(" ");
            }
        }

        // Info printing
        shippingSystem.printAllPackages();


        // Report Genereting
        shippingSystem.generateReport();

        shippingSystem.getPackages().get(0).setDelivered(true);
        shippingSystem.getPackages().get(4).setDelivered(true);

        System.out.println(" ");

        //To see how this affects
        shippingSystem.generateReport();

    }
}
abstract class Package {
    private String senderName;
    private String  recipientName;
    private Double weight;
    private Boolean isDelivered;
    private String  destinationCity;
    private String  destinationCountry;


    public Package(){
        this.senderName="";
        this.recipientName="";
        this.weight=0.0;
        this.isDelivered=false;
        this.destinationCity="";
        this.destinationCountry="";
    }

    public Package(String senderName, String recipientName, Double weight, String destinationCity, String destinationCountry) {
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.weight = weight;
        this.isDelivered = false;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
    }
    public abstract double calculateShippingCost();

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(Boolean delivered) {
        this.isDelivered = delivered;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
    public void markDelivered(){
        setDelivered(true);
    }

    public void printInfo(){

        System.out.println("Sender: "+getSenderName());

        System.out.println("Recipient: "+getRecipientName());


        System.out.println("Weight of Package: "+getWeight());

        System.out.println("Destination Country and City: "+getDestinationCountry() + "/" + getDestinationCity());

        if (isDelivered==true) {

            System.out.println("Delivery: " + "Completed");

        } else {

            System.out.println("Delivery: " + "Incompleted");
        }

    }

}

class StandartPackage extends Package implements Trackable{
    private String shippingType;
    private String currentLocation;
    private String estimatedDeliveryTime;

    public StandartPackage(String senderName, String recipientName, Double weight, String destinationCity, String destinationCountry, String currentLocation, String estimatedDeliveryTime) {
        super(senderName, recipientName, weight, destinationCity, destinationCountry);
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.shippingType ="Ground";

    }


    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    @Override
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }
    @Override
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    @Override
    public double calculateShippingCost() {
        return (getWeight()*2.0);
    }

    @Override
    public void printInfo(){
        System.out.println("*****************************");
        System.out.println("Sender: "+getSenderName());

        System.out.println("Recipient: "+getRecipientName());


        System.out.println("Weight of Package: "+getWeight());

        System.out.println("Destination Country and City: "+getDestinationCountry() + "/" + getDestinationCity());

        if (isDelivered()==true) {

            System.out.println("Delivery: " + "Completed");

        } else {

            System.out.println("Delivery: " + "Incompleted");
        }
        System.out.println("Shipping Type: "+ shippingType);
        System.out.println("Current Location: "+ currentLocation);
        System.out.println("Estimated Delivery Time: "+ estimatedDeliveryTime);
        System.out.println("*****************************");
    }
    @Override
    public String getTrackingInfo(){
        return "Current Location is: "+ currentLocation + "\n" + "Estimated Delivery Time: "+ estimatedDeliveryTime;
    }

    @Override
    public void updateLocation(String newLocation){
        setCurrentLocation(newLocation);
    }

}

class ExpressPackage extends Package implements Trackable,Insurable {

    private int priorityLevel;
    private String currentLocation;
    private String estimatedDeliveryTime;
    private Double insuredValue;


    public ExpressPackage(String senderName, String recipientName, Double weight, String destinationCity, String destinationCountry, String currentLocation, String estimatedDeliveryTime, Double insuredValue, int priorityLevel) {
        super(senderName, recipientName, weight, destinationCity, destinationCountry);
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.insuredValue = insuredValue;
        this.priorityLevel = priorityLevel;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    @Override
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public double getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(Double insuredValue) {
        this.insuredValue = insuredValue;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @Override
    public double calculateShippingCost() {
        return (getWeight() * 5.0) + 10.0;
    }

    @Override
    public String getTrackingInfo() {
        return "Current Location is: " + currentLocation + "\n" + "Estimated Delivery Time: " + estimatedDeliveryTime;
    }

    @Override
    public void updateLocation(String newLocation) {
        setCurrentLocation(newLocation);
    }

    @Override
    public void insurePackage(double insuredValue) {
        if (insuredValue > 0) {
            this.insuredValue = insuredValue;
        } else {

        }
    }


    @Override
    public boolean claimInsurance(String claimReason) {
        if (insuredValue <= 0) {
            System.out.println("Package " + " is not insured. Claim denied.");
            return false;
        }
        if (!(claimReason.equalsIgnoreCase("Lost") ||
                claimReason.equalsIgnoreCase("Damaged") ||
                claimReason.equalsIgnoreCase("Expired") ||
                claimReason.equalsIgnoreCase("Broken"))) {
            System.out.println("Invalid claim reason. Allowed reasons: Lost, Damaged, Expired, Broken.");
            return false;
        }
        logInsuranceClaim("", claimReason);
        System.out.println("Insurance claim approved for package ");

        this.insuredValue = 0.0;
        return true;
    }


    @Override
    public void printInfo(){
        System.out.println("*****************************");
        System.out.println("Sender: "+getSenderName());

        System.out.println("Recipient: "+getRecipientName());


        System.out.println("Weight of Package: "+getWeight());

        System.out.println("Destination Country and City: "+getDestinationCountry() + "/" + getDestinationCity());

        if (isDelivered()==true) {

            System.out.println("Delivery: " + "Completed");

        } else {

            System.out.println("Delivery: " + "Incompleted");
        }
        System.out.println("Priority Level: "+ getPriorityLevel());
        System.out.println("Insured Value " + getInsuredValue());
        System.out.println("Current Location: "+ currentLocation);
        System.out.println("Estimated Delivery Time: "+ estimatedDeliveryTime);
        System.out.println("*****************************");
    }
}

class FragilePackage extends Package implements Trackable,Refundable,Insurable{
    private boolean requiresReinforcedBox;
    private boolean requiresTemperatureControl;
    private String currentLocation;
    private String estimatedDeliveryTime;
    private double insuredValue;
    private double refundAmount;

    public FragilePackage(String senderName, String recipientName, Double weight, String destinationCity, String destinationCountry, String currentLocation, String estimatedDeliveryTime, double insuredValue, double refundAmount, boolean requiresReinforcedBox, boolean requiresTemperatureControl) {
        super(senderName, recipientName, weight, destinationCity, destinationCountry);
        this.currentLocation = "";
        this.estimatedDeliveryTime = "";
        this.insuredValue = 0.0;
        this.refundAmount = 0.0;
        this.requiresReinforcedBox = requiresReinforcedBox;
        this.requiresTemperatureControl = requiresTemperatureControl;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    @Override
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public void setInsuredValue(double insuredValue) {
        this.insuredValue = insuredValue;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public boolean isRequiresReinforcedBox() {
        return requiresReinforcedBox;
    }

    public void setRequiresReinforcedBox(boolean requiresReinforcedBox) {
        this.requiresReinforcedBox = requiresReinforcedBox;
    }

    public boolean isRequiresTemperatureControl() {
        return requiresTemperatureControl;
    }

    public void setRequiresTemperatureControl(boolean requiresTemperatureControl) {
        this.requiresTemperatureControl = requiresTemperatureControl;
    }

    @Override
    public boolean requestFund(String reason){
        if (super.isDelivered()==true&& "damaged".equalsIgnoreCase(reason)){
            refundAmount=insuredValue*0.9;
            logRefundRequest("Package x");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void markDelivered(){
        super.markDelivered();
        System.out.println("Handle with care - Fragile item Delivered!");
    }

    @Override
    public double calculateShippingCost() {
        return getWeight()*2.0 + 8.0;
    }
    @Override
    public double getRefundAmount(){
        return refundAmount;
    }

    @Override
    public void insurePackage(double insuredValue) {
        if (insuredValue > 0) {
            this.insuredValue = insuredValue;

        } else {

        }
    }


    @Override
    public boolean claimInsurance(String claimReason) {
        if (insuredValue <= 0) {
            System.out.println("Package " + " is not insured. Claim denied.");
            return false;
        }

        if (!(claimReason.equalsIgnoreCase("Lost") ||
                claimReason.equalsIgnoreCase("Damaged") ||
                claimReason.equalsIgnoreCase("Expired") ||
                claimReason.equalsIgnoreCase("Broken"))) {
            System.out.println("Invalid claim reason. Allowed reasons: Lost, Damaged, Expired, Broken.");
            return false;
        }

        logInsuranceClaim("", claimReason);
        System.out.println("Insurance claim approved for package ");

        this.insuredValue = 0.0;
        return true;
    }
    @Override
    public double getInsuredValue() {
        return insuredValue;
    }
    @Override
    public String getTrackingInfo(){
        return "Current Location is: "+ currentLocation + "\n" + "Estimated Delivery Time: "+ estimatedDeliveryTime;
    }

    @Override
    public void updateLocation(String newLocation){
        setCurrentLocation(newLocation);
    }


    @Override
    public void printInfo(){
        System.out.println("*****************************");
        System.out.println("Sender: "+getSenderName());

        System.out.println("Recipient: "+getRecipientName());


        System.out.println("Weight of Package: "+getWeight());

        System.out.println("Destination Country and City: "+getDestinationCountry() + "/" + getDestinationCity());

        if (isDelivered()==true) {

            System.out.println("Delivery: " + "Completed");

        } else {

            System.out.println("Delivery: " + "Incompleted");
        }
        System.out.println("Requires ReinforcedBok: "+ requiresReinforcedBox);
        System.out.println("Requires Temperature Control: " + requiresTemperatureControl);
        System.out.println("Insured Value " + getInsuredValue());
        System.out.println("Current Location: "+ currentLocation);
        System.out.println("Estimated Delivery Time: "+ estimatedDeliveryTime);
        System.out.println("*****************************");
    }

}

interface Refundable {
    boolean requestFund(String reason);
    double getRefundAmount();

    default void logRefundRequest(String packageIdentifier){
        System.out.println("Refund request logged for package: " + packageIdentifier);
    }
}
interface Insurable {



    void insurePackage(double insuredValue);
    double getInsuredValue();
    boolean claimInsurance(String claimReason);

    default void logInsuranceClaim(String packageIdentifier, String reason){
        System.out.println("Insurance claim logged for package: " + packageIdentifier +
                " | Reason: " + reason);
    }

}
interface Trackable {

    String getTrackingInfo();
    void updateLocation(String newLocation);

    void setEstimatedDeliveryTime(String dateTime);
    String getEstimatedDeliveryTime();

}
class ShippingSystem {

    private List<Package> packages;


    public ShippingSystem(List<Package> packages){
        this.packages=new ArrayList<>();
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void  addPackage(Package p){

        if (packages.contains(p)) {
            System.out.println("Package Already exists");
        } else {
            packages.add(p);
        }
    }
    public void removePackage(Package p){
        if (packages.contains(p)) {
            packages.remove(p);
        }
    }
    public void printAllPackages(){
        for (Package p : packages) {
            p.printInfo();
            System.out.println(" ");
        }
    }



    public void howManyisDelivered(){
        int totalDelivered=0;
        for (Package p : packages ) {
            if (p.isDelivered()==true){
                totalDelivered++;
            }
        }
        System.out.println("Delivered packages: "+ totalDelivered);
    }
    public void averageShippingCost() {
        if (packages == null || packages.isEmpty()) { // Prevent division by zero
            System.out.println("No packages available to calculate average shipping cost.");
            return;
        }

        double totalShippingAmount = 0.0; // Use local variable
        for (Package p : packages) {
            totalShippingAmount += p.calculateShippingCost();
        }

        double avg = totalShippingAmount / packages.size(); // Safe division
        System.out.println("Average Shipping Cost is: " + avg);
    }

    public void generateReport(){
        System.out.println("Stored packages: "+packages.size());
        howManyisDelivered();
        averageShippingCost();
    }
}