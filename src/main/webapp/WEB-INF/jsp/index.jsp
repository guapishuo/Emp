<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    #stable th, #stable td {
        border: 1px solid black;
        width: 150px;
    }
</style>
<body>

<table border="0" cellspacing="0" cellpadding="8" style="text-align: center" id="table">

    <tbody id="tbody">
    <tr>
        <th>员工编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>学历</th>
        <th>月薪</th>
        <th>添加</th>
        <th>移除</th>
    </tr>

    <tr>
        <td><input type="text" name="employeeNum"></td>
        <td><input type="text" name="employeeName"></td>
        <td>
            <select name="employeeSex">
                <option>请选择</option>
                <option value="1">男</option>
                <option value="0">女</option>
            </select>
        </td>
        <td>
            <select name="educationId">
                <option>请选择</option>
                <c:forEach items="${educations}" var="educations">
                    <option value="${educations.educationId}">${educations.educationName}</option>
                </c:forEach>
            </select>
        </td>
        <td><input type="text" name="employeePrice"></td>
        <td><input type="button" value="+" onclick="addButton(this)"></td>
        <td><input type="button" value="-" onclick="subButton(this)"></td>
    </tr>
    </tbody>

    <tr>
        <td colspan="7"><input type="button" value="批量提交" id="insertAll"></td>
    </tr>

</table>
<br/><br/><br/><br/>

<form id="uploadForm" enctype="multipart/form-data">
    <input type="file" name="multipartFile" id="multipartFile"/>
    <input type="button" value="导入Excel" onclick="uploadExcel()"/>
    <input type="button" value="导出Excel" onclick="insertExcel()">
</form>




<table border="0" cellspacing="0" cellpadding="1" id="stable" style="text-align: center">
    <tr>
        <th>员工编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>学历</th>
        <th>月薪</th>
    </tr>

    <c:forEach items="${employees}" var="employee">
        <tr>
            <td>${employee.employeeNum}</td>
            <td>${employee.employeeName}</td>
            <td>
                <c:choose>
                    <c:when test="${employee.employeeSex==1}">
                        男
                    </c:when>
                    <c:when test="${employee.employeeSex==0}">
                        女
                    </c:when>
                    <c:otherwise>
                        未知
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${employee.education.educationName}</td>
            <td>${employee.employeePrice}</td>
        </tr>
    </c:forEach>
</table>


<script type="text/javascript" src="js/jquery-1.11.3.js" charset="utf-8"></script>
<script type="text/javascript">

    function insertExcel(){
        window.location.href="/daochu";
    }

    function uploadExcel() {
        var formData = new FormData($("#uploadForm")[0]);
        $.ajax({
            method: "post",
            url: "/excelFile",
            cache: false,
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                var tbody = $("#tbody");
                $.each(data, function (index, obj) {
                    tbody.append("" +
                        "<tr>" +
                        "   <td>" + obj.name + "</td>" +
                        "   <td>" + obj.sex + "</td>" +
                        "   <td>" + obj.age + "</td>" +
                        "</tr>")
                })
            }
        })
    }

    function addButton() {
        $("#tbody").append("<tr>\n" +
            "        <td><input type=\"text\" name=\"employeeNum\"></td>\n" +
            "        <td><input type=\"text\" name=\"employeeName\"></td>\n" +
            "        <td>\n" +
            "            <select name=\"employeeSex\">\n" +
            "                <option>请选择</option>\n" +
            "                <option value=\"1\">男</option>\n" +
            "                <option value=\"0\">女</option>\n" +
            "            </select>\n" +
            "        </td>\n" +
            "        <td>\n" +
            "            <select name=\"educationId\">\n" +
            "                <option>请选择</option>\n" +
            "                <c:forEach items='${educations}' var='educations'>\n" +
            "                    <option value=\"${educations.educationId}\">${educations.educationName}</option>\n" +
            "                </c:forEach>\n" +
            "            </select>\n" +
            "        </td>\n" +
            "        <td><input type=\"text\" name=\"employeePrice\"></td>\n" +
            "        <td><input type=\"button\" value=\"+\" onclick=\"addButton(this)\"></td>\n" +
            "        <td><input type=\"button\" value=\"-\" onclick=\"subButton(this)\"></td>\n" +
            "    </tr>")
    }

    function subButton(sub) {
        var parent = $(sub).parent().parent("tr");
        var length = $("#table tr").length;
        if (length != 3) {
            parent.remove();
        } else {
            alert("就只有一行了，再删你怎么添加");
        }
    }

    $("#insertAll").click(function () {
        var dataArr = new Array();

        var tr = $("#tbody tr");
        $.each(tr, function (index, obj) {

                dataArr.push({
                    "employeeNum": $("input[name='employeeNum']", obj).val(),
                    "employeeName": $("input[name='employeeName']", obj).val(),
                    "employeeSex": $("select[name='employeeSex'] option:selected", obj).val(),
                    "educationId": $("select[name='educationId'] option:selected", obj).val(),
                    "employeePrice": $("input[name='employeePrice']", obj).val()
                });
        })

        $.ajax({
            type: 'POST',
            url: "/insert",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(dataArr),
            success: function (data) {
                alert(data.msg);
                window.location.reload();
            }
        });
    })
</script>
</body>
</html>