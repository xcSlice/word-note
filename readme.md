# 单词本网站手册

> 环境需求

- elasticsearch 
> 使用技术

    elasticsearch :     搜索
    POI :               管理excel
    mybatis-plus        登录管理
    
    
> 测试网址

    运行后.
    Swagger.                    http://localhost:8080/swagger-ui.html
    测试文件上传和文件下载地址.   http://localhost:8080/index
    前端页面                    https://github.com/xcSlice/word-note-vue.git
> 文件结构

    模块
    common - 通用工具, 暂时未使用
    word-search - 查询单词
        - com.xusi.search
            - entity - 实体类
            - util  - 工具类
    system - 主要的功能实现
        - static    - 存放 excel 文件
        - com.xusi.system
            - config - 配置类
            - util - 工具类
            - controller - 接口
            - entity - 实体类
            - service - 服务
            
               
## 版本说明

### version beta 0.4 'word-search'
> 说明

    完成了前端测试页面的基础功能, 一些功能只完成了测试,确定其可使用, 具体的功能未完成
    加入了一个 ==word-search== 模块, 其使用了 jsoup 来爬取查询的单词,前端页面也完成了
    完善了文件管理功能, 根据当前登录的用户来读写 excel 文件
    完善了 Swagger 的说明
    
> 下一版本

    完善前端页面后, 优化项目 
    引入设计模式
    部署到服务器
### version beta 0.3 : '引入shiro,mybaits,jjwt'
> 说明

    shiro 已引入.
    mybatis plus 已引入.
    权限管理可以通过用户名和密码登录获取 token, 请求通过携带返回的token可以进行权限认证.
> 下一版本
 
    完善权限管理接口
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
    