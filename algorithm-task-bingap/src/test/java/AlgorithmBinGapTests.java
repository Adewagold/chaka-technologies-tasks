
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

/**
 * *  Created by Adewale Adeleye on 2020-09-01
 **/

public class AlgorithmBinGapTests {
    AlgorithmBinGap algorithmBinGap = new AlgorithmBinGap();

    @Test
    public void convertIntegerToBinary(){
        String binary = algorithmBinGap.convertIntegerToBinaryString(5);
        Assert.assertEquals("101",binary);
    }

    @Test
    public void getBinaryGapForNumberNine(){
        Integer binary = algorithmBinGap.getBinaryGap(9);
        Assertions.assertEquals(2,binary);
    }

    @Test
    public void getBinaryGapForNumberFiveTwentyNine(){
        Integer binary = algorithmBinGap.getBinaryGap(529);
        Assertions.assertEquals(4,binary);
    }

    @Test
    public void getBinaryGapForNumberTwenty(){
        Integer binary = algorithmBinGap.getBinaryGap(20);
        Assertions.assertEquals(1,binary);
    }

    @Test
    public void getBinaryGapForNumberFifteen(){
        Integer binary = algorithmBinGap.getBinaryGap(15);
        Assertions.assertEquals(0, binary);
    }

    @Test
    public void getBinaryGapForNumberThirtyTwo(){
        Integer binary = algorithmBinGap.getBinaryGap(32);
        Assertions.assertEquals(0,binary);
    }

    @Test
    public void getBinaryGapForNumberOneTHousandAndFortyOne(){
        Integer binary = algorithmBinGap.getBinaryGap(1041);
        Assertions.assertEquals(5,binary);
    }

    @Test
    public void getBinaryGapForLargeNumbers(){
        Integer binary = algorithmBinGap.getBinaryGap(66551);
        Assertions.assertEquals(6,binary);
    }


    @Test
    public void getBinaryGapForNumbersWithTrailingZeros(){

        Integer binary = algorithmBinGap.getBinaryGap(328);
        Assertions.assertEquals(2,binary);
    }
}
