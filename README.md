# 自用简单http服务模拟脚本

方便开发前端内容时发送请求获取数据

## 启动

```
groovy simpleApiServer.groovy
```

默认端口8091 默认读取数据json文件目录 同目录datas文件夹

```
groovy simpleApiServer.groovy 8080 ds
```

指定端口8080 文件夹同目录ds

```
groovy simpleApiServer.groovy 8080
```

仅指定端口

## json文件存放命名规则

例：  
json文件存放目录 datas/store/products   
文件名 get_200.json  
则访问路径为http://localhost:8091/store/products  
访问http方法为GET
返回的http状态码为200  
返回的内容为json文件内容

json文件存放目录 datas/store/products/:productId
文件名 post_201.json  
则访问路径为http://localhost:8091/store/products/1234  
productId为参数，可以随意指定，详细规则参见 [vert.x web router相关文档](https://vertx.io/docs/vertx-web/java/#_capturing_path_parameters)  
访问http方法为POST
返回的http状态码为201  
返回的内容为json文件内容