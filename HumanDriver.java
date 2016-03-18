/**
 *The driver for a human who wishes to play Sudoku.
 *
 *@author Chris Varanese
 *@version Program 7
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class HumanDriver{
   private static final Scanner sc = new Scanner(System.in);
   /**
    *Plays the game.
    */
   public static void runHumanGame(){
      boolean read = getUserYesOrNo("Would you like to read from a file?(y/n)\n" +
                                    "This will read from the file \"gameBoard.txt\"");
      SudokuGame game = null;
      File file;
      if (read){
         file = new File("gameBoard.txt");
         if (file.canRead()){
            int[][] gameNums = new int[9][9];
            boolean[][] gameChange = new boolean[9][9];
            try{
               FileReader reader = new FileReader(file);
               BufferedReader buff = new BufferedReader(reader);
               int temp =0;
               for(int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j++){
                        temp = buff.read() - 48;
                        if(temp < 0 || temp > 9){
                           throw new IOException();
                        }
                        gameNums[i][j] = temp;
                  }
               }
               for(int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j ++){
                     int boo = buff.read() - 48;
                     if (boo == 0){
                        gameChange[i][j] = false;
                     } else if (boo == 1){
                        gameChange[i][j] = true;
                     }  else {
                        throw new IOException();
                     }
                  }
               }
            } catch (FileNotFoundException ex){
               System.out.println("Could not open file");
               System.exit(1);
            } catch (IOException ex){
               System.out.println("Error reading file");
               System.exit(1);
            }
            game = new SudokuGame(gameNums);
            for (int i = 0; i < 9; i ++){
               for (int j = 0; j < 9; j ++){
                  game.setChangeable(i, j, gameChange[i][j]);
               }
            }
         }
         else {
            System.out.println("Cannot read file.");
         }
      }
      if (!read){
         System.out.println("What would you like the difficulty to be?\n" +
                     "Enter \"0\", \"1\", or \"2\", with 0 being the easiest.");
         int diff = getUserInteger(0, 2);
         game = new SudokuGame(diff);
      }
      System.out.println("Your game is now starting. You may quit out at any time, " +
                        "and your board will be saved to the document \"gameBoard.txt\"");
      boolean solved = false;
      while (!solved){
         game.printBoard();
         System.out.println("Enter the row of the element you wish to change");
         int row = getUserInteger(0, 8);
         System.out.println("Enter the column of the element you wish to change");
         int column = getUserInteger(0, 8);
         while (!game.getChangeable(column, row)){
            System.out.println("That entry is not changeable. Please Enter new values.");
            System.out.println("Row:");
            row = getUserInteger(0, 8);
            System.out.println("Column:");
            column = getUserInteger(0, 8);
         }
         System.out.println("Enter the number you wish to enter (0 - 9)");
         int replace = getUserInteger(0, 9);
         game.set(column, row, replace);
         boolean finished = true;
         for (int i = 0; i < 9; i ++){
            for (int j = 0; j < 9; j ++){
               if (game.get(i,j) == 0){
                  finished = false;
                  break;
               }
            }
            if (!finished){
               break;
            }
         }
         if (finished){
            System.out.println("Your board is filled!");
            solved = game.checkBoardSolved();
            if (!solved){
               System.out.println("But, it's not solved yet!");
            }
         }
         File write = new File("gameBoard.txt");
         if (write.exists()){
            write.delete();
         }
         try{
            write.createNewFile();
         } catch(IOException ex){
            System.out.println("Cannot create new file.");
         }
         PrintStream strim;
         try{
            strim = new PrintStream(write);
         } catch (FileNotFoundException ex){
            strim = null;
            System.out.println("Could not create print stream");
         }
         if (write.canWrite()){
            for (int i = 0; i < 9; i ++){
               for (int j = 0; j < 9; j ++){
                  if (strim != null){
                     strim.print(game.get(i,j));
                  }
               }
            }
            for (int i = 0; i < 9; i ++){
               for (int j = 0; j < 9; j ++){
                  if (strim != null){
                     if (game.getChangeable(i,j)){
                        strim.print(1);
                     } else{
                        strim.print(0);
                     }
                  }
               }
            }
         }
         write.setReadOnly();
      }
      if (solved){
         System.out.println("Congratulations! You did it!");
      }
   }

   private static int getUserInteger(int min, int max){
      System.out.println("Enter a number between " + min + " and " + max + ":");
      int next = 0;
      boolean acceptedInput = false;
      while (!acceptedInput){
         try{
            next = Integer.parseInt(sc.nextLine());
            if (next >= min && next <= max){
               acceptedInput = true;
            } else {
               System.out.println("Between " + min + " and " + max + " please.");
            }
         } catch(NumberFormatException ex){
            System.out.println("Between " + min + " and " + max + " please.");
         }
      }
      return next;
   }
   private static boolean getUserYesOrNo(String str){
      System.out.println(str);
      boolean next = false;
      boolean acceptedInput = false;
      while (!acceptedInput){
         try{
            String response = sc.nextLine();
            if (response.equals("y")){
               next = true;
               acceptedInput = true;
            } else if (response.equals("n")){
               next = false;
               acceptedInput = true;
            } else{
               System.out.println("Please enter \"y\" or \"n\"");
            }
         } catch(NumberFormatException ex){
            System.out.println("Please enter \"y\" or \"n\"");
         }
      }
      return next;
   }
}
