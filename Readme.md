# Chaka Technoogies Take Home Tasks

The solution to the tasks (transactions and algorithm) was built as a multi-module project using spring and maven. 
##### Language: Java 8
##### Framework: SpringBoot <version>2.3.3.RELEASE</version>, Junit 4, Mockmvc, Maven

### Run project with tests

```
* mvn clean install and mvn clean integration-test *
```

## Task 1: Transaction Statistics
## Specs
````
POST /transactions
````
This endpoint is called to create a new transaction. It MUST execute in constant time and
memory (O(1)).

Body:
```
{
    "amount":"12.3343",
    "timestamp":"2018-07-17T09:59:51.312Z"
}
```

Where:
````
amount – transaction amount; a string of arbitrary length that is parsable as a BigDecimal
timestamp – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the UTC timezone (this is not the current timestamp)
````
Returns: Empty body with either 201 or 204.
````
201 – in case of success
204 – if the transaction is older than 60 seconds
400 – if the JSON is invalid
422 – if any of the fields are not parsable or the transaction date is in the future
````

````
GET /statistics
````
This returns statistics of last 60 seconds transactions.
```
Response:
{
  "sum": "1000.00",
  "avg": "100.53",
  "max": "200000.49",
  "min": "50.23",
  "count": 10
}
```

```
Where:

sum – a BigDecimal specifying the total sum of transaction value in the last 60 seconds
avg – a BigDecimal specifying the average amount of transaction value in the last 60 seconds
max – a BigDecimal specifying single highest transaction value in the last 60 seconds
min – a BigDecimal specifying single lowest transaction value in the last 60 seconds
count – a long specifying the total number of transactions that happened in the last 60 seconds
All BigDecimal values always contain exactly two decimal places and use `HALF_ROUND_UP` rounding. eg: 10.345 is returned as 10.35, 10.8 is returned as 10.80


```


````
DELETE /transactions
````

```
This endpoint causes all existing transactions to be deleted

The endpoint should accept an empty request body and return a 204 status code.
```

## Task 2: Binary Gap
A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is
surrounded by ones at both ends in the binary representation of N.

Solution can be found in module **algorithm-task**

Directory to solution: **algorithim-task/src/main/java/AlgorithmBinGap.java**

Directory to test cases: **algorithim-task/src/test/java/AlgorithmBinGapTests.java**

To run unit tests
```$xslt
> mvn test -Dtest=AlgorithmBinGapTests
```


```AlgorithmBinGap algorithmBinGap = new AlgorithmBinGap();```

**Sample Test 1:** Test integer to binary conversion.
```$xslt
    @Test
    public void convertIntegerToBinary(){
        String binary = algorithmBinGap.convertIntegerToBinaryString(5);
        Assert.assertEquals("101",binary);
    }
```

**Sample Test 2:** Binary Gap for Number 9.
```$xslt
@Test
    public void getBinaryGapForNumberNine(){
        Integer binary = algorithmBinGap.getBinaryGap(9);
        Assertions.assertEquals(2,binary);
    }

```

**Sample Test 3:** Binary Gap for Number 25.
```$xslt
    @Test
    public void getBinaryGapForNumberFiveTwentyNine(){
        Integer binary = algorithmBinGap.getBinaryGap(529);
        Assertions.assertEquals(4,binary);
    }
```

**Sample Test 4:** Binary Gap for Number 20. 
```$xslt
@Test
    public void getBinaryGapForNumberTwenty(){
        Integer binary = algorithmBinGap.getBinaryGap(20);
        Assertions.assertEquals(1,binary);
    }
```

**Sample Test 5:** Binary Gap for Number 15. 
```$xslt
@Test
    public void getBinaryGapForNumberFifteen(){
        Integer binary = algorithmBinGap.getBinaryGap(15);
        Assertions.assertEquals(0, binary);
    }
```
    
**Sample Test 6:** Binary Gap for Number 32. 
```$xslt
@Test
    public void getBinaryGapForNumberThirtyTwo(){
        Integer binary = algorithmBinGap.getBinaryGap(32);
        Assertions.assertEquals(0,binary);
    }
```

**Sample Test 7:** Binary Gap for Number 1041. 
```$xslt
    @Test
    public void getBinaryGapForNumberOneTHousandAndFortyOne(){
        Integer binary = algorithmBinGap.getBinaryGap(1041);
        Assertions.assertEquals(5,binary);
    }
```
    

**Sample Test 8:** Binary Gap for Number 32. 
```$xslt
    @Test
    public void getBinaryGapForLargeNumbers(){
        Integer binary = algorithmBinGap.getBinaryGap(66551);
        Assertions.assertEquals(6,binary);
    }
```
    

**Sample Test 9:** Binary Gap for Number 32. 
```$xslt
    @Test
    public void getBinaryGapForNumbersWithTrailingZeros(){
        Integer binary = algorithmBinGap.getBinaryGap(328);
        Assertions.assertEquals(2,binary);
    }
```

