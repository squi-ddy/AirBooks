package airbooks.model;

public class FenwickTree {
    private final int[] tree;
    private int sum;

    public FenwickTree(int size) {
        tree = new int[size + 1];
    }

    public void update(int pos, int val) {
        pos++;
        sum += val;
        while (pos < tree.length) {
            tree[pos] += val;
            pos += (pos & -pos);
        }
    }

    public int sum() {
        return sum;
    }

    public int query(int val) {
        int tot = 0;
        int l = 0, r = 0;
        int msb = (int) Math.floor(Math.log(tree.length) / Math.log(2));
        while (msb != -1) {
            if (tree[(1 << msb) + l] + tot < val) {
                tot += tree[(1 << msb) + l];
                l = (1 << msb) + l;
            } else r = (1 << msb) + l;
            msb--;
        }
        return r - 1;
    } //find lowest position that is n-th lowest locker
}
