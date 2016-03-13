import java.util.Scanner;
public class HumanDriver{
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      System.out.println("What would you like the difficulty to be?\n" +
                        "Enter \"0\", \"1\", or \"2\", with 0 being the easiest.");
      int diff = sc.nextInt();
      SudokuGame game = new SudokuGame(diff);
      game.printBoard();
   }
}
