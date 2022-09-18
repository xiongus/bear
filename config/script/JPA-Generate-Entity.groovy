import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import javax.swing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

config = [
        author: "xiongus",
        // 去除表名称前缀
        prefix: "sys_",
        // 生成开关
        generate: [
                entity          : true,
                repository      : false,
                service         : false,
                serviceImpl     : false,
                controller      : false
        ],
        // 多模块路径配置
        multiModule: [
                enable : false,
                entityModulePackage : "user-pojo",
                repositoryModulePackage : "user-repository",
                serviceModulePackage : "user-service",
                controllerModulePackage : "user-web"
        ],
        // 包名称
        package: [
                // 父包名
                parent          : "com.xiongus.bear",
                // 父包模块名
                module          : "console",
                // Entity包名
                entity          : "entity.po",
                //  Repository包名
                repository      : "repository",
                //  Service包名
                service         : "service",
                // Service Impl包名
                serviceImpl     : "service.impl",
                // Service Impl包名
                controller      : "controller",
        ],
        // 实体生成设置
        entity  : [
                // 继承父类设置
                parent         : [
                        // 是否继承父类
                        enable    : true,
                        // 父类名称
                        name      : "BaseEntity",
                        // 父类包名
                        package   : "com.xiongus.bear.common.entity",
                        // 父类的属性，父类已有属性不在出现在生成的实体内
                        properties: ["id", "deleted", "deletedTime", "createBy", "createTime", "modifyBy", "modifyTime"],
                ],
                // 是否序列化
                impSerializable: true,
                // 是否生成 jpa 相关内容，设置为 false 可以生成与 jpa 无关的实体
                jpa            : true,
                // 是否生成 swagger 文档相关注解，相关说明来数据库注释
                useSwagger     : false,
                // 是否使用 lombok 注解代替 get、set方法
                useLombok      : true
        ],
        // service 生成设置
        service : [
                // 参照 entity 部分的 parent
                parent: [
                        enable : false,
                        name   : "BaseService",
                        package: "com.xiongus.bear.common"
                ]
        ],
        // service impl 生成设置
        serviceImpl : [
                // 参照 entity 部分的 parent
                parent: [
                        enable : false,
                        name   : "BaseServiceImpl",
                        package: "com.xiongus.bear.common"
                ]
        ],
        // controller 生成设置
        controller : [
                // 参照 entity 部分的 parent
                parent: [
                        enable : false,
                        name   : "BaseController",
                        package: "com.xiongus.bear.common"
                ]
        ]
]

typeMapping = [
        (~/(?i)bool|boolean|bit/)         : "boolean",
        (~/(?i)bigint/)                   : "Long",
        (~/(?i)int|tinyint/)              : "Integer",
        (~/(?i)float/)                    : "Float",
        (~/(?i)double/)                   : "Double",
        (~/(?i)decimal/)                  : "BigDecimal",
        (~/(?i)datetime|timestamp/)       : "java.time.LocalDateTime",
        (~/(?i)date/)                     : "java.time.LocalDate",
        (~/(?i)time/)                     : "java.time.LocalTime",
        (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter {
        it instanceof DasTable && it.getKind() == ObjectKind.TABLE
    }.each { table ->
        def fields = calcFields(table)
        Gen.main(config, table, fields, dir.toString())
    }
}

// 转换类型
def calcFields(table) {
    def pk = Utils.getPK(table)
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           name        : Utils.toLowerCamelCase(col.getName().toString()),
                           column      : col.getName(),
                           type        : typeStr,
                           dataType    : Utils.firstMatched(col.getDataType(), /\b\w+\b/, ""),
                           len         : Utils.firstMatched(col.getDataType(), /(?<=\()\d+(?!=\))/, -1),
                           default     : col.getDefault(),
                           comment     : col.getComment(),
                           nullable    : !col.isNotNull(),
                           isPrimaryKey: null != pk && pk == col.getName(),
                   ]]
        return fields
    }
}

class Gen {

    // 生成对应的文件
    def static main(config, table, fields, dir) {
        def entityName = Utils.toUpperCamelCase(table.getName().replaceAll(config.prefix,""))
        def entityPath = dir + "/"+ (config.package.parent).replace(".", "/") + "/"+ (config.package.module).replace(".", "/") + "/" + (config.package.entity).replace(".", "/")
        def repositoryPath = dir + "/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.repository).replace(".", "/")
        def servicePath = dir + "/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.service).replace(".", "/")
        def serviceImplPath = dir + "/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" +(config.package.serviceImpl).replace(".", "/")
        def controllerPath = dir + "/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" +(config.package.controller).replace(".", "/")

        // 是否为多模块项目
        if(config.multiModule.enable){
            entityPath = dir + "/" + (config.multiModule.entityModulePackage) + "/src/main/java/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.entity).replace(".", "/")
            repositoryPath = dir + "/" + (config.multiModule.repositoryModulePackage) + "/src/main/java/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.repository).replace(".", "/")
            servicePath = dir + "/" + (config.multiModule.serviceModulePackage) + "/src/main/java/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.service).replace(".", "/")
            serviceImplPath = dir + "/" +(config.multiModule.serviceModulePackage) + "/src/main/java/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.serviceImpl).replace(".", "/")
            controllerPath = dir + "/" +(config.multiModule.controllerModulePackage) + "/src/main/java/" + (config.package.parent).replace(".", "/") + "/"+ (config.package.module) + "/" + (config.package.controller).replace(".", "/")
        }
        def pkType = fields.find { it.isPrimaryKey }.type

        // entity
        if (config.generate.entity) {
            Utils.createFile("${entityPath}", "${entityName}.java").withWriter("utf8") {
                writer -> genEntity(writer, config, config.entity.parent, table, entityName, fields)
            }
        }
        // repository
        if (config.generate.repository) {
            Utils.createFile("${repositoryPath}", "${entityName}Repository.java").withWriter("utf8") {
                writer -> genRepository(writer, config, entityName, pkType)
            }
        }
        // service
        if (config.generate.service) {
            Utils.createFile("${servicePath}", "${entityName}Service.java").withWriter("utf8") {
                writer -> genService(writer, config, config.service.parent, entityName, pkType)
            }
        }
        // service impl
        if (config.generate.serviceImpl) {
            Utils.createFile("${serviceImplPath}", "${entityName}ServiceImpl.java").withWriter("utf8") {
                writer -> genServiceImpl(writer, config, config.serviceImpl.parent, entityName, pkType)
            }
        }
        // controller
        if (config.generate.controller) {
            Utils.createFile("${controllerPath}", "${entityName}Controller.java").withWriter("utf8") {
                writer -> genController(writer, config, config.controller.parent, entityName, pkType)
            }
        }


    }

    // 生成实体
    def static genEntity(writer, config, parentConfig, table, entityName, fieldList) {

        writer.writeLine "package ${config.package.parent}.${config.package.module}.${config.package.entity};"
        writer.writeLine ""
        if (parentConfig.enable) {
            writer.writeLine "import ${parentConfig.package}.${parentConfig.name};"
        }
        if (config.entity.impSerializable) {
            writer.writeLine "import java.io.Serial;"
            writer.writeLine "import java.io.Serializable;"
            writer.writeLine ""
        }
        if (config.entity.useSwagger) {
            writer.writeLine "import io.swagger.annotations.ApiModel;"
            writer.writeLine "import io.swagger.annotations.ApiModelProperty;"
        }
        if (config.entity.jpa) {
            writer.writeLine "import javax.persistence.Column;"
            writer.writeLine "import javax.persistence.Entity;"
            writer.writeLine "import javax.persistence.Id;"
            writer.writeLine "import javax.persistence.Table;"
        }
        if (config.entity.useLombok) {
//            if (parentConfig.enable) {
//                writer.writeLine "import lombok.EqualsAndHashCode;"
//            }
//            writer.writeLine "import lombok.Data;"
            writer.writeLine "import lombok.Getter;\n" +"import lombok.Setter;"
            writer.writeLine ""
        }

        if (config.entity.jpa) {
            writer.writeLine "import org.hibernate.annotations.SQLDelete;"
            writer.writeLine "import org.hibernate.annotations.Where;"
        }

        def tableComment = Utils.getDefaultValIfCurrentValIsBlank(table.getComment(), entityName)
        writer.writeLine ""
        writer.writeLine "/**"
        writer.writeLine " * ${Utils.docCommentConvert(tableComment, '')} Entity."
        writer.writeLine " *"
        writer.writeLine " * @author ${config.author}"

        writer.writeLine " */"
        if (config.entity.useLombok) {
//            writer.writeLine "@Data"
//            if (parentConfig.enable) {
//                writer.writeLine "@EqualsAndHashCode(callSuper = true)"
//            }
            writer.writeLine "@Getter\n" + "@Setter"
        }
        if (config.entity.jpa) {
            writer.writeLine "@Entity"
            writer.writeLine "@Table(name = \"${table.name}\")"
            writer.writeLine "@SQLDelete(sql = \"UPDATE ${table.name} SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?\")"
            writer.writeLine "@Where(clause = \"deleted = 0\")"
        }
        if (config.entity.useSwagger) {
            writer.writeLine "@ApiModel(value = \"${Utils.swaggerCommentConvert(tableComment)}\")"
        }
        def extendsStr = parentConfig.enable ? " extends $parentConfig.name" : "",
            impStr = config.entity.impSerializable ? " implements Serializable" : ""
        writer.writeLine "public class $entityName$extendsStr$impStr {"

        writer.writeLine genSerialID()

        if (parentConfig.enable) {
            fieldList = fieldList.findAll { field -> !parentConfig.properties.contains(field.name) }
        }

        fieldList.each() { field -> genEntityProperties(writer, config, parentConfig, field) }

        if (!config.entity.useLombok) {
            fieldList.each() { field -> genEntityGetAndSetMethod(writer, field) }
        }
        writer.writeLine "}"

    }

    // 生成serialVersionUID
    static String genSerialID() {
        return "\t@Serial\n\tprivate static final long serialVersionUID = " + Math.abs(new Random().nextLong()) + "L;"
    }

    // 实体属性
    def static genEntityProperties(writer, config, parentConfig, field) {
        writer.writeLine ""
        def comment = Utils.getDefaultValIfCurrentValIsBlank(field.comment, field.name)

        if (config.entity.useSwagger) {
            writer.writeLine "\t@ApiModelProperty(value = \"${Utils.swaggerCommentConvert(comment)}\")"
        }else {
            if(Utils.isNotBlank(field.comment)){
                writer.writeLine "\t/**"
                writer.writeLine "\t *${Utils.docCommentConvert(field.comment, "\t")}"
                if(Utils.isNotBlank(field.default)){
                    writer.writeLine "\t * default : ${field.default}"
                }
                writer.writeLine "\t */"
            }
        }

        if (field.isPrimaryKey && config.entity.jpa) {
            writer.writeLine "\t@Id"
        }

        if (config.entity.jpa) {
            def lenStr = ""
            if (field.len.toInteger() >= 0 && !field.type.contains("java")) {
                lenStr = ", length = $field.len"
            }
            def nullStr = ""
            if(field.isNotNull){
                nullStr = ", nullable = ${!field.isNotNull}"
            }
            writer.writeLine "\t@Column(name = \"${field.column}\"$nullStr$lenStr, columnDefinition = \"${Utils.docCommentConvert(field.comment, "")}\")"
        }
        writer.writeLine "\tprivate ${field.type} ${field.name};"

    }

    // 生成get、get方法
    def static genEntityGetAndSetMethod(writer, field) {

        def methodName = Utils.toUpperCamelCase(field.name)

        // get
        writer.writeLine "\t"
        writer.writeLine "\tpublic ${field.type} get${methodName}() {"
        writer.writeLine "\t\treturn this.${field.name};"
        writer.writeLine "\t}"

        // set
        writer.writeLine "\t"
        writer.writeLine "\tpublic void set${methodName}($field.type $field.name) {"
        writer.writeLine "\t\tthis.${field.name} = ${field.name};"
        writer.writeLine "\t}"
    }

    // 生成rep
    def static genRepository(writer, config, entityName, pkType) {
        def customStr = config.generate.repositoryCustom ? ", ${entityName}RepositoryCustom" : ""

        writer.writeLine "package ${config.package.parent}.${config.package.module}.${config.package.repository};"
        writer.writeLine ""
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.entity}.$entityName;"
        writer.writeLine "import org.springframework.data.jpa.repository.JpaRepository;"
        writer.writeLine ""
        writer.writeLine "/**"
        writer.writeLine " * $entityName Repository"
        writer.writeLine " *"
        writer.writeLine " * @author ${config.author}"

        writer.writeLine " */"
        writer.writeLine "public interface ${entityName}Repository extends JpaRepository<$entityName, $pkType>$customStr {"
        writer.writeLine ""
        writer.writeLine "}"
    }

    // 生成Service
    def static genService(writer, config, parentConfig, entityName, pkType) {
        writer.writeLine "package ${config.package.parent}.${config.package.module}.${config.package.service};"
        writer.writeLine ""
        writer.writeLine "import com.xiongus.bear.common.model.PageInfo;"
        writer.writeLine "import org.springframework.stereotype.Service;"
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.entity}.$entityName;"
        writer.writeLine ""
        writer.writeLine ""
        writer.writeLine "/**"
        writer.writeLine " * $entityName service"
        writer.writeLine " *"
        writer.writeLine " * @author ${config.author}"
        writer.writeLine " */"
        def extendsStr = parentConfig.enable ? " extends ${parentConfig.name}<$entityName, $pkType>" : ""
        writer.writeLine "public interface ${entityName}Service ${extendsStr} {"
        writer.writeLine ""
        writer.writeLine "\tPageInfo<${entityName}> get${entityName}(int pageNo, int pageSize);"
        writer.writeLine ""
        writer.writeLine "\t${entityName} create${entityName}(${entityName} ${Utils.toLowerCamelCase(entityName)});"
        writer.writeLine ""
        writer.writeLine "\t${entityName} update${entityName}(${entityName} ${Utils.toLowerCamelCase(entityName)});"
        writer.writeLine ""
        writer.writeLine "\tvoid delete${entityName}(String id);"
        writer.writeLine ""
        writer.writeLine "}"
    }

    // 生成ServiceImpl
    def static genServiceImpl(writer, config, parentConfig, entityName, pkType) {
        writer.writeLine "package ${config.package.parent}.${config.package.module}.${config.package.serviceImpl};"
        writer.writeLine ""
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.repository}.${entityName}Repository;"
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.entity}.$entityName;"
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.service}.${entityName}Service;"
        if (parentConfig.enable) {
            writer.writeLine "import $parentConfig.package.$parentConfig.name;"
        }
        writer.writeLine "import org.springframework.stereotype.Service;"
        writer.writeLine ""
        writer.writeLine "import com.xiongus.bear.common.model.PageInfo;"
        writer.writeLine "import org.springframework.data.domain.Page;"
        writer.writeLine "import org.springframework.data.domain.PageRequest;"
        writer.writeLine "import org.springframework.data.domain.Pageable;"
        writer.writeLine "import org.springframework.transaction.annotation.Propagation;"
        writer.writeLine "import org.springframework.transaction.annotation.Transactional;"
        writer.writeLine ""
        writer.writeLine "import javax.annotation.Resource;"
        writer.writeLine "import java.util.stream.Collectors;"
        writer.writeLine ""
        writer.writeLine "/**"
        writer.writeLine " * $entityName service"
        writer.writeLine " *"
        writer.writeLine " * @author ${config.author}"

        writer.writeLine " */"
        writer.writeLine "@Service(\"${Utils.toLowerCamelCase(entityName)}Service\")"
        def extendsStr = parentConfig.enable ? " extends ${parentConfig.name}<$entityName, $pkType>" : ""
        writer.writeLine "public class ${entityName}ServiceImpl${extendsStr} implements ${entityName}Service {"
        writer.writeLine ""
        writer.writeLine "\t@Resource"
        writer.writeLine "\tprivate ${entityName}Repository ${Utils.toLowerCamelCase(entityName)}Repository;"
        writer.writeLine ""
        writer.writeLine "\t@Override"
        writer.writeLine "\t@Transactional(rollbackFor = Exception.class,propagation = Propagation.SUPPORTS)"
        writer.writeLine "\tpublic PageInfo<${entityName}> get${entityName}(int pageNo, int pageSize) {"
        writer.writeLine "\t\tPageInfo<${entityName}> pageInfo = new PageInfo<>();"
        writer.writeLine "\t\tPageable pageable = PageRequest.of(pageNo, pageSize);"
        writer.writeLine "\t\tPage<${entityName}> ${Utils.toLowerCamelCase(entityName)}Page = ${Utils.toLowerCamelCase(entityName)}Repository.findAll(pageable);"
        writer.writeLine "\t\tif (!${Utils.toLowerCamelCase(entityName)}Page.isEmpty()) {"
        writer.writeLine "\t\t\tpageInfo.setPageItems(${Utils.toLowerCamelCase(entityName)}Page.get().collect(Collectors.toList()));"
        writer.writeLine "\t\t\tpageInfo.setPageNumber(${Utils.toLowerCamelCase(entityName)}Page.getNumber());"
        writer.writeLine "\t\t\tpageInfo.setTotalCount((int) ${Utils.toLowerCamelCase(entityName)}Page.getTotalElements());"
        writer.writeLine "\t\t\tpageInfo.setPagesAvailable(${Utils.toLowerCamelCase(entityName)}Page.getTotalPages());"
        writer.writeLine "\t\t}"
        writer.writeLine "\t\treturn pageInfo;"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "\t@Override"
        writer.writeLine "\t@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)"
        writer.writeLine "\tpublic ${entityName} create${entityName}(${entityName} ${Utils.toLowerCamelCase(entityName)}) {"
        writer.writeLine "\t\t//todo already exist? "
        writer.writeLine "\t\treturn ${Utils.toLowerCamelCase(entityName)}Repository.save(${Utils.toLowerCamelCase(entityName)});"
        writer.writeLine "\t} "
        writer.writeLine ""
        writer.writeLine "\t@Override"
        writer.writeLine "\t@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)"
        writer.writeLine "\tpublic ${entityName} update${entityName}(${entityName} ${Utils.toLowerCamelCase(entityName)}) {"
        writer.writeLine "\t\t//todo already exist? "
        writer.writeLine "\t\treturn ${Utils.toLowerCamelCase(entityName)}Repository.save(${Utils.toLowerCamelCase(entityName)});"
        writer.writeLine "\t} "
        writer.writeLine ""
        writer.writeLine "\t@Override"
        writer.writeLine "\t@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)"
        writer.writeLine "\tpublic void delete${entityName}(String id) {"
        writer.writeLine "\t\t //todo already exist? "
        writer.writeLine "\t\t ${Utils.toLowerCamelCase(entityName)}Repository.deleteById(id);"
        writer.writeLine "\t} "
        writer.writeLine ""
        writer.writeLine "}"
    }

    // 生成Controller
    def static genController(writer, config, parentConfig, entityName, pkType) {
        writer.writeLine "package ${config.package.parent}.${config.package.module}.${config.package.controller};"
        writer.writeLine ""
        writer.writeLine "import com.xiongus.bear.common.model.RestResultUtils;"
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.entity}.$entityName;"
        writer.writeLine "import ${config.package.parent}.${config.package.module}.${config.package.service}.${entityName}Service;"
        writer.writeLine "import lombok.AllArgsConstructor;"
        writer.writeLine "import org.springframework.security.access.prepost.PreAuthorize;"
        writer.writeLine "import org.springframework.web.bind.annotation.*;"

        if (parentConfig.enable) {
            writer.writeLine "import $parentConfig.package.$parentConfig.name;"
        }

        writer.writeLine ""
        writer.writeLine "/**"
        writer.writeLine " * $entityName operation controller"
        writer.writeLine " *"
        writer.writeLine " * @author ${config.author}"
        writer.writeLine " */"
        writer.writeLine "@RestController(\"${Utils.toLowerCamelCase(entityName)}\")"
        writer.writeLine "@RequestMapping(\"/v1/${Utils.toLowerCamelCase(entityName)}\")"
        writer.writeLine "@AllArgsConstructor"
        def extendsStr = parentConfig.enable ? " extends ${parentConfig.name}" : ""
        writer.writeLine "public class ${entityName}Controller${extendsStr} {"
        writer.writeLine ""
        writer.writeLine "\tprivate final ${entityName}Service ${Utils.toLowerCamelCase(entityName)}Service;"
        writer.writeLine ""
        writer.writeLine "\t@GetMapping"
        writer.writeLine "\t@PreAuthorize(\"@pms.hasPermission('${Utils.toLowerCamelCase(entityName)}_select')\")"
        writer.writeLine "\tpublic Object get${entityName}(@RequestParam int pageNo, @RequestParam int pageSize) {"
        writer.writeLine "\t\treturn ${Utils.toLowerCamelCase(entityName)}Service.get${entityName}(pageNo,pageSize);"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "\t@GetMapping(\"/{id}\")"
        writer.writeLine "\t@PreAuthorize(\"@pms.hasPermission('${Utils.toLowerCamelCase(entityName)}_select')\")"
        writer.writeLine "\tpublic Object get${entityName}ById(@PathVariable String id) {"
        writer.writeLine "\t\treturn null;"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "\t@PostMapping"
        writer.writeLine "\t@PreAuthorize(\"@pms.hasPermission('${Utils.toLowerCamelCase(entityName)}_create')\")"
        writer.writeLine "\tpublic Object create${entityName}(@RequestBody ${entityName} ${Utils.toLowerCamelCase(entityName)}) {"
        writer.writeLine "\t\treturn RestResultUtils.success(${Utils.toLowerCamelCase(entityName)}Service.create${entityName}(${Utils.toLowerCamelCase(entityName)}));"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "\t@PutMapping"
        writer.writeLine "\t@PreAuthorize(\"@pms.hasPermission('${Utils.toLowerCamelCase(entityName)}_update')\")"
        writer.writeLine "\tpublic Object update${entityName}(@RequestBody ${entityName} ${Utils.toLowerCamelCase(entityName)}) {"
        writer.writeLine "\t\treturn RestResultUtils.success(${Utils.toLowerCamelCase(entityName)}Service.update${entityName}(${Utils.toLowerCamelCase(entityName)}));"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "\t@DeleteMapping"
        writer.writeLine "\t@PreAuthorize(\"@pms.hasPermission('${Utils.toLowerCamelCase(entityName)}_delete')\")"
        writer.writeLine "\tpublic Object delete${entityName}(@RequestParam String id) {"
        writer.writeLine "\t\t${Utils.toLowerCamelCase(entityName)}Service.delete${entityName}(id);"
        writer.writeLine "\t\treturn RestResultUtils.success(\"delete ${Utils.toLowerCamelCase(entityName)} ok!\");"
        writer.writeLine "\t}"
        writer.writeLine ""
        writer.writeLine "}"
    }

}

class Utils {

    /**
     * 提示框
     * @param message
     * @return
     */
    static def dialog(message) {
        JOptionPane.showMessageDialog(null, message, "\u6807\u9898", JOptionPane.PLAIN_MESSAGE)
    }

    /**
     * 反射获取主键列名，
     * @param table
     * @return 若没有返回null
     */
    static def getPK(table) {
        def method = table.getClass().getMethod("getText")
        method.setAccessible(true)
        def text = method.invoke(table).toString()
        def reg = /(?<=\s{4,})\b[^\s]+\b(?!=.+\n\s+PRIMARY KEY,)/
        firstMatched(text, reg, null)
    }

    /**
     *  转换为大写驼峰
     * @param content
     * @return
     */
    static def toUpperCamelCase(content) {
        content.toString()
                .split(/_/)
                .toList()
                .stream()
                .filter { s -> s.length() > 0 }
                .map { s -> s.replaceFirst("^.", s.substring(0, 1).toUpperCase()) }
                .collect(Collectors.joining())
    }

    /**
     *  转换为驼峰
     * @param content
     * @return
     */
    static def toLowerCamelCase(content) {
        content = content.toString()
        toUpperCamelCase(content).replaceFirst(/^./, content.substring(0, 1).toLowerCase())
    }

    /**
     * 寻找第一个匹配的值
     * @param content 匹配内容
     * @param reg 正则
     * @param defaultValue 默认值
     * @return 根据正则匹配，能匹配就返回匹配的值，不能则匹配默认值
     */
    static def firstMatched(content, reg, defaultValue) {
        if (null == content) {
            return defaultValue
        }
        def m = content =~ reg
        if (m.find()) {
            return m.group()
        }
        return defaultValue
    }

    static def localDateTimeStr() {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    static def createFile(filePath, fileName) {
        def file = new File(filePath)

        if (!file.exists()) {
            file.mkdir()
        }

        file = new File(filePath + "/" + fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    static def getDefaultValIfCurrentValIsBlank(currentVal, defaultVal) {
        if (null == currentVal || currentVal.isEmpty()) {
            return defaultVal
        }
        return currentVal
    }

    static def docCommentConvert(comment, prefix) {
        if (!comment.toString().contains("\n")) {
            return prefix + comment
        }
        return Arrays.stream(comment.split("\n"))
                .map { prefix + " * " + it }
                .reduce { s1, s2 -> s1 + "\n" + s2 }
                .get()
    }

    static def swaggerCommentConvert(comment) {
        comment.replaceAll("\n", "\\\\n")
    }

    static def isNotBlank(content) {
        return content != null && content.toString().trim().length() > 0
    }
}
