# Auto Coder

## 项目介绍

Auto Coder 是一个基于 FreeMarker 模板引擎的 Java 代码生成工具，旨在通过解析数据库建表语句（DDL），自动生成 Java 代码（包括 Model、DTO、Mapper 接口、Mapper.xml、Service 接口及实现、Controller 层等），极大地提高开发效率，减少手动编写模板代码的繁琐。

## 功能特性

- 解析数据库建表语句（DDL）并生成 Java 代码，支持生成以下代码：
  - **Model**（实体类）
  - **Mapper 接口**
  - **Mapper.xml**（MyBatis XML 映射文件）
  - **Service 接口及实现**
  - **Controller 层**
- **兼容多个数据库类型**，自动生成对应的 JDBC 类型。
- **提供 API 接口**，支持：
  - 定向生成指定文件类型，返回生成的代码内容。
  - 一次性生成全部代码并打包成 ZIP 文件下载。
- **定期任务与文件清理：** 支持定期清理过期文件，保持系统文件目录的整洁。

## 技术栈

- **Java** 17
- **Spring Boot 3.1.6**
- **FreeMarker**（模板引擎）
- **JSQLParser**（用于解析 DDL 语句）
- **Maven 3.8.8**

## 快速开始

### 1. 环境准备

- Java 17+
- Maven 3.8+
- Redis 服务器（仅在使用 Redis 模式时需要）

### 2. 安装步骤

#### 克隆代码仓库

```bash
git clone https://github.com/lingyjava/auto-coder.git
cd auto-coder
```

#### 构建项目

```bash
mvn clean install
```

#### 启动项目

```bash
mvn spring-boot:run 
```

### 3. 配置说明

#### 主要配置项

项目中配置文件为 `application.properties`，在 `src/main/resources` 文件夹下，可以配置项目的输出目录、压缩包目录等配置项。以下是配置示例：

```properties
# 生成代码的基础目录
output-dir= coder/

# 存放 ZIP 文件的目录
zip-dir= zip/
```

## 接口文档

### 1. 生成指定代码

- 请求方式：`POST`
- 请求地址：`/api/generate-code`
- 请求可以传入以下参数：
  - ddl：数据库建表语句（SQL DDL）
  - basePackage：生成的 Java 代码包名。
  - author：代码作者。
  - useLombok：是否使用 Lombok（true 或 false）
  - useSerializable：是否需要实现 Serializable 接口（true 或 false）
  - fileType：指定生成的文件类型。

**请求示例：**

```json
{
  "basePackage": "com.lingyuan",
  "author": "LingYuan",
  "useLombok": false,
  "useSerializable": true,
  "fileType": "CONTROLLER",
  "ddl": "CREATE TABLE my_table (\n  id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n  name VARCHAR(255) NOT NULL COMMENT '用户名',\n  age INT DEFAULT 18 COMMENT '年龄',\n  PRIMARY KEY (id)\n);"
}
```

**响应示例：**

```json
{
    "code": 200,
    "message": "Success",
    "data": "package com.lingyuan.controller;\n\nimport com.lingyuan.model.MyTable;\nimport com.lingyuan.service.MyTableService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.*;\n\nimport java.util.List;\n\n/**\n* MyTable Controller\n*\n* @author: LingYuan\n*/\n@RestController\n@RequestMapping(\"/mytable\")\npublic class MyTableController {\n\n    private final MyTableService myTableService;\n\n    @Autowired\n    public MyTableController(MyTableService myTableService) {\n        this.myTableService = myTableService;\n    }\n\n    /**\n    * 查询所有记录\n    * @return 所有记录\n    */\n    @GetMapping\n    public List<MyTable> getAll() {\n        return myTableService.selectAll();\n    }\n\n    /**\n    * 根据主键查询记录\n    * @param id 主键\n    * @return 对应的记录\n    */\n    @GetMapping(\"/{id}\")\n    public MyTable getById(@PathVariable(\"id\") Long id) {\n        return myTableService.selectByPrimaryKey(id);\n    }\n\n    /**\n    * 新增记录\n    * @param myTable 新增的记录\n    * @return 操作结果\n    */\n    @PostMapping\n    public int add(@RequestBody MyTable myTable) {\n        return myTableService.insert(myTable);\n    }\n\n    /**\n    * 批量新增记录\n    * @param records 批量记录\n    * @return 操作结果\n    */\n    @PostMapping(\"/batch\")\n    public int batchAdd(@RequestBody List<MyTable> records) {\n        return myTableService.batchInsert(records);\n    }\n\n    /**\n    * 更新记录\n    * @param id 主键\n    * @param myTable 更新的记录\n    * @return 操作结果\n    */\n    @PutMapping(\"/{id}\")\n    public int update(@PathVariable(\"id\") Long id, @RequestBody MyTable myTable) {\n        myTable.setId(id);\n        return myTableService.updateByPrimaryKey(myTable);\n    }\n\n    /**\n    * 按非空字段选择性更新记录\n    * @param myTable 更新的记录\n    * @return 操作结果\n    */\n    @PatchMapping\n    public int updateSelective(@RequestBody MyTable myTable) {\n        return myTableService.updateBySelective(myTable);\n    }\n\n    /**\n    * 根据主键删除记录\n    * @param id 主键\n    * @return 操作结果\n    */\n    @DeleteMapping(\"/{id}\")\n    public int delete(@PathVariable(\"id\") Long id) {\n        return myTableService.deleteByPrimaryKey(id);\n    }\n}\n"
}
```

### 2. 生成 ZIP 文件

将生成的代码打包为一个 ZIP 文件，ZIP 文件将被保存到配置文件中指定的目录，并提供文件名，可通过文件下载接口获取。

- 请求方式：`POST`
- 请求地址：`/api/generate-multiple`

**请求示例：**

```json
{
  "basePackage": "com.lingyuan",
  "author": "LingYuan",
  "useLombok": false,
  "useSerializable": true,
  "ddl": "CREATE TABLE my_table (\n  id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n  name VARCHAR(255) NOT NULL COMMENT '用户名',\n  age INT DEFAULT 18 COMMENT '年龄',\n  PRIMARY KEY (id)\n);"
}
```

**响应示例：**

```json
{
    "code": 200,
    "message": "Success",
    "data": "code_1733192099060.zip"
}
```

### 3. 文件下载

- 请求方式：`GET`
- 请求地址：`/download/${fileName}`

**请求示例：**

GET：`http://localhost:8080/api/download/code_1733192099060.zip`

- `code_1733192099060.zip` 为路径参数，值为生成 ZIP 文件接口响应的文件名。

## 定期清理

系统会自动执行定期清理任务，删除过期文件并清理空目录。[定时任务](src/main/java/com/lingyuan/codegen/scheduling/FileCleanupTask.java)的执行时间和文件过期时间可以根据实际需求调整。

## 注意事项

### 路径配置

如果未找到生成的文件，注意配置的路径是相对路径还是绝对路径。

如 `output-dir: coder/` 为相对路径，应在 `项目根目录/coder/` 下寻找生成的文件。

如 `output-dir: /coder/` 为相对路径，应在 `系统用户根目录/coder/` 下寻找生成的文件。

**配置为绝对路径时可能因无权限写入导致生成失败，确保目标文件夹允许写入。**

## 开发与贡献

欢迎提交 Issue 或 Pull Request 来改进项目。如果有兴趣参与贡献，请确保所有新的功能都已通过单元测试，并遵循项目的编码风格。

## 许可证

本项目基于 MIT License 开源。
