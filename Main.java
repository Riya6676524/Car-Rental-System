import java.util.*;

class car{

    private String carId;
    private String brand;
    private String model;
    private double basepriceperday;
    private boolean isAvailable;
    
    /*Add constructor to initialize the object; when we pass the value at the time 
    of object cration..*/

    public car(String carId,String brand,String model,double basepriceperday){
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basepriceperday=basepriceperday;
        this.isAvailable=true;
    }
    
    /*AS we create the variables as private so we have to use getter and setter 
    to update and retrive the values of variables*/

    public String getcarId(){
        return carId;
    }

    public String getbrand(){
        return brand;
    }

    public String getmodel(){
        return model;
    }
    
    //calculate the total price =total no. of days to rent the car * base price per day of renting the car
    
    public double calprice(int daysforrent){
        return basepriceperday*daysforrent;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    //if car is on rent then car is not available for rent so isAvailable is false

    public void rent(){
        isAvailable=false;
    }

    //if car is returned then car is available for rent so isAvailable is true
    public void returncar(){
        isAvailable=true;
    }
}

class customer{

    private String customerId;
    private String Name;
    
    public customer(String customerId,String Name){
        this.customerId=customerId;
        this.Name=Name;
    }

    public String getCustId(){
        return customerId;
    }

    public String getName(){
        return Name;
    }
}

class Rental{

    //here (cardetail) has datatype car as car is a class name which contain the car details
    private car cardetail;
    private customer customerdetail;
    private int days;

    public Rental(car cardetail,customer customerdetail,int days){
        this.cardetail=cardetail;
        this.customerdetail=customerdetail;
        this.days=days;
    }

    public car getcardetails(){
        return cardetail;
    }

    public customer getcustomerdetail(){
        return customerdetail;
    }

    public int getdays(){
        return days;
    }
}

class carRentalSystem{

    private List<car>cars;
    private List<customer>customers;
    private List<Rental>rentals;

    public carRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }

    public void addCar(car cardl){
        cars.add(cardl);
    }

    public void addcustomer(customer customerdl){
        customers.add(customerdl);
    }

    public void rentcar(car cardl,customer customerdl,int days){
        if(cardl.isAvailable()){
            cardl.rent();

            //when you create an object of a class, you must use the new keyword.
            //Rental(cars, customers, days) looks like a function call, but Rental is a class, not a method.
            //Java expects new Rental(...) to properly allocate memory and initialize the object.
            //that is why we use new Rental().....

            rentals.add(new Rental(cardl,customerdl,days));
        }
        else{
            System.out.println("Car is Not Available for rent");
        }
    }

    public void returncar(car cardl){
        cardl.returncar();
        Rental removerentalcar=null;
        for(Rental i:rentals){
            if(i.getcardetails()==cardl){
                removerentalcar=i;
                break;
            }
        }
        if(removerentalcar!=null){
            rentals.remove(removerentalcar);
            System.out.println("car rturned Successfully");
        }
        else{
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner sc=new Scanner(System.in);

        while(true){
            System.out.println("**********Car Rental System**********");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");

            int choice=sc.nextInt();
            sc.nextLine();

            if(choice==1){
                System.out.println("--Rent a car--");
                System.out.print("Enter your name: ");
                String customername= sc.nextLine();

                System.out.println("Available Cars:");
                for(car i:cars){
                    if(i.isAvailable()){
                        System.out.println(i.getcarId()+" "+i.getbrand()+" "+i.getmodel());
                    }
                }
                System.out.println("Enter the car ID you wants to rent");
                String carId=sc.nextLine();

                System.out.println("Enter the number of days you wants to rent the car");
                int days=sc.nextInt();
                sc.nextLine();

                customer newcustomer=new customer("CUS"+customers.size()+1,customername);
                //in carrentalsysytem addcustomer method is their
                addcustomer(newcustomer);

                car selectedcar=null;
                for(car i:cars){
                    if(i.getcarId().equals(carId) && i.isAvailable()){
                        selectedcar=i;
                        break;
                    }
                }
                if(selectedcar!=null){
                    double totalprice=selectedcar.calprice(days);
                    System.out.println("Rental Information");
                    System.out.println("customer ID: "+ newcustomer.getCustId());
                    System.out.println("customer name: "+ newcustomer.getName());
                    System.out.println("Car: " + selectedcar.getbrand()+" "+selectedcar.getmodel());
                    System.out.println("Rental Days: "+days);
                    System.out.println("Total Price: "+totalprice);

                    System.out.println("Confirm Rental of car(Y/N): ");
                    String confirm=sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentcar(selectedcar, newcustomer, days);
                    System.out.println("Car Rented Successfully");
                    }
                    else{
                    System.out.println("Rental Cancle");
                    }
                }
                else{
                System.out.println("Invalid car selection or caris not available for rent");
                }
            }
            else if(choice==2){
                System.out.println("--Return car--");
                System.out.print("Enter the car ID you wants to return: ");
                String carId=sc.nextLine();

                car returnrentedCar=null;
                for(car i:cars){
                    if(i.getcarId().equals(carId) && !i.isAvailable()){
                        returnrentedCar=i;
                        break;
                    }
                }
                if(returnrentedCar!=null){
                    customer customer=null;
                    for(Rental i:rentals){
                        if(i.getcardetails()==returnrentedCar){
                            customer=i.getcustomerdetail();
                            break;
                        }
                    }
                    if(customer!=null){
                        returncar(returnrentedCar);
                        System.out.println("Car Returned Successfully by: "+customer.getName());
                    }
                    else{
                        System.out.println("Car was not rented information is missing");
                    }
                }
                else{
                    System.out.println("Invalid car ID or car is not rented");
                }
            }
            else if(choice ==3){
                break;
            }
            else{
                System.out.println("Invalid choice please enter correct choice");
            }
        }
        System.out.println("Thankyou for using Car Rental System");
    }
}
public class Main{

    public static void main(String[]args){
        carRentalSystem rentalsystem=new carRentalSystem();

        car car1=new car("C001","Toyota","Camry",60.0);
        car car2=new car("C002","Honda","Accord",70);
        car car3=new car("C003","Mahindra","Thar",150);
        car car4=new car("C004","Ford","Tierra",166);
        car car5=new car("C005","Suzuki","Solio",88);
        car car6=new car("C006","BMW","Crossover",66);
        rentalsystem.addCar(car1);
        rentalsystem.addCar(car2);
        rentalsystem.addCar(car3);
        rentalsystem.addCar(car4);
        rentalsystem.addCar(car5);
        rentalsystem.addCar(car6);

        rentalsystem.menu();
    }
}
