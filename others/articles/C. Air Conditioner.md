You are given two sets of intervals `[l1, r1]` and `[l2, r2]`. The task is to determine if these two intervals overlap, and if they do, update the interval.

For two sets `[l1, r1]` and `[l2, r2]`, if there is no overlap, it means that one of the following conditions is true:

- The left boundary of one set is greater than the right boundary of the other set.
- The right boundary of one set is less than the left boundary of the other set.

This check is all you need to determine if the sets overlap, and you can then proceed to solve the problem.

### C++ Code:

```
cppCopyEditvoid solve() {
    int n, m; cin >> n >> m;
    bool ok = true;
    int p = 0;
    int st = 0, ed = 0;
    for (int i = 0; i < n; ++i) {
        int t, l, r; cin >> t >> l >> r;
        if (i == 0) 
            st = m - t, ed = m + t;
        else 
            st -= (t - p), ed += (t - p);
        
        ok &= !(ed < l || st > r);  // Check if intervals overlap
        st = max(st, l);  // Update the start of the interval
        ed = min(ed, r);  // Update the end of the interval
        p = t;
    }
    puts(ok ? "YES" : "NO");  // Output the result
}
```

