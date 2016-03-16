import java.util.Arrays;
public class testPlethora{
   public static void main(String[] args){
      long dumb =0;
      long smart=0;
      int dif =0;
      int difSum = 0;
      int[][] temp = new int[9][9];
      int[][] temp2 = new int[9][9];
      long t1=0;
      long t2 =0;
      for(int i =0; i < 1000; i++){
         dif =((int) (Math.random()*3));
         difSum+=dif;
         temp = SudokuGame.generateBoard(dif);
         for(int j = 0; j < 9; j++){
            temp2[j] = Arrays.copyOf(temp[j], 9);
         }
         t1 = System.currentTimeMillis();
         temp = SmartAi.solve(temp);
         t2 = System.currentTimeMillis();
         smart += (t2 - t1);
         temp2 = DumbAi.solve(temp2);
         t1 = System.currentTimeMillis();
         dumb += (t1 - t2);
         System.out.println(i);
      }
      dumb = dumb/1000;
      smart = smart/1000;
      difSum = difSum/1000;
      System.out.println("Average Diffuculty: " + difSum);
      System.out.println("Average time for Smart Ai: " + smart + " milliseconds");
      System.out.println("Average time for Dumb Ai: " + dumb + " milliseconds");
   }
}
