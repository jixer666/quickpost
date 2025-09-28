<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">快传驿站（QuickPost）</h1>

<h4 align="center">基于SpringBoot+Vue前后端分离的文件快速存取平台</h4>

<p align="center">
	<a href="https://github.com/jixer666/quickpost/stargazers"><img src="https://img.shields.io/github/stars/jixer666/quickpost?style=social" alt="Stars"></a>
	<a href="https://github.com/jixer666/quickpost/forks"><img src="https://img.shields.io/github/forks/jixer666/quickpost?style=social" alt="Forks"></a>
	<a href="https://github.com/jixer666/quickpost/issues"><img src="https://img.shields.io/github/issues/jixer666/quickpost" alt="Issues"></a>
	<a href="https://github.com/jixer666/quickpost/blob/main/LICENSE"><img src="https://img.shields.io/github/license/jixer666/quickpost" alt="License"></a>
</p>

## 🌟 平台简介

快传驿站（QuickPost）是一款创新的文件快速存取平台，旨在为用户提供像寄快递一样简单、便捷的文件传输体验。平台通过独特的“取件码"机制，让文件分享变得前所未有的轻松。

视频演示：https://www.bilibili.com/video/BV13Rndz1E4k/?vd_source=20b2e12c281b549f17437a2e6b6cd3bd

### ✨ 核心特性

- **🚀 极简操作** - 类似快递寄取的直观体验，无需复杂设置
- **🔒 安全可靠** - 文件加密存储，取件码一次性验证（待做）
- **📱 多端适配** - 完美支持桌面端和移动端访问
- **⚡ 高速传输** - 基于分布式存储，支持大文件快速上传下载
- **🕒 智能过期** - 可自定义文件保存期限，自动清理过期文件

## 🏗️ 技术架构

### 后端技术栈
| 技术         | 版本   | 说明       |
| ------------ | ------ | ---------- |
| Spring Boot  | 2.7.0  | 核心框架   |
| MyBatis-Plus | 3.5.0  | ORM框架    |
| MySQL        | 8.0+   | 数据库     |
| Redis        | 6.0+   | 缓存数据库 |
| JWT          | 3.18.0 | 身份认证   |
| Hutool       | 5.8.0  | 工具库     |

### 前端技术栈
| 技术         | 版本   | 说明       |
| ------------ | ------ | ---------- |
| Vue 3        | 3.2.0  | 渐进式框架 |
| Element Plus | 2.2.0  | UI组件库   |
| Vite         | 3.0.0  | 构建工具   |
| Axios        | 0.27.0 | HTTP客户端 |

## 📦 功能模块

### 核心功能
- **文件上传** - 支持拖拽上传、多文件批量上传（待做）
- **取件码生成** - 自动生成6位唯一取件码
- **文件提取** - 通过取件码快速下载文件
- **有效期管理** - 支持自定义文件保存时间
- **文件预览** - 在线预览图片、文档等格式（待做）
- **多种存储方式** - 支持本地、阿里云和 Minio 三种存储方式

### 管理功能
- **用户管理** - 用户注册、登录、权限控制
- **文件管理** - 文件列表、状态监控、统计分析
- **系统监控** - 服务器状态、操作日志、性能监控

## 🚀 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js 14.0+
- Maven 3.6+

### 部署步骤

1. **克隆项目**
```bash
git clone https://github.com/jixer666/quickpost.git
cd quickpost
```

2. **数据库初始化**
```sql
# 创建数据库
CREATE DATABASE quickpost DEFAULT CHARACTER SET utf8mb4;

# 导入SQL脚本（位于sql目录）
```

3. **后端配置**
```bash
cd quickpost-backend

# 修改配置文件
vim src/main/resources/application.yml

# 配置数据库和Redis连接
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/quickpost
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

4. **启动后端服务**
```bash
mvn clean install
mvn spring-boot:run
```

5. **前端配置**
```bash
cd quickpost-ui

# 安装依赖
npm install

# 启动前端服务
npm run dev
```

6. **访问系统**
```
前端地址：http://localhost
后端API：http://localhost:15000
```

## 📱 使用指南

### 存放文件
1. 点击"存放文件"进入上传页面
2. 拖拽或选择要上传的文件
3. 设置文件有效期
4. 生成取件码并分享

### 提取文件
1. 点击"提取文件"进入提取页面
2. 输入6位取件码
3. 验证通过后下载文件

## 🤝 参与贡献

我们欢迎任何形式的贡献！请阅读以下指南：

1. **Fork 本项目**
2. **创建特性分支** (`git checkout -b feature/AmazingFeature`)
3. **提交更改** (`git commit -m 'Add some AmazingFeature'`)
4. **推送到分支** (`git push origin feature/AmazingFeature`)
5. **开启 Pull Request**

### 贡献者
感谢所有为本项目做出贡献的开发者！

<a href="https://github.com/jixer666/quickpost/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=jixer666/quickpost" />
</a>

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系我们

- **项目主页**：https://github.com/jixer666/quickpost
- **问题反馈**：https://github.com/jixer666/quickpost/issues
- **邮箱联系**：jixer666t@163.com

## ⭐ 支持项目

如果这个项目对您有帮助，请给我们一个 ⭐ Star！这是对我们最大的鼓励！

---

<p align="center">
  让文件传输像寄快递一样简单 - 快传驿站
</p>
