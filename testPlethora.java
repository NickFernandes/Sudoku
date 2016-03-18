import java.util.Arrays;
public class testPlethora{
   public static void main(String[] args){
      System.out.println("Running 1000 tests");
      double dumb =0;
      double smart=0;
      int dif =0;
      double difSum = 0;
      int[][] temp = new int[9][9];
      int[][] temp2 = new int[9][9];
      double t1=0;
      double t2 =0;
      SmartAi s = new SmartAi();
      DumbAi d = new DumbAi();
      for(int i =0; i < 1000; i++){
         dif =((int) (Math.random()*3));
         difSum+=dif;
         temp = SudokuGame.generateBoard(dif);
         for(int j = 0; j < 9; j++){
            temp2[j] = Arrays.copyOf(temp[j], 9);
         }
         t1 = System.nanoTime()/1000000;
         temp = s.solve(temp);
         t2 = System.nanoTime()/1000000;
         smart += (t2 - t1);
         temp2 = d.solve(temp2);
         t1 = System.nanoTime()/1000000;
         dumb += (t1 - t2);
         System.out.println("Test Number: " + (i +1));
      }
      dumb = dumb/1000;
      smart = smart/1000;
      difSum = difSum/1000;
      System.out.println("Average Diffuculty: " + difSum);
      System.out.println("Average time for Smart Ai: " + smart + " milliseconds");
      System.out.println("Average time for Dumb Ai: " + dumb + " milliseconds");
   }
}
