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
public class ChainedHashTable<K,V> implements HashTable<K,V> {
    HashFunction<K> func;
    int size;
    int collisions;
    int elements;
    private ArrayList<LinkedList<Entry<K,V>>> table;
    public ChainedHashTable(int s, HashFunction<K> h){
        elements = collisions =0;
        size = s;
        table = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            table.add(null);
        }
        func = h;
    }

    /**
     * Returns the value associated with the key
     * returns null if the key is not present
     * @param key
     * @return
     */
    public V get(K key){
        int hash = (int)(func.Hash(key)%size);
        LinkedList<Entry<K,V>> list = table.get(hash);
        if(list == null) return null;
        for(Entry<K,V> e: list){
            if(e.key.equals(key)){
                return e.value;
            }
        }
        return null;
    }

    /**
     * Removes the key value pair value from the hash table
     * @param key
     * @return
     */
    public void remove(K key){
        int hash = (int)(func.Hash(key)%size);
        LinkedList<Entry<K,V>> list = table.get(hash);
        if(list == null) return ;
        for(int i =0 ; i< list.size(); i++){
            Entry<K,V> e = list.get(i);
            if(e.key.equals(key)){
                list.remove(i);
            }
        }
        return ;
    }

    /**
     * Returns true if the value is in the hash table
     * @param key
     * @return
     */
    public boolean contains(K key){
        int hash = (int)(func.Hash(key)%size);
        LinkedList<Entry<K,V>> list = table.get(hash);
        if(list == null) return false;
        for(Entry<K,V> e: list){
            if(e.key.equals(key)){
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
        int hash = (int)(func.Hash(key)%size);
        LinkedList<Entry<K,V>> list = table.get(hash);
        if(contains(key)){
            for(Entry<K,V> e: list){
                if(e.key.equals(key)){
                    e.value = value;
                    return true;
                }
            }
        }
        else{
            elements++;
            if(list == null){
                list = new LinkedList<>();
                list.add(new Entry(key,value));
                table.set(hash, list);
                return true;
            }
            collisions ++;
            list.add(new Entry(key,value));
            return true;
        }
        return false;
    }

    public String extraInfo(){return "Total Collisions: "+collisions;}
    public double loadFactor(){return (double)elements/(double)size;}
    public String type(){return "Chaining";}
    public int elements(){return this.elements;}

    private class Entry<K,V>{
        public K key;
        public V value;
        public Entry(K k,V v){
            key = k; value = v;
        }
    }
}