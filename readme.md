# 单词本网站手册

> 环境需求

- elasticsearch 
> 使用技术

    elasticsearch :     搜索
    POI :               管理excel
    
> 测试网址

    运行后.
    Swagger.                    http://localhost:8080/swagger-ui.html
    测试文件上传和文件下载地址.   http://localhost:8080/index
> 文件结构

    模块
    common - 通用工具, 暂时未使用
    system - 主要的功能实现
        - static    - 存放 excel 文件
        - com.xusi.system
            - config - 配置类
            - util - 工具类
            - controller - 接口
            - entity - 实体类
            - service - 服务
            
               
## 版本说明

### version beta 0.3 : '引入shiro,mybaits,jjw'
> 说明

    shiro 已引入.
    mybatis plus 已引入.
    权限管理可以通过用户名和密码登录获取 token, 请求通过携带返回的token可以进行权限认证.
> 下一版本

    引入 vue
    编写简单的前端页面 :
        文件上传和下载页面
        搜索页面
        登录页面
        注册页面

### version beta 0.2 : interface-finished
> 说明

    接口已完成.
    Swagger 已引入.    http://localhost:8080/swagger-ui.html
    测试文件上传和文件下载地址. http://localhost:8080/index
> 下一个版本

    引入权限管理
 
 ### version beta 0.1 : esUtil
 > 说明

    完成了文件上传和下载的功能, 并提供了简单的测试页面. http://localhost:8080/index
    完成了部分的工具类
    
 > 下一个版本

    完成接口.
    引入swagger.
    