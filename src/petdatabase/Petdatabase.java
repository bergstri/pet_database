
package petdatabase;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 *
 * @author ianbergstrom
 */

class Pet implements Serializable{
    private String name;
    private int age;

    /**
     * @return the name
     */
    public Pet(){
        //empty constructor
    }
    public Pet(String name, int age){
        this.name=name;
        this.age=age;
    }
    //added constructor to make it easier on reading data from file
    
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

//add toString method??
}

class Menu{
    
    public void getMenu(){
        System.out.print(
"What would you like to do?\n" +
" 1) View all pets\n" +
" 2) Add more pets\n" +
" 3) Update an existing pet\n" +
" 4) Remove an existing pet\n" +
" 5) Search pets by name\n" +
" 6) Search pets by age\n" +
" 7) Exit program\n");
    }
    
    public void displayPrompt(){
        
        System.out.print("\nYour choice: ");
    }
}

public class Petdatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Scanner input=new Scanner(System.in);
        Menu menu=new Menu();
        boolean exitCondition=false;
        
        System.out.println("Pet Database Program\n"); //welcome message
        
        ArrayList<Pet> petArray = new ArrayList<Pet>();
        
        File file=new File("petData.ser");
        file.createNewFile(); //create a file if one does not exist already
        Scanner fileScan=new Scanner(file);
        
            while(fileScan.hasNextLine()){
                petArray.add(new Pet(fileScan.next(), fileScan.nextInt()));
                //add any pets from the text file to the array 
            }
        
        while(!exitCondition){
            if(petArray.size()>5) System.out.println("Caution- file has been modified and there are more entries than supported");
            //redundancy to display message if the file has been altered to have more than 5 entries
            menu.getMenu();
            menu.displayPrompt();
            int choice= input.nextInt();
            System.out.println();
            
            if(choice==1){
                System.out.println("+-----------------------+");
                System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',"ID",'|', "NAME",'|',"AGE",'|');
                System.out.println("+-----------------------+");
                for(int i=0; i<petArray.size();i++){
                    System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',i,'|', petArray.get(i).getName(),'|',petArray.get(i).getAge(),'|');
                }
                System.out.println("+-----------------------+");
                System.out.println(petArray.size()+" rows in a set. \n");
                
            }
            
            else if(choice==2){
                int addedPets=0;
                //counter for number of pets added by the current operation
                System.out.println("Type done when you are finished adding pets"); 
                //added this line for clearer instructions
                while(true){ //infinite loop to be exited by a break when user types "done"
                System.out.print("add pet (name, age): ");
                String petName=input.next();
                if(petName.equalsIgnoreCase("done")){
                    System.out.println(addedPets+" pet(s) added.\n"); //added new line character for spacing
                    
                    break;
                }else{
                   
                    if(petArray.size()>=5){
                        System.out.print("Error: Database is Full.\n\n");
                        input.next(); //this is sp00ky_gh0st (needed to eat up the extra input so
                        //the program will not loop around without asking the user for input first)
                        break;
                    }
                    Pet pet=new Pet();
                    pet.setName(petName);
                    int petAge=input.nextInt();

                    if(petAge>20 || petAge<0){
                        System.out.println("Error: " +petAge+" is not a valid age.");
                        //if the user attempts to enter an age greater than 20, prompt them for a different age
                    }
                    else{
                    pet.setAge(petAge);
                    
                    petArray.add(pet);
                    addedPets++;
                    }
                   
                }
                }
            }
            else if(choice==3){
                
                System.out.println("+-----------------------+");
                System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',"ID",'|', "NAME",'|',"AGE",'|');
                System.out.println("+-----------------------+");
                for(int i=0; i<petArray.size();i++){
                    System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',i,'|', petArray.get(i).getName(),'|',petArray.get(i).getAge(),'|');
                }
                System.out.println("+-----------------------+");
                System.out.println(petArray.size()+" rows in a set. \n");
                
                System.out.print("Enter the pet ID you want to update: ");
                int petId=input.nextInt();
                if(petId>petArray.size()-1 || petId<0){
                    System.out.println("Error: ID "+petId+" does not exist.\n");
                    //check to make sure the user inputs a valid array ID
                }else{
                Pet pet=petArray.get(petId); //retrieve the pet object associated with the searched ID from the Arraylist
                String originalName=pet.getName();
                int originalAge=pet.getAge();
                System.out.print("Enter new name and new age: ");
                pet.setName(input.next());
                pet.setAge(input.nextInt());
                System.out.println(originalName +" " +originalAge+" changed to "+pet.getName()+" "+pet.getAge()+".\n");
                
            }
            }
            
            else if(choice==4){
                
                 System.out.println("+-----------------------+");
                System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',"ID",'|', "NAME",'|',"AGE",'|');
                System.out.println("+-----------------------+");
                for(int i=0; i<petArray.size();i++){
                    System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',i,'|', petArray.get(i).getName(),'|',petArray.get(i).getAge(),'|');
                }
                System.out.println("+-----------------------+");
                System.out.println(petArray.size()+" rows in a set. \n");
                System.out.print("Enter the pet ID to remove: ");
                
                int petId=input.nextInt();
                if(petId>petArray.size()-1 || petId<0){
                    System.out.println("Error: ID "+petId+" does not exist.\n");
                    //check to make sure the user inputs a valid array ID
                }else{
                Pet pet=petArray.get(petId);
                //retrieve the pet object associated with the searched ID from the Arraylist so we can
                //pull the name and age and store them localy before removing the object from the Arraylist
                String petName=pet.getName();
                int petAge=pet.getAge();
                petArray.remove(petArray.get(petId));
                    System.out.println(petName+" "+petAge+" is removed.\n");
            }
            }
            
            else if(choice==5){ //allows for the searching of pet by name
                System.out.print("Enter a name to search: ");
                String nameSearch=input.next();
                
                System.out.println("+-----------------------+");
                System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',"ID",'|', "NAME",'|',"AGE",'|');
                System.out.println("+-----------------------+");
                for(int i=0; i<petArray.size();i++){
                    if(petArray.get(i).getName().equalsIgnoreCase(nameSearch)){
                        //if the name of the Pet object at position i in the Arraylist matches the searched name, print it
                    System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',i,'|', petArray.get(i).getName(),'|',petArray.get(i).getAge(),'|');
                }
                }
                System.out.println("+-----------------------+");
                System.out.println(petArray.size()+" rows in a set. \n");
            }
            
            else if(choice==6){ //allows for the searching of pet by age
                System.out.print("Enter age to search: ");
                int ageSearch=input.nextInt();
                
                System.out.println("+-----------------------+");
                System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',"ID",'|', "NAME",'|',"AGE",'|');
                System.out.println("+-----------------------+");
                for(int i=0; i<petArray.size();i++){
                    if(petArray.get(i).getAge()==(ageSearch)){
                        //if the Pet object at position i in the Arraylist matches the searched age, print it
                    System.out.printf("%c %-2s %c %-10s %c %3s %c\n",'|',i,'|', petArray.get(i).getName(),'|',petArray.get(i).getAge(),'|');
                }
                }
                System.out.println("+-----------------------+");
                System.out.println(petArray.size()+" rows in a set. \n");
            }
            
            else if(choice==7){
                System.out.println("Goodbye!");
                //when the user exits, write the Pet objects to petData.ser so the user can continue later
                //by loading the previous data from the file
                try{
                        FileWriter fWrite;   
                        fWrite = new FileWriter("petData.ser");
                        PrintWriter pWrite= new PrintWriter(fWrite);
                        for(int i=0; i<petArray.size();i++){
                            
                            if(!(i==(petArray.size()-1))){
                                pWrite.print(petArray.get(i).getName()+" "+petArray.get(i).getAge()+"\n");
                                //only print a new line if it is not the last entry to prevent an empty line 
                                //at the end of the file that screws with the file reading
                            }
                            else{
                                pWrite.print(petArray.get(i).getName()+" "+petArray.get(i).getAge());
                            }
                        }
                        
                        pWrite.close();
                            }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                exitCondition=true;
            }
        }
        
    }
}
