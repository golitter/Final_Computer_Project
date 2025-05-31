**Installation of Redis**

```shell
sudo apt-get update
sudo apt-get install redis
```

**Starting redis-server**

```shell
sudo service redis-server start
```

**Accessing Redis**

```shell
sudo redis-cli
```

**Modifying Redis Configuration**

```shell
su # switch to root user
vim /etc/redis/redis.conf
```

Find the line `# requirepass foobared`, uncomment it,

`foobared` is the password, you can change it here.

Then exit the file.

**Restarting Redis**

```shell
sudo service redis-server restart
```

Inside, type `auth password` to log in for verification.