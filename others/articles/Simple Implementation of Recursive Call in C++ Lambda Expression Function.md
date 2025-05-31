In C++11, recursive lambda expressions often require the `<functional>` header. The syntax is as follows:

```cpp
    function<int(int)> fib = [&fib] (int n) {
        if(n <= 2) return 1;
        else return fib(n-1) + fib(n-2);
    };
	cout<<fib(5);
```

There is also a simpler second method (C++14):

```cpp
    auto fib = [](auto && fib, int n)  -> int {
        if(n <= 2) return 1;
        else return fib(fib, n - 1) + fib(fib, n - 2);
    };
    cout<<fib(fib,5);
```

For the second method, `auto && fib` is used to allow the lambda expression to recursively call itself. In C++14, lambda expressions cannot recursively call themselves by default because they cannot access their own definition within the lambda.

Using `auto &&fib` essentially passes the recursive function object to the lambda expression itself, achieving recursion by recursively calling the passed function object.

The specific principle is as follows:

1. `auto &&fib` defines a function object of rvalue reference type, which is called `fib`.
2. Within the lambda expression, `fib` can be called directly because it is a function object.
3. During recursive calls, `fib` is passed to itself, thereby completing the recursive call to itself.

This technique bypasses the restriction on recursive calls in lambda expressions, fulfilling the need for recursive self-calls within the lambda expression.

[(7 messages) C++ Implementation of Lambda Recursive Call (C++11 - C++23)_c++ lambda recursion_J__M__C’s Blog-CSDN Blog](https://blog.csdn.net/J__M__C/article/details/125437699?ops_request_misc=&request_id=&biz_id=102&utm_term=lambda表达式泛型递归&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-125437699.142^v91^insert_down1,239^v3^control&spm=1018.2226.3001.4187)