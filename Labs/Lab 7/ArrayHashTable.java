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
import java.util.*;
public class ArrayHashTable<K,V> implements HashTable<K,V> {
    HashFunction<K> func1;
    HashFunction<K> func2;
    int size;
    int totalNumProbes;
    int elements;
    private ArrayList<Entry<K,V>> table;
    SteppingFunction step;
    
    
    public ArrayHashTable(int s, HashFunction<K> h1, HashFunction<K> h2, SteppingFunction st){
        elements = totalNumProbes =0;
        size = s;
        table = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            table.add(null);
        }
        func1 = h1;
        func2 = h2;
        step= st;
    }
    
    /**
     * Returns the value associated with the key
     * returns null if the key is not present
     * @param key
     * @return 
     */
    public V get(K key){ 
		
		/*********** complete this method; now returning a dummy null ***********/
		
        return null;
    }
    
    /**
     * Removes the key value pair value from the hash table
     * @param key
     * @return 
     */
    public void remove(K key){ 
		long h = func1.Hash(key);
        long h2 = (func2==null)? 1 : func2.Hash(key); 
        for(int i = 0; i< size; i++){
            int hash = (int)((h+step.step(i, h2,size))%size);
            Entry<K,V> entry = table.get(hash);
            if(entry == null) return;
            if(entry.key == null){   //Special Case for deleted object
                continue;
            }
            if(entry.key.equals(key)){
                table.set(hash,new Entry(null,null));
                this.elements--;
                return;
            }
        }
        return;
    }
    
     /**
     * Returns true if the value is in the hash table
     * @param key
     * @return 
     */
    public boolean contains(K key){
		long h = func1.Hash(key);
        long h2 = (func2==null)? 1 : func2.Hash(key); 
        for(int i = 0; i< size; i++){
            int hash = (int)((h+step.step(i, h2,size))%size);
            Entry<K,V> entry = table.get(hash);
            if(entry == null) return false;
            if(entry.key == null){   //Special Case for deleted object
                continue;
            }
            if(entry.key.equals(key)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Puts the key value pair into the hash table, if the key is 
     * already in the value is updated
     * @param key
     * @param value
     * @return 
     */
    public boolean put(K key, V value){
		
		/*********** complete this method; now returning a dummy false ***********/
		
        return false;     
    }
    
    public String extraInfo(){return "Total number of Probes: "+ totalNumProbes+", Average Probe Depth: "+(double)totalNumProbes/(double)elements;}
    public double loadFactor(){return (double)elements/(double)size;}
    public String type(){return step.getType();}
    public int elements(){return this.elements;}
    private class Entry<K,V>{
        public K key;
        public V value;
        public Entry(K k,V v){
            key = k; value = v;
        }
    }
}

