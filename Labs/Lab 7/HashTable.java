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
public interface HashTable<K,V> {
    public V get(K key);
    public boolean contains(K key);
    public boolean put(K key, V value);
    public String extraInfo();
    public double loadFactor();
    public void remove(K key);
    public int elements();
    public String type();
}
