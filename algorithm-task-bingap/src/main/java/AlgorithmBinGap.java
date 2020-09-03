/**
 * *  Created by Adewale Adeleye on 2020-09-01
 **/
public class AlgorithmBinGap {

    public static void main(String[] args){

    }
    public String convertIntegerToBinaryString(int i) {
        return Integer.toBinaryString(i);
    }

    public Integer getBinaryGap(int i) {
        String getBinaryValue = convertIntegerToBinaryString(i);
        int max =0;
        int n = getBinaryValue.length()-1;
        int counter = 0;
        int left = 0,right = 0;

        while (n>=right){
            if(getBinaryValue.charAt(left)==getBinaryValue.charAt(right)){
                left=right;
                right++;
                max = Math.max(max,counter);
                counter=0;
            }
            else if(getBinaryValue.charAt(left)!=getBinaryValue.charAt(right)){
                right++;
                counter++;
            }
        }
        //if left pointer never moved from the initial index, then it doesn't have a gap
        if(left<1){
            return 0;
        }

        // if the counter is greater than 0, then there's an outstanding gap value.
        if(counter>0){
            // if the current counter is more than the current max, set max as the minimum between counter and max.
            if(counter>max){
                max = Math.min(max,counter);
            }//Otherwise, set max as the maximum between max and current counter value.
            else{
                max = Math.max(max,counter);
            }
        }

        return max;
    }
}
