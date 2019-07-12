import java.util.ArrayList;

class OALDictionary<Key extends Comparable, E> implements Dictionary<Key, E> {
    private static final int defaultSize = 10;
    private ArrayList<KVpair<Key, E>> list;

    // Constructors
    OALDictionary() {
        this(defaultSize);
    }

    OALDictionary(int sz) {
        list = new ArrayList<KVpair<Key, E>>(sz);
    }

    public void clear() {
        list.clear();
    }

    /** Insert an element: append to list */
    public void insert(Key k, E e) {
        KVpair<Key, E> temp = new KVpair<Key, E>(k, e);
        int i = 0;
        while ((i < list.size()) && (k.compareTo(list.get(i).key()) > 0))
            ++i;
        list.add(i, temp);
    }

    /** Remove element with key k, return element */
    public E remove(Key k) {
        E temp = find(k);
        if (temp != null)
            list.remove(new KVpair<Key, E>(k, temp));
        return temp;
    }

    /** Remove any element */
    public E removeAny() {
        return list.remove(list.size()-1).value();
    }

    /**
     * Find k using sequential search
     *
     * @return Record with key value k
     */
    public E sfind(Key k) {
        for (KVpair<Key, E> t : list)
            if (k.compareTo(t.key()) == 0)
                return t.value();
        return null; // "k" does not appear in dictionary
    }

    /**
     * Find k using binary search
     *
     * @return Record with key value k
     */
    public E find(Key k) {
        int low = 0;
        int hi = list.size() - 1;
        int mid = (low + hi) / 2;
        while (low <= hi) {
            if (k.compareTo(list.get(mid).key()) == 0)
                return list.get(mid).value();
            else if (k.compareTo(list.get(mid).key()) > 0)
                low = mid+1;
            else
                hi = mid-1;
            mid = (low + hi) / 2;
        }
        return null; // "k" does not appear in dictionary
    }

    /* findAll implementation using inefficient sequential search.
     *
     */
    public Iterable<E> sfindAll(Key k) {
        ArrayList<E> al = new ArrayList<E>();
        for (KVpair<Key, E> t : list)
            if (k.compareTo(t.key()) == 0)
                al.add(t.value());
        return al;
    }

    public Iterable<E> findAll(Key k) {
        ArrayList<E> al = new ArrayList<E>();

        int fidx = -1;
        int low = 0;
        int hi = list.size() - 1;
        int mid = (low + hi) / 2;
        while ((low <= hi) && (fidx < 0)) {
            if (k.compareTo(list.get(mid).key()) == 0)
                fidx = mid;
            else if (k.compareTo(list.get(mid).key()) > 0)
                low = mid+1;
            else
                hi = mid-1;
            mid = (low + hi) / 2;
        }
        if(fidx <0) return al; // "k" does not appear in dictionary
        int i = fidx;
        for(i=fidx; i>=0;--i)
            if(k.compareTo(list.get(i).key()) != 0)
                break;
        fidx = i+1; // the first instance of key k
        for(i=fidx; i<list.size(); ++i)
        {
            if(k.compareTo(list.get(i).key()) == 0)
                al.add(list.get(i).value());
            else
                break;
        }

        return al;
    }

    public int size() {
        return list.size();
    }

    /** Returns an iterable collection of the dictionary. */
    public Iterable<E> values() {
        ArrayList<E> elements = new ArrayList<E>(list.size());
        for (KVpair<Key, E> t : list)
            elements.add(t.value());
        return elements;
    }
}
