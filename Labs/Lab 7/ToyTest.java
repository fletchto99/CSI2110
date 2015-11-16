
import java.util.ArrayList;

// ==========================================================================
// CSI2110 Lab code: Hash Tables
// ==========================================================================
// (C)opyright:
//
//   Lachlan Plant
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: lplant (Lachlan Plant)
// Email:   lplan053@uottawa.ca
// ==========================================================================
public class ToyTest {  
    
    public static void testAdd( HashTable<String,Integer> h){
        String[] s ={"the","quick","brown","fox","jumps"};
        for(String e: s){
            h.put(e, 1);
        }
        System.out.println("All Elements In: "+ (h.elements()==5));       
        
    }
    public static void testGet( HashTable<String,Integer> h){
        String[] s ={"the","quick","brown","fox","jumps"};
        int i = 0;
        for(String e: s){
            h.put(e, i);
            i++;
        }
        System.out.println("Brown Value is 2: "+ (h.get("brown")==2)); 
        System.out.println("jumps Value is 4: "+ (h.get("jumps")==4));   
        
    }
    public static void testAddOverWtite( HashTable<String,Integer> h){
        String[] s ={"the","quick","brown","fox","jumps"};
        int i = 0;
        for(String e: s){
            h.put(e, i);
            i++;
        }
        h.put("brown", 25);
        System.out.println("Brown Value is 25: "+ (h.get("brown")==25));     
        
    }
    
     public static void main(String[] args){
        int size = 11;
        ToyTest test = new ToyTest();
        System.out.println("Table Size: "+size);
        HashTable<String,Integer> linear = new ArrayHashTable<String,Integer>(size, new StringHash(),null, new LinearProbeStep());
        HashTable<String,Integer> quadratic = new ArrayHashTable<String,Integer>(size, new StringHash(), null,new QuadraticProbeStep()); 
        HashTable<String,Integer> doubleH = new ArrayHashTable<String,Integer>(size, new StringHash(), new ReverseStringHash(),new DoubleHashStep()); 
        HashTable<String,Integer> quadDoubleH = new ArrayHashTable<String,Integer>(size, new StringHash(), new ReverseStringHash(),new QuadraticDoubleHashStep()); 
        HashTable<String,Integer> chain = new ChainedHashTable<String,Integer>(size, new StringHash()); 
        System.out.println("Testing Linear Probing");
        testAdd(linear);
        testGet(linear);
        testAddOverWtite(linear);
        System.out.println();
        System.out.println("Testing Quadratic Probing");
        testAdd(quadratic);
        testGet(quadratic);
        testAddOverWtite(quadratic);
        System.out.println();
        System.out.println("Testing Double Hashing");
        testAdd(doubleH);
        testGet(doubleH);
        testAddOverWtite(doubleH);
        System.out.println();
        System.out.println("Testing Quadratic Double Hashing");
        testAdd(quadDoubleH);
        testGet(quadDoubleH);
        testAddOverWtite(quadDoubleH);
        System.out.println();
        System.out.println("Testing Chaining");
        testAdd(chain);
        testGet(chain);
        testAddOverWtite(chain);
    }
    
}
