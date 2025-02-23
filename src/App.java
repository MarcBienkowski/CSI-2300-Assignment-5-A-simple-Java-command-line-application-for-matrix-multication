import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


public class App {
    public static ArrayList<ArrayList<Integer>> Matrix_Maker_From_File(String File_Path) {
        //creates 2D Matrix, ArrayList of ArrayList's (with integers as the data type)
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
        //creates an object (BR) to read the file
        //FileReader opens and reads the file, BufferedReader wraps the file to read the file in chunks allowing for the .readLine() method to read an entire line at once
        try(BufferedReader BR = new BufferedReader(new FileReader(File_Path))){
            String Line;
            //while loop continues to readline's sequentially until it cannot, in which it returns null
            while ((Line = BR.readLine()) != null) {
                //creates a String array "numbers" where it uses the read line "Line" as a string, where it then trims and splits any spaces assigning each split to an element in the array
                // \\s means any white space character and the + makes sure that multiple spaces are treated as a single delimiter
                String[] numbers = Line.trim().split("\\s+");

                //creates an ArrayList to store the integers in the read line
                ArrayList<Integer> row = new ArrayList<>();

                //for each String element num in the array numbers, it converts it to an integer and then adds it to the ArrayList row
                for(String num : numbers) {
                    row.add(Integer.parseInt(num));
                }
                //adds the array as a row, rather than an element due to matrix being ArrayList<ArrayList<Integer>> rather than a normal array
                matrix.add(row);
            }

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return matrix;
    }

    public static boolean Matrix_Validity_Checker(ArrayList<ArrayList<Integer>> Matrix_1, ArrayList<ArrayList<Integer>> Matrix_2){
        int Matrix_1_Columns = Matrix_1.get(0).size();
        int Matrix_2_Rows = Matrix_2.size();
        if(Matrix_1_Columns == Matrix_2_Rows){
            return true;
        }else{
            return false;
        }
    }
    
    public static ArrayList<ArrayList<Integer>> Matrix_Multiplier(ArrayList<ArrayList<Integer>> Matrix_1, ArrayList<ArrayList<Integer>> Matrix_2) {
        ArrayList<ArrayList<Integer>> Multiplied_Matrix = new ArrayList<ArrayList<Integer>>();
        
        int Matrix_1_Rows = Matrix_1.size();
        int Matrix_2_Columns = Matrix_2.get(0).size();
        int Matrix_1_Columns_in_Row = Matrix_1.get(0).size();
        
        //goes through each row in Matrix 1
        for(int row = 0; row < Matrix_1_Rows; row++){
            ArrayList<Integer> Calculated_Row = new ArrayList<Integer>();

            //goes over each column in matrix 2
            for(int column = 0; column < Matrix_2_Columns; column++){
                int Calculated_Index_Value = 0;
                //goes through rows of matrix 1 and columsn of matrix 2
                for(int row_element = 0; row_element < Matrix_1_Columns_in_Row; row_element++ ){
                    
                    
                    int Matrix_1_Index_Value = Matrix_1.get(row).get(row_element);
                    int Matrix_2_Index_Value = Matrix_2.get(row_element).get(column);

                    //calculations and adding value to row
                    Calculated_Index_Value += Matrix_1_Index_Value * Matrix_2_Index_Value;
                }       
                Calculated_Row.add(Calculated_Index_Value);
            }
            Multiplied_Matrix.add(Calculated_Row);
        }
        return Multiplied_Matrix;
    }
    
    public static void Matrix_To_Text_File(ArrayList<ArrayList<Integer>> Calculated_Matrix, String Calculated_Matrix_File_directory){
        try{
            FileWriter writer = new FileWriter(Calculated_Matrix_File_directory + File.separator + "matrix3.txt");
            for (ArrayList<Integer> row : Calculated_Matrix) {  
                for (Integer value : row) {  
                    writer.write(value + " ");  
                }  
                writer.write("\n");
            }
            writer.close();
        }catch(IOException e) {

        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to matrix multiplication app");
        System.out.println("I will take 2 matrices from a given file location and multiply them");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String File_Directory_1;
            String File_Name_1;
            String File_1_Path;
            String File_Directory_2;
            String File_Name_2;
            String File_2_Path;
        //Getting File 1
            while(true){
                //receiving file directory and file name to create file path
                System.out.println("please enter the directory of file 1");
                File_Directory_1 = scanner.nextLine();

                //attempt to open folder to see if its valid
                File folder = new File(File_Directory_1);
                if (!folder.isDirectory()){
                    System.out.println("EROR: Please enter a valid folder directory");
                    continue;
                }
                break;  
            }
            while(true){
                try{
                    //recieve file name and build file path
                    System.out.println("please enter the file name of file 1");
                    File_Name_1 = scanner.nextLine();
                    File_1_Path = File_Directory_1 + File.separator + File_Name_1;
    
                    //attempts to read file path
                    File file = new File(File_1_Path);
                    Scanner File_Scanner = new Scanner(file);
                    File_Scanner.close();
                    break;
                }catch(FileNotFoundException e){
                    System.out.println("EROR: Please enter a valid file name");
                    continue;
                    }
            }     
        //Getting File 2
            while(true){
                //receiving file directory and file name to create file path
                System.out.println("please enter the directory of file 2");
                File_Directory_2 = scanner.nextLine();

                //attempt to open folder to see if its valid
                File folder = new File(File_Directory_2);
                if (!folder.isDirectory()){
                    System.out.println("EROR: Please enter a valid folder directory");
                    continue;
                }
                break;  
            }



            while(true){
                try{
                    //recieve file name and build file path
                    System.out.println("please enter the file name of file 2");
                    File_Name_2 = scanner.nextLine();
                    File_2_Path = File_Directory_2 + File.separator + File_Name_2;
    
                    //attempts to read file path
                    File file = new File(File_2_Path);
                    Scanner File_Scanner = new Scanner(file);
                    File_Scanner.close();
                    break;
                }catch(FileNotFoundException e){
                    System.out.println("EROR: Please enter a valid file name");
                    continue;
                    }
            }      
            ArrayList<ArrayList<Integer>> File_1_Matrix = Matrix_Maker_From_File(File_1_Path);
            ArrayList<ArrayList<Integer>> File_2_Matrix = Matrix_Maker_From_File(File_2_Path);

            ArrayList<ArrayList<Integer>> Calculated_Matrix = new ArrayList<ArrayList<Integer>>();
            if(Matrix_Validity_Checker(File_1_Matrix, File_2_Matrix)){
                Calculated_Matrix = Matrix_Multiplier(File_1_Matrix, File_2_Matrix);
            }else {
                System.out.println("Error, the 2 entered matrices are not valid to be multiplied");
                System.out.println("Please try again" + "\n");
                continue;
            }
            
            String Calculated_Matrix_File_directory;
            while(true){
                //receiving file directory and file name to create file path
                System.out.println("please enter the file directory of where you would like the calculated Matrix stored");
                Calculated_Matrix_File_directory = scanner.nextLine();

                //attempt to open folder to see if its valid
                File folder = new File(File_Directory_1);
                if (!folder.isDirectory()){
                    System.out.println("EROR: Please enter a valid folder directory");
                    continue;
                }
                break;
            }
            Matrix_To_Text_File(Calculated_Matrix, Calculated_Matrix_File_directory);
            String Scanner_Response = null;
            while(true){
                System.out.println("Type in \"continue\" to restart the program or type in \"exit\" to close the program");
                Scanner_Response = scanner.nextLine();
                switch(Scanner_Response){
                    case "exit":
                        scanner.close();
                        System.exit(0);
                    case "continue":
                        break;
                    default:
                        System.out.println("Please enter a valid key word");
                        continue;
                }
                break;
        }
    }
}
}