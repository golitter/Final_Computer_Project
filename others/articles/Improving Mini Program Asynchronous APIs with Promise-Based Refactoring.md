#### Drawbacks of Callback-Based Asynchronous APIs

By default, WeChat Mini Programs provide asynchronous APIs that are implemented using callback functions.

**API Promisification** refers to the process of transforming these callback-based asynchronous APIs into Promise-based ones through additional configuration. This enhances code **readability** and **maintainability**, and helps **avoid callback hell**.

In Mini Programs, Promise-based refactoring is primarily achieved using a third-party npm package called **`miniprogram-api-promise`**.

```
bash


CopyEdit
npm i --save miniprogram-api-promise
```

After installation, don’t forget to **build the npm component**.

```
jsCopyEditimport { promisifyAll } from "miniprogram-api-promise"
const wxp = wx.p = {}

promisifyAll(wx, wxp)
```

Now, in your page script, you can use `async/await` syntax for cleaner asynchronous logic:

```
jsCopyEditasync get__info() {
  const res = await wx.p.request({
    method: 'GET',
    url: 'https://www.escook.cn/api/get',
    data: {
      name: "golitter",
      age: 20
    }
  })
  console.log(res)
}
```

By applying this method, your asynchronous code becomes easier to follow and maintain.

