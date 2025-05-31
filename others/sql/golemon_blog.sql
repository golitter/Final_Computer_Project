/*
 Navicat Premium Data Transfer

 Source Server         : local_wsl2_ubuntu_mysql
 Source Server Type    : MySQL
 Source Server Version : 80041
 Source Host           : localhost:3306
 Source Schema         : golemon_blog

 Target Server Type    : MySQL
 Target Server Version : 80041
 File Encoding         : 65001

 Date: 31/05/2025 15:37:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for access
-- ----------------------------
DROP TABLE IF EXISTS `access`;
CREATE TABLE `access`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `access_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '权限状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of access
-- ----------------------------
INSERT INTO `access` VALUES (1, '发表博客', 'article:add', '0');
INSERT INTO `access` VALUES (2, '删除博客', 'article:delete', '0');
INSERT INTO `access` VALUES (3, '编辑博客', 'article:edit', '0');
INSERT INTO `access` VALUES (4, '查看博客', 'article:view', '0');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类id',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint(0) NULL DEFAULT 0 COMMENT '访问量',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (46, 'Python and its related techniques', 'Python Data Analysis and Machine Learning Tips\nUpdate Date: February 3, 2025\n\n## Python\n\n### Coding Standards\n\nWhen declaring variables, you should not break lines, but you can use parentheses `()` to wrap the expression for line breaks.\n\n\n> Python allows code blocks within parentheses `()`, square brackets `[]`, and curly braces `{}` to be automatically wrapped without the need for an explicit backslash `\\`.\n\n```python\ntbdf = (\n    pd.read_csv(\'./data/cdctuberculosis.csv\',\n                header=1,\n                thousands=\',\')\n    .rename(columns=columns_renamedict)\n)\n```\n### Python’s Ruff\nRuff is a Linter and Formatter for Python, used for code checking and formatting.\n[Simple usage of Python’s ruff - CSDN Blog](https://golemon.blog.csdn.net/article/details/145433813?spm1001.2014.3001.5502)\n### Debugging\nUse `print` or `pdb` (more convenient) for debugging.\n\n### Type Annotations\nAdd type annotations to functions for better code clarity.\n\n```python\ndef dfs(i: int) -> int:\n    if i < 0:\n        return 1\n    else:\n        return dfs(i - 1) * i\n```\n### Pre-commit\nUse Python’s pre-commit library to check code/format before git commit.\n[Usage of Python’s pre-commit library - CSDN Blog](https://golemon.blog.csdn.net/article/details/145433704?spm1001.2014.3001.5502)\n### Virtual Environments\nAlways use virtual environments to manage dependencies and keep projects isolated.\n### Notebooks\nIn Jupyter Notebooks, use shortcuts to improve workflow: \n- `Shift + Enter`: Run the current cell and select the cell below\n- `Ctrl + Enter`: Run the current cell\n- `Alt + Enter`: Run the current cell and insert a new cell below\n- `M`: Convert the current cell to Markdown\n- `Y`: Convert the current cell to code\n\n## Others\n### Python’s Pre-commit\nChecks code/formats before git commit.\n[Usage of Python’s pre-commit library - CSDN Blog](https://golemon.blog.csdn.net/article/details/145433704?spm1001.2014.3001.5502)\n### Python’s Ruff\nRuff is a Linter and Formatter for Python, used for code checking and formatting.\n[Simple usage of Python’s ruff - CSDN Blog](https://golemon.blog.csdn.net/article/details/145433813?spm1001.2014.3001.5502)', 'Python Data Analysis and Machine Learning Tips\nUpdate Date: February 3, 2025\nPython\nCoding Standards\nWhen declaring variables, you should not break li', 2, '0', 14, NULL, '2025-04-22 21:44:52', NULL, '2025-04-22 21:44:52', 0);
INSERT INTO `article` VALUES (47, 'CCCC 2023 Summary and Reflections', 'This was my first team programming ladder competition, and I missed the individual third prize by just one point, which is indeed a pity.\n\nEspecially when I found out that outputting “No Solution” for L3 would earn 2 points, and outputting 20 would earn 15 points, I felt even more upset (although it was due to my own lack of skill).\n\nThe issues exposed during today’s competition are as follows:\n\n- Too slow in solving problems\n- Lacking a sense of the problems\n- Insufficient experience with the IO competition format\n\nPossible reasons include:\n\n- Not doing enough practice problems\n- Not having done past CCCC problems, thus unaware of the difficulty\n- Forgetting to score points strategically in the IO format\n\nSolutions:\n\n- If I aim to win a medal in ACM, I need to practice problems diligently every day, and reflect and summarize regularly. From now on, I will actively solve problems on Luogu and Codeforces. I plan to solve 30 problems a week, whether on Luogu, Nowcoder, or Codeforces.\n- It is crucial to keep records, **reflect**, and **summarize**. My personal blog has a “Talk” feature where I will check in daily, documenting what I’ve learned and summarizing my experiences.\n\n**Problem Reflection Summary**\n\n**CMU15-445 Frontend - WeChat Mini Program**\n\nKeep up the good work!!!', 'This was my first team programming ladder competition, and I missed the individual third prize by just one point, which is indeed a pity.\nEspecially w', 21, '0', 13, NULL, '2025-04-22 21:50:25', NULL, '2025-04-22 21:50:25', 0);
INSERT INTO `article` VALUES (48, 'Simple Implementation of Recursive Call in C++ Lambda Expression Function', 'In C++11, recursive lambda expressions often require the `<functional>` header. The syntax is as follows:\n\n```cpp\n    function<int(int)> fib = [&fib] (int n) {\n        if(n <= 2) return 1;\n        else return fib(n-1) + fib(n-2);\n    };\n	cout<<fib(5);\n```\n\nThere is also a simpler second method (C++14):\n\n```cpp\n    auto fib = [](auto && fib, int n)  -> int {\n        if(n <= 2) return 1;\n        else return fib(fib, n - 1) + fib(fib, n - 2);\n    };\n    cout<<fib(fib,5);\n```\n\nFor the second method, `auto && fib` is used to allow the lambda expression to recursively call itself. In C++14, lambda expressions cannot recursively call themselves by default because they cannot access their own definition within the lambda.\n\nUsing `auto &&fib` essentially passes the recursive function object to the lambda expression itself, achieving recursion by recursively calling the passed function object.\n\nThe specific principle is as follows:\n\n1. `auto &&fib` defines a function object of rvalue reference type, which is called `fib`.\n2. Within the lambda expression, `fib` can be called directly because it is a function object.\n3. During recursive calls, `fib` is passed to itself, thereby completing the recursive call to itself.\n\nThis technique bypasses the restriction on recursive calls in lambda expressions, fulfilling the need for recursive self-calls within the lambda expression.\n\n[(7 messages) C++ Implementation of Lambda Recursive Call (C++11 - C++23)_c++ lambda recursion_J__M__C’s Blog-CSDN Blog](https://blog.csdn.net/J__M__C/article/details/125437699?ops_request_misc=&request_id=&biz_id=102&utm_term=lambda表达式泛型递归&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-125437699.142^v91^insert_down1,239^v3^control&spm=1018.2226.3001.4187)', 'In C++11, recursive lambda expressions often require the &lt;functional&gt; header. The syntax is as follows:\n    function&lt;int(int)&gt; fib = [&amp', 22, '0', 3, NULL, '2025-04-22 21:56:22', NULL, '2025-04-22 21:56:22', 0);
INSERT INTO `article` VALUES (49, 'Introduction to RTTI', '## Introduction to RTTI\n\nRTTI (Runtime Type Identification) means “runtime type identification.” C++ is a statically typed language, meaning that data types are determined at compile time and cannot be changed at runtime. However, due to the requirements of polymorphism in object-oriented programming, the type of a pointer or reference in C++ may not be consistent with the actual type it represents. Sometimes, we need to convert a polymorphic pointer to the actual type of the object it points to, which requires knowing the type information at runtime, leading to the need for runtime type identification. Compared to Java, C++ can only obtain runtime type information through the RTTI mechanism, and the final code generated by C++ is directly related to the machine.\n\nC++ provides RTTI functionality through the following two keywords:\n\n1. `typeid`: This operator returns the actual type of its expression or type name.\n2. `dynamic_cast`: This operator safely converts a pointer or reference to a base class to a pointer or reference to a derived class type (also known as downcasting).\n\n## `typeid`\n\n### Static\n\nWhen the operand of `typeid` is any of the following, `typeid` determines the static type, which is the type determined at compile time:\n\n- An arbitrary type name\n- A variable of a basic built-in type, or a pointer or reference to a basic built-in type\n- A pointer to any type (a pointer is just a pointer, it does not exhibit polymorphism by itself, dereferencing a pointer may potentially exhibit polymorphism)\n- A specific object instance, whether the corresponding class has polymorphism or not, can be directly determined at compile time\n- Dereferencing a pointer to an object of a class without polymorphism\n- A reference to an object of a class without polymorphism\n\n### Dynamic\n\nWhen the operand of `typeid` is any of the following, `typeid` needs to deduce the type at runtime, because the type of the operand cannot be determined at compile time:\n\n- Dereferencing a pointer to an object of a class with polymorphism\n- A reference to an object of a class with polymorphism\n\n## `dynamic_cast`\n\nThis is the most commonly used RTTI component. It cannot answer questions like “Which class does this pointer point to?” but it can answer questions like “Can the address of this object be safely assigned to a pointer of a specific type?”\n\n> In other words, it checks whether this object pointer can be converted to the target pointer.\n\nUsually, if the type of the object pointed to by `*pt` is Type or is derived directly or indirectly from Type, the following expression converts the pointer `pt` to a pointer of type Type:\n\n```cpp\ndynamic_cast<Type *>(pt)\n```\n\nOtherwise, the result is 0, i.e., a null pointer.\n\n> **Note**: Even if the compiler supports RTTI, it may be turned off by default. If this feature is turned off, the program may still compile, but will result in runtime errors. In this case, you should check the documentation or menu options.\n\n`dynamic_cast` can also be used with **references**, with slightly different usage:\n\nThere is no reference value corresponding to a null pointer, so a special reference value cannot be used to indicate failure. When the request is incorrect, `dynamic_cast` will throw an exception of type `bad_cast`, which is derived from the `exception` class and is defined in the header file **typeinfo**.\n\n## References\n\n- [Detailed Explanation of C++ RTTI](https://blog.csdn.net/weixin_43798887/article/details/118541570?ops_request_misc=%7B%22request%5Fid%22%3A%22168485495316800184135470%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=168485495316800184135470&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-1-118541570-null-null.142^v87^koosearch_v1,239^v2^insert_chatgpt&utm_term=rtti&spm=1018.2226.3001.4187)\n- [What is RTTI in C++? How to use it? - Zhihu (zhihu.com)](https://zhuanlan.zhihu.com/p/509453699)', 'Introduction to RTTI\nRTTI (Runtime Type Identification) means “runtime type identification.” C++ is a statically typed language, meaning that data typ', 22, '0', 17, NULL, '2025-04-22 21:59:39', NULL, '2025-04-22 21:59:39', 0);
INSERT INTO `article` VALUES (50, 'Common tutorials for Anaconda', '### View All Current Environments\n\n```shell\nconda info --envs\n-- or\nconda env list\n```\n\n### Create a Virtual Environment\n\nCreate a virtual environment named `env_name` with Python version `beta`.\n\n```shell\nconda create -n env_name [ python=beta ]\n```\n\n### Delete a Virtual Environment\n\n```shell\nconda remove -n env_name --all\n```\n\n#### Activate a Virtual Environment\n\n```shell\nconda activate env_name\n```\n\n#### Deactivate a Virtual Environment\n\n```shell\nconda deactivate env_name\n```\n\n#### Package Operations Within a Virtual Environment\n\n##### If you are not in the virtual environment where you want to install packages\n\n```shell\n# View installed packages in a specified environment\nconda list -n env_name\n# Install a package in a specified environment\nconda install -n env_name [package]\n# Remove a package from a specified environment\nconda remove -n env_name [package]\n# Update a package in a specified environment\nconda update -n env_name [package]\n```\n\n##### If you are in the virtual environment where you want to install packages\n\n```shell\n# View installed packages\nconda list\n# Install a package\nconda install [package]\n# Remove a package\nconda remove [package]\n# Update a package\nconda update [package]\n\n# Update conda to keep it up-to-date\nconda update conda\n```\n\n##### Export Virtual Environment to a YAML File\n\n```shell\nconda env export > environment.yaml -- Export\nconda env create -f environment.yaml -- Import\n```\n\n##### Use pip to Export and Import Package Information for a Virtual Environment\n\n```shell\npip freeze > requirements.txt -- Export\npip install -r requirements.txt -- Import\n```\n\n', 'View All Current Environments\nconda info --envs\n-- or\nconda env list\n\nCreate a Virtual Environment\nCreate a virtual environment named env_name with Py', 2, '0', 21, NULL, '2025-04-22 22:03:19', NULL, '2025-04-22 22:03:19', 0);
INSERT INTO `article` VALUES (51, 'WSL2 Ubuntu configure Redis', '**Installation of Redis**\n\n```shell\nsudo apt-get update\nsudo apt-get install redis\n```\n\n**Starting redis-server**\n\n```shell\nsudo service redis-server start\n```\n\n**Accessing Redis**\n\n```shell\nsudo redis-cli\n```\n\n**Modifying Redis Configuration**\n\n```shell\nsu # switch to root user\nvim /etc/redis/redis.conf\n```\n\nFind the line `# requirepass foobared`, uncomment it,\n\n`foobared` is the password, you can change it here.\n\nThen exit the file.\n\n**Restarting Redis**\n\n```shell\nsudo service redis-server restart\n```\n\nInside, type `auth password` to log in for verification.\n\n', 'Installation of Redis\nsudo apt-get update\nsudo apt-get install redis\n\nStarting redis-server\nsudo service redis-server start\n\nAccessing Redis\nsudo redi', 23, '0', 28, NULL, '2025-04-22 22:08:22', NULL, '2025-04-22 22:08:22', 0);
INSERT INTO `article` VALUES (52, 'Getting Started with Vue.js: Core Concepts, Syntax, and Data Binding', '### [Quick Start | Vue.js (vuejs.org)](https://cn.vuejs.org/guide/quick-start.html#using-vue-from-cdn)\n\n```\nhtmlCopyEdit<script src=\"https://unpkg.com/vue@3/dist/vue.global.js\"></script>\n<!-- or -->\n<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n```\n\n### Initializing Vue\n\n1. To make Vue work, you must create a Vue instance and pass in a configuration object.\n2. The code inside the root container still follows standard HTML syntax, but with some special Vue syntax mixed in.\n3. The code inside the root container is called the **Vue template**.\n4. A Vue instance corresponds to exactly one container.\n5. In real development, there is typically only one Vue instance, which works alongside components.\n6. In `{{xxx}}`, `xxx` must be a JavaScript expression and can directly access all attributes from `data`.\n7. When data in `data` changes, any part of the template that uses this data will automatically update.\n\n```\nhtmlCopyEdit<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\">\n  <script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n  <title>Vue</title>\n</head>\n<body>\n  <!-- Vue Container -->\n  <div id=\"root\">\n    <h1>Hello, {{name}}</h1>\n  </div>\n  <script type=\"text/javascript\">\n    // Vue instance\n    new Vue({\n      el: \'#root\', // \'el\' specifies which container the Vue instance controls, usually a CSS selector\n      data: {      // \'data\' stores data used by the container specified by \'el\'\n        name: \'Kerwin\' // Can be nested objects\n      }\n    }) \n  </script>\n</body>\n</html>\n```\n\n### Vue Template Syntax\n\nThere are two major types of template syntax:\n\n- **Interpolation Syntax**\n  - Purpose: To render text content inside HTML elements.\n  - Syntax: `{{xxx}}`, where `xxx` is a JavaScript expression and can access any property in `data`.\n- **Directive Syntax**\n  - Purpose: To manipulate HTML elements (attributes, text content, events, etc.)\n  - Example: `<a v-bind:href=\"xxx\">` or the shorthand `<a :href=\"xxx\">`, where `xxx` is a JavaScript expression that can read from `data`.\n  - Note: Vue has many directives, all with the form `v-???`; here we use `v-bind` as an example.\n\n------\n\n### Data Binding\n\n```\nhtmlCopyEdit<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n  <script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n  <title>Vue</title>\n</head>\n<body>\n  <div id=\"root\">\n    <input type=\"text\" v-bind:value=\"name\"><br>\n    <h3>{{name}}</h3>\n    <input type=\"text\" v-model:value=\"name\"><br>\n  </div>\n  <script>\n    new Vue({\n      el:\"#root\",\n      data: {\n        name:\"kerwin\"\n      }\n    })\n  </script>\n</body>\n</html>\n```\n\nVue provides two data binding methods:\n\n- **One-way binding** (`v-bind`): Data flows from `data` to the DOM only.\n- **Two-way binding** (`v-model`): Data flows in both directions — from `data` to DOM and DOM to `data`.\n\nNote:\n Two-way binding is usually used with form elements like `<input>`, `<select>`, and `<textarea>`.\n `v-model:value` can be simplified to `v-model` because it binds to the `value` attribute by default.\n\n------\n\n### Two Ways to Use `el` and `data`\n\n#### `el` can be written in two ways:\n\n1. Define `el` when creating the Vue instance.\n2. Create the Vue instance first, and then use `vm.$mount(\'#root\')` to specify the `el` value.\n\n```\nhtmlCopyEdit<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n  <script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n  <title>Vue</title>\n</head>\n<body>\n  <div id=\"root\">\n    <input type=\"text\" v-bind:value=\"name\"><br>\n    <h3>{{name}}</h3>\n    <input type=\"text\" v-model=\"name\"><br>\n  </div>\n  <script>\n    const x = new Vue({\n      // el: \"#root\",\n      data: {\n        name: \"kerwin\"\n      }\n    })\n    console.log(x)\n    x.$mount(\"#root\") // Second approach\n  </script>\n</body>\n</html>\n```\n\n#### `data` can be written in two ways:\n\n1. **Object syntax**\n2. **Function syntax**\n\n```\njavascriptCopyEditconst x = new Vue({\n  // el: \"#root\",\n  data() {\n    return {\n      name: \'Kerwin\'\n    }\n  }\n})\n```\n\n**How to choose:** Both ways are valid now, but when working with components later, `data` **must** be a function, or you will get an error.\n\n> Important: Do not use arrow functions for methods managed by Vue, otherwise `this` will no longer refer to the Vue instance.\n\n------\n\n### MVVM Architecture\n\n![img](https://img-blog.csdnimg.cn/img_convert/ac43527b06defd324a702aea4f7940a0.png)\n\n**MVVM Model:**\n\n- **M** (Model): The data, defined in the `data` object.\n- **V** (View): The UI, defined in the template.\n- **VM** (ViewModel): The Vue instance.\n\nAll properties in `data` eventually appear on the Vue instance (`vm`).\n All properties on the Vue instance and on the Vue prototype are directly accessible in the Vue template.', 'Quick Start | Vue.js (vuejs.org)\nhtmlCopyEdit&lt;script src=&quot;https://unpkg.com/vue@3/dist/vue.global.js&quot;&gt;&lt;/script&gt;\n&lt;!-- or --&gt', 4, '0', 29, NULL, '2025-04-30 21:32:35', NULL, '2025-04-30 21:32:35', 0);
INSERT INTO `article` VALUES (53, 'Collecting Form Data', 'When collecting form data with `v-model`:\n\n- For `<input type=\"text\" />`, `v-model` binds to the `value` property, meaning it collects the text entered by the user.\n- For `<input type=\"radio\" />`, `v-model` also collects the `value`, and each radio button should be assigned a `value` attribute.\n- For `<input type=\"checkbox\" />`:\n  - If no `value` attribute is configured, `v-model` collects the `checked` state — a boolean indicating whether the box is checked.\n  - If a `value` attribute is configured:\n    - If the initial value bound by `v-model` is not an array, it collects the `checked` state (boolean).\n    - If the initial value is an array, it collects an array of `value`s for the checked boxes.\n\n**Three modifiers for `v-model`:**\n\n- `lazy`: Data is collected only after the input loses focus.\n- `number`: Converts the input string to a valid number.\n- `trim`: Automatically trims leading and trailing whitespace from the input.', 'When collecting form data with v-model:\n\nFor &lt;input type=&quot;text&quot; /&gt;, v-model binds to the value property, meaning it collects the text ', 4, '0', 14, NULL, '2025-04-30 21:43:17', NULL, '2025-04-30 21:43:17', 0);
INSERT INTO `article` VALUES (54, 'Understanding PostgreSQL Schemas: Concepts, Benefits, and Management', 'A schema is a fundamental concept in the database domain. Some databases combine schemas with users, but PostgreSQL clearly distinguishes between them.\n\nIn essence, a schema in a database acts like a namespace or directory. Different schemas can contain objects (like tables or functions) with the same name without conflict. This design simplifies management, and as long as proper permissions are in place, objects across schemas can reference each other.\n\nIn PostgreSQL, a single database can contain multiple schemas, and each schema holds its own set of tables, functions, and other objects. However, this structure may vary across different databases.\n\nAdvantages of schemas:\n\nEnable multiple users to work within the same database without interfering with each other.\n\nGroup database objects logically for easier management.\n\nAllow third-party applications to reside in separate schemas, avoiding naming conflicts with existing objects.\n\nSyntax examples:\n```\n-- Create a schema\nCREATE SCHEMA schemaname [ AUTHORIZATION username ... ];\n\n-- List existing schemas\n\\dn\n\n-- Drop a schema\nDROP SCHEMA schemaname;\n```\nSchema Search Path:\n\nWriting fully qualified object names can be cumbersome, and hardcoding schema names in applications is not recommended. Typically, objects are referred to by their unqualified names. PostgreSQL determines the target object by searching through a search path—a list of schemas to be searched in order. The first match found is used. If no matching object is found in the search path, an error is thrown, even if the object exists in another schema.\n\nThe first schema in the search path is known as the current schema, and it is also the default location for new objects created without a specified schema.\n\n```\n-- Show current search path\nSHOW search_path;\n```\n-- Set default schema search path\nSET search_path TO selfschema, public;\nSchema Permissions:\n\nBy default, users cannot access objects in schemas they do not own. To enable access, the USAGE privilege must be granted on the schema.\n\n```\n-- Revoke CREATE permission from all users\nREVOKE CREATE ON SCHEMA schemaname FROM PUBLIC;\n-- \'schemaname\' is the schema name; \'PUBLIC\' refers to all users.\n```\nPortability Considerations:\n\nAccording to the SQL standard, objects within the same schema cannot be owned by different users, and the concept of a public schema does not exist. Some database systems do not support schemas at all.', 'A schema is a fundamental concept in the database domain. Some databases combine schemas with users, but PostgreSQL clearly distinguishes between them', 24, '0', 11, NULL, '2025-04-30 21:45:09', NULL, '2025-04-30 21:45:09', 0);
INSERT INTO `article` VALUES (55, 'Improving Mini Program Asynchronous APIs with Promise-Based Refactoring', '#### Drawbacks of Callback-Based Asynchronous APIs\n\nBy default, WeChat Mini Programs provide asynchronous APIs that are implemented using callback functions.\n\n**API Promisification** refers to the process of transforming these callback-based asynchronous APIs into Promise-based ones through additional configuration. This enhances code **readability** and **maintainability**, and helps **avoid callback hell**.\n\nIn Mini Programs, Promise-based refactoring is primarily achieved using a third-party npm package called **`miniprogram-api-promise`**.\n\n```bash\nnpm i --save miniprogram-api-promise\n```\n\nAfter installation, don’t forget to **build the npm component**.\n\n```javascript\njsCopyEditimport { promisifyAll } from \"miniprogram-api-promise\"\nconst wxp = wx.p = {}\n\npromisifyAll(wx, wxp)\n```\n\nNow, in your page script, you can use `async/await` syntax for cleaner asynchronous logic:\n\n```javascript\njsCopyEditasync get__info() {\n  const res = await wx.p.request({\n    method: \'GET\',\n    url: \'https://www.escook.cn/api/get\',\n    data: {\n      name: \"golitter\",\n      age: 20\n    }\n  })\n  console.log(res)\n}\n```\n\nBy applying this method, your asynchronous code becomes easier to follow and maintain.\n\n', 'Drawbacks of Callback-Based Asynchronous APIs\nBy default, WeChat Mini Programs provide asynchronous APIs that are implemented using callback functions', 25, '0', 20, NULL, '2025-04-30 21:50:09', NULL, '2025-04-30 21:50:09', 0);
INSERT INTO `article` VALUES (56, '1830A - Copil Copac and Drawing Trees', 'Copil Copac received a list of $n - 1$ edges representing a tree with $n$ vertices. He wants to draw the tree using the following algorithm:\n\n- **Step 0**: Draw the first vertex (vertex $1$). Proceed to Step 1.\n- **Step 1**: Iterate through each edge in the order they appear in the input. If an edge connects a drawn vertex $u$ with an undrawn vertex $v$, then draw vertex $v$ and the edge between them. After checking all edges, go to Step 2.\n- **Step 2**: If all vertices have been drawn, terminate the algorithm. Otherwise, return to Step 1.\n\nThe number of **readings** is defined as the number of times Step 1 is executed by Copil Copac.\n\nYou are required to determine how many readings are necessary to complete the drawing of the tree.\n\n**Simplified Interpretation:**\n\nYou are given a tree with edges in a fixed input order. Starting from vertex 1, at each reading (i.e., one full iteration through the edge list), you can only draw an edge and its corresponding undrawn vertex if one of its endpoints has already been drawn. However, you can only do this effectively if the edge appears *after* the edge used to reach the parent node; otherwise, an additional reading is required. The goal is to compute the **minimum number of readings** needed to fully draw the tree.\n\n**Code**：\n```cpp\n#include <iostream>\n#include <vector>\n#include <string>\n#include <cstring>\n#include <set>\n#include <map>\n#include <queue>\n#include <ctime>\n#include <random>\n#include <sstream>\n#include <numeric>\n#include <stdio.h>\n#include <functional>\n#include <bitset>\n#include <algorithm>\nusing namespace std;\n\n#define Multiple_groups_of_examples\n#define IOS std::cout.tie(0);std::cin.tie(0)->sync_with_stdio(false);\n#define dbgnb(a) std::cout << #a << \" = \" << a << \'\\n\';\n#define dbgtt cout<<\" !!!test!!! \"<<endl;\n#define rep(i,x,n) for(int i = x; i <= n; i++)\n\n#define all(x) (x).begin(),(x).end()\n#define pb push_back\n#define vf first\n#define vs second\n\ntypedef long long LL;\ntypedef pair<int,int> PII;\n\nconst int INF = 0x3f3f3f3f;\nconst int N = 2e5 + 21;\n\nvoid inpfile();\nvoid solve() {\n    int n; cin>>n;\n    vector<vector<PII>> g(n+1); // PII({ node u, input order })\n    for(int i = 2; i <= n ; ++i) {\n        int u,v; cin>>u>>v;\n        // Undirected\n        g[u].push_back({v,i});\n        g[v].push_back({u,i});\n    }\n    // f[i] represents how many readings were required to reach node i\n    vector<int> f(n + 1);\n    int ans = 0; // To store the answer\n    f[1] = 1; // The first node requires one reading\n    auto vis(f); // Whether the node has been visited, can skip if y == fu or idx == fi (indicating it\'s already checked)\n\n    // Current node, parent node, and the input order number of the edge connecting them\n    auto dfs = [&](auto &&dfs, int u, int fu, int fi) -> void {\n        for(auto t: g[u]) {\n            // Get child node and the input order number of the edge <u, y>\n            int y = t.vf, idx = t.vs;\n            if(y == fu || idx == fi) continue;\n            if(vis[y]) continue;\n            vis[y] = 1;\n            // If the input order number of <u, y> is smaller than that of <fu, u>, an additional reading is required\n            f[y] = f[u] + (idx < fi);\n            dfs(dfs, y, u, idx);\n        }\n        // Update the answer, it\'s the maximum since the problem requires the maximum number of readings needed to draw everything\n        ans = max(ans, f[u]);\n    };\n    dfs(dfs, 1, -1, 0);\n    cout << ans << endl;\n}\n\nint main()\n{\n    #ifdef Multiple_groups_of_examples\n    int T; cin>>T;\n    while(T--)\n    #endif\n    solve();\n    return 0;\n}\n\nvoid inpfile() {\n    #define mytest\n    #ifdef mytest\n    freopen(\"ANSWER.txt\", \"w\",stdout);\n    #endif\n}\n```\n\n**Approach Summary (as in the code):**\n\nThe solution is similar to a tree-based dynamic programming problem. A depth-first search is used to traverse the tree. For each node, we record how many readings were required to reach it. If the edge used to reach the child node comes *before* the edge used to reach the current node (i.e., out of order), an additional reading is counted. The final answer is the maximum number of readings needed among all vertices.\n\n', 'Copil Copac received a list of $n - 1$ edges representing a tree with $n$ vertices. He wants to draw the tree using the following algorithm:\n\nStep 0: ', 26, '0', 68, NULL, '2025-04-30 21:59:29', NULL, '2025-04-30 21:59:29', 0);
INSERT INTO `article` VALUES (57, 'C. Air Conditioner', 'You are given two sets of intervals [l1, r1] and [l2, r2]. The task is to determine if these two intervals overlap, and if they do, update the interval.\n\nFor two sets [l1, r1] and [l2, r2], if there is no overlap, it means that one of the following conditions is true:\n\n- The left boundary of one set is greater than the right boundary of the other set.\n\n- The right boundary of one set is less than the left boundary of the other set.\n\nThis check is all you need to determine if the sets overlap, and you can then proceed to solve the problem.\n**C++ Code**:\n```cpp\nvoid solve() {\n    int n,m; cin>>n>>m;\n    bool ok = true;\n    int p = 0;\n    int st = 0, ed = 0;\n    for(int i = 0; i < n; ++i) {\n        int t,l,r; cin>>t>>l>>r;\n        if(i == 0) st = m - t, ed = m + t;\n        else st -= (t - p), ed += (t - p);\n        ok &= !(ed < l || st > r);\n        st = max(st, l), ed = min(ed, r);\n        // cout<<st<<\" \"<<ed<<endl;\n        p = t;\n    }\n    puts(ok ? \"YES\" : \"NO\");\n}\n```\n', 'You are given two sets of intervals [l1, r1] and [l2, r2]. The task is to determine if these two intervals overlap, and if they do, update the interva', 26, '0', 34, NULL, '2025-04-30 22:02:40', NULL, '2025-04-30 22:02:40', 0);
INSERT INTO `article` VALUES (58, 'Test Markdown', '# Heading  --ttt\n## Heading  \n### Heading  \n#### Heading  \n\n**Bold**  \n*Italic*  \n***Bold Italic***  \n\n---  \n~~Strikethrough~~  \n<u>Underline</u>  \n\n| Header     | Header     | Header     |  \n| ---------- | ---------- | ---------- |  \n| Row1 Col1  | Row1 Col2  | Row1 Col3  |  \n| Row2 Col1  | Row2 Col2  | Row2 Col3  |  \n| Row3 Col1  | Row3 Col2  | Row3 Col3  |  \n\n$O(n^{2})$  \n$\\alpha, \\beta, \\gamma$  \n\n$$  \n\\vec{Y} =  \n\\begin{bmatrix}  \ny_1 \\\\  \ny_2 \\\\  \n... \\\\  \ny_n  \n\\end{bmatrix},  \n\\vec{X} = \\begin{bmatrix}  \nx_{11} ... x_{1p} \\\\  \n... \\\\  \nx_{n1} ... x_{np}  \n\\end{bmatrix},  \n\\vec{\\beta} = \\begin{bmatrix}  \n\\beta_0 \\\\  \n\\beta_1 \\\\  \n... \\\\  \n\\beta_p  \n\\end{bmatrix}  \n$$  \n\n```mermaid  \ngraph LR  \n  A[Start] --> B{Satisfied?}  \n  B -- Yes --> C[End]  \n  B -- No --> D[Improve]  \n  D --> B  \n```\n\nCode:\n```cpp\nvoid solve() {  \n    auto fib = [](auto && fib, int n) -> int {  \n        if(n <= 2) return 1;  \n        else return fib(fib, n - 1) + fib(fib, n - 2);  \n    };  \n    cout << fib(fib, 5);  \n}  \n```', 'Programming Languages\nThis is Python\nprint(\'Hello World\')\n\nThis is C++\nstd::cout&lt;&lt;&quot;Hello World&quot;;\n\nThis is shell\necho &quot;Hello world', 26, '0', 166, NULL, '2025-05-01 00:29:39', NULL, '2025-05-01 00:29:39', 0);

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `article_id` bigint(0) NOT NULL COMMENT '文章 ID',
  `tag_id` bigint(0) NOT NULL COMMENT '标签 ID',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章-标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (26, 11);
INSERT INTO `article_tag` VALUES (26, 12);
INSERT INTO `article_tag` VALUES (46, 2);
INSERT INTO `article_tag` VALUES (47, 17);
INSERT INTO `article_tag` VALUES (48, 18);
INSERT INTO `article_tag` VALUES (49, 18);
INSERT INTO `article_tag` VALUES (50, 2);
INSERT INTO `article_tag` VALUES (51, 19);
INSERT INTO `article_tag` VALUES (52, 15);
INSERT INTO `article_tag` VALUES (53, 15);
INSERT INTO `article_tag` VALUES (54, 16);
INSERT INTO `article_tag` VALUES (55, 21);
INSERT INTO `article_tag` VALUES (56, 17);
INSERT INTO `article_tag` VALUES (56, 18);
INSERT INTO `article_tag` VALUES (57, 17);
INSERT INTO `article_tag` VALUES (57, 18);
INSERT INTO `article_tag` VALUES (58, 23);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `pid` bigint(0) NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 'markdown', -1, '0', 1, '2025-05-31 14:51:53', 0);
INSERT INTO `category` VALUES (2, 'python', -1, '0', 1, '2025-05-31 22:32:45', 0);
INSERT INTO `category` VALUES (3, 'java', -1, '0', 1, '2025-05-31 22:56:36', 0);
INSERT INTO `category` VALUES (4, 'vue3', -1, '0', 1, '2025-05-31 23:59:15', 0);
INSERT INTO `category` VALUES (19, '', -1, '0', NULL, '2025-05-31 20:40:05', 0);
INSERT INTO `category` VALUES (20, 'abcd', -1, '0', NULL, '2025-03-18 20:13:30', 0);
INSERT INTO `category` VALUES (21, 'reflection', -1, '0', NULL, '2025-04-22 21:50:25', 0);
INSERT INTO `category` VALUES (22, 'cpp', -1, '0', NULL, '2025-04-22 21:56:22', 0);
INSERT INTO `category` VALUES (23, 'linux', -1, '0', NULL, '2025-04-22 22:08:22', 0);
INSERT INTO `category` VALUES (24, 'database', -1, '0', NULL, '2025-04-30 21:45:09', 0);
INSERT INTO `category` VALUES (25, 'WeChat Mini Program', -1, '0', NULL, '2025-04-30 21:50:09', 0);
INSERT INTO `category` VALUES (26, 'algorithm', -1, '0', NULL, '2025-04-30 21:59:29', 0);
INSERT INTO `category` VALUES (27, 'f', -1, '0', NULL, '2025-05-04 13:33:35', 0);
INSERT INTO `category` VALUES (28, 'ffff', -1, '0', NULL, '2025-05-04 13:37:22', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT 'del_flag',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'admin', '0', 0);
INSERT INTO `role` VALUES (2, '普通用户', 'normal_user', '0', 0);

-- ----------------------------
-- Table structure for role_access
-- ----------------------------
DROP TABLE IF EXISTS `role_access`;
CREATE TABLE `role_access`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `access_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '权限id',
  PRIMARY KEY (`role_id`, `access_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048696 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_access
-- ----------------------------
INSERT INTO `role_access` VALUES (1, 1);
INSERT INTO `role_access` VALUES (1, 2);
INSERT INTO `role_access` VALUES (1, 3);
INSERT INTO `role_access` VALUES (1, 4);
INSERT INTO `role_access` VALUES (2, 4);
INSERT INTO `role_access` VALUES (14787164048694, 1);
INSERT INTO `role_access` VALUES (14787164048694, 2);
INSERT INTO `role_access` VALUES (14787164048694, 3);
INSERT INTO `role_access` VALUES (14787164048694, 4);
INSERT INTO `role_access` VALUES (14787164048696, 1);
INSERT INTO `role_access` VALUES (14787164048696, 2);
INSERT INTO `role_access` VALUES (14787164048696, 3);
INSERT INTO `role_access` VALUES (14787164048696, 4);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (2, 'python', '2025-05-31 22:32:45', 0);
INSERT INTO `tag` VALUES (15, 'vue3', '2025-04-13 15:32:00', 0);
INSERT INTO `tag` VALUES (17, 'algorithm', '2025-04-22 21:50:25', 0);
INSERT INTO `tag` VALUES (18, 'cpp', '2025-04-22 21:56:22', 0);
INSERT INTO `tag` VALUES (19, 'linux', '2025-04-22 22:08:22', 0);
INSERT INTO `tag` VALUES (20, 'database', '2025-04-30 21:45:09', 0);
INSERT INTO `tag` VALUES (21, 'WeChat Mini Program', '2025-04-30 21:50:09', 0);
INSERT INTO `tag` VALUES (22, 'name', '2025-05-01 00:34:39', 0);
INSERT INTO `tag` VALUES (23, 'markdown', '2025-05-04 13:27:14', 0);
INSERT INTO `tag` VALUES (24, 'kjk', '2025-05-04 13:29:11', 0);
INSERT INTO `tag` VALUES (25, 'ki', '2025-05-04 13:29:32', 0);
INSERT INTO `tag` VALUES (26, 'a', '2025-05-04 13:36:58', 0);
INSERT INTO `tag` VALUES (27, 'b', '2025-05-04 13:36:58', 0);
INSERT INTO `tag` VALUES (28, 'ffff', '2025-05-04 13:37:22', 0);
INSERT INTO `tag` VALUES (29, 'test', '2025-05-07 09:27:59', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `signature` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Golemon is good!' COMMENT '个性签名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户类型：0 代表普通用户，1 代表管理员(只能有一个)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048696 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (14787164048694, 'test', '用一句话来介绍自己', '$2a$10$.9aeVTOjsmOsy7XpUOgHoun1zMIAgASG4TAHzsTadZ9r/6NBePrTW', '1', '0', 'djfkdslfj@qq.com', NULL, NULL, 'https://ui-avatars.com/api/?name=test&background=d2c8cd&color=0bd263', '2025-02-11 15:49:57', '2025-02-11 15:49:57', 0);
INSERT INTO `user` VALUES (14787164048696, 'golemon', 'Golemon is good!', '$2a$10$rePNrAuH851IPxipF2QwneCOCJ8nnw2rPSBNQPXrmaAm4jm3dYM.i', '1', '0', 'golitter@qq.com', NULL, NULL, NULL, '2025-05-05 00:44:09', '2025-05-05 00:44:09', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048696 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (14787164048694, 1);
INSERT INTO `user_role` VALUES (14787164048696, 1);

SET FOREIGN_KEY_CHECKS = 1;
