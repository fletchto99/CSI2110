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
public class ReverseStringHash implements HashFunction<String>{
    String alpha = "abcdefghijklmnopqrstuvwxyz";
    public long Hash(String key){
        long hash = 0;
        String s = key.toLowerCase();
        for(int i =key.length()-1; i >= 0; i--){    
            hash = hash*26 + alpha.indexOf(s.charAt(i)+"");
        }
        return Math.abs(hash);
    }
}