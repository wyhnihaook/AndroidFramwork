目录结构解析：

基类库：mvvmhabit ：所有依赖都由基类去依赖，由功能块module去引入，基类中只涵盖实现方式内容，不提现具体交互行为以及界面UI操作逻辑（除去基类中Activity、Fragment绑定的UI界面）

UI额外组件库：lib_widget：所有额外需要依赖的ui组件库（自定义View等等）都在该类中定义

网络请求库：lib_net：所有网络请求的实现、实体类的创建都由该类实现，统一分配网络请求到各个module中

资源文件库 lib_res：当前资源文件整合库，为了保证上述三个库的资源完整性，所以该库只用于业务module中的资源存储

***************以上四个库一般情况下，所有业务module都应该直接或者间接的依赖***************

组件Demo

首页模块 module_main

身份验证业务模块：module_login

音乐业务模块：module_music

场次业务模块：module_sessions

赛程业务模块：module_schedule

用户业务模块：module_user



***************如果要使每个组件独立允许，配置gradle.properties文件中的isBuildModule属性，需要注意，单独运行中执行的是alone文件下的AndroidManifest***************
