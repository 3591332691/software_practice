## 项目结构
```
┌─components            uni-app组件目录
│  └─comp-a.vue         可复用的a组件
├─pages                 业务页面文件存放的目录
│  ├─index
│  │  └─index.vue       index页面
│  └─search
│     └─index.vue       search页面
├─static                存放应用引用的本地静态资源（如图片、视频等）的目录
├─common                存放全局js和css文件
│  ├─js
│  │  └─xx.js           全局js文件
│  └─style
│     └─xx.css          全局css文件
├─platforms             存放各平台专用页面的目录
├─main.js               Vue初始化入口文件
├─App.vue               应用配置，用来配置App全局样式以及监听 应用生命周期
├─pages.json            配置页面路由、导航条、选项卡等页面类信息
├─manifest.json         配置应用名称、appid、logo、版本等打包信息
└─uni.scss              内置的常用样式变量
```

## 测试

使用minium框架进行自动化测试
> 代码在novel-test文件夹

[minium介绍](https://developers.weixin.qq.com/community/develop/article/doc/0000cae3a58748ed7f2c8975351413)
[minium文档](https://minitest.weixin.qq.com/#/minium/Python/readme)