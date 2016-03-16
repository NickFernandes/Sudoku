import java.util.Scanner;
public class HumanDriver{
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      System.out.println("What would you like the difficulty to be?\n" +
                        "Enter \"0\", \"1\", or \"2\", with 0 being the easiest.");
      int diff = sc.nextInt();
      SudokuGame game = new SudokuGame(diff);
      game.printBoard();
      boolean solved = false;
      while (!solved){
         System.out.println("Enter the row of the element you wish to change");
         int row = sc.nextInt();
         System.out.println("Enter the column of the element you wish to change");
         int column = sc.nextInt();
         while (!game.getChangeable(row,column)){
            System.out.println("That entry is not changeable. Please Enter new values.");
            System.out.println("Row:");
            row = sc.nextInt();
            System.out.println("Column:");
            column = sc.nextInt();
         }
         System.out.println("Enter the number you wish to enter (1 - 9)");
         int replace = sc.nextInt();
         while (replace < 1 || replace > 9){
            System.out.println("The number must be between 1 and 9");
            replace = sc.nextInt();
         }
         game.set(row, column, replace);
         boolean finished = true;
         for (int i = 0; i < 9; i ++){
            for (int j = 0; j < 9; j ++){
               if (game.get(i,j) == 0){
                  finished = false;
                  break;
               }
            }
            if (finished){
               break;
            }
         }
         if (finished){
            System.out.println("Your board is complete!");
            //check it
            if (!solved){
               System.out.println("It's not done yet!");
            }
         }
      }
      System.out.println("Congratulations! You did it!");
   }
}
