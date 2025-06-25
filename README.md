> SUST-Ulster COM668 ( 65096 ) - Computing Project. 2024-25.
>
> 

# **Design and Implementation of Personal Blog System**

# 个人博客系统的设计与实现

**个人博客系统的效果预览详见2425_61_10708_080901H_202115030121_BS.pdf和AT4.mp4**

- `blog-backend`：后端部分
- `blog-frontend`：前端部分
- `other`：
  - `articles`：所存储的文章内容
  - `sql`：数据库部分
  - `images`：论文的图片部分（未进行整理，直接拷贝过来的）
  - `2425_61_10708_080901H_202115030121_BS.pdf`：中方论文最终版
  - `AT4.mp4`: 项目演示视频（静默）
  - `答辩ppt.pdf`：答辩ppt


**密码**

Linux root：golitter

redis密码：redis

mysql密码：mysql

博客超级管理员：

```shell
username: golemon
password: golitter
```



## 项目启动

1. 启动后端部分：使用`IDEA`打开并运行
2. 启动前端部分：查看前端主目录中的`README.md`
3. 启动数据库（MySQL和redis）

```shell
sudo service redis-server start  
sudo service mysql start   
```

